package guru.sfg.brewery.security.filters;

import guru.sfg.brewery.web.controllers.api.BeerRestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class RestHeaderAuthFilter extends AbstractAuthenticationProcessingFilter {

    public RestHeaderAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;


        try {
            Authentication authResult = attemptAuthentication(request, response);
            //see catch for -ve outcome

            if (authResult != null) { //if not null, we authenticated
                this.successfulAuthentication(request, response, chain, authResult);
            } else {    // we didnt, so go on with the filter
                chain.doFilter(req, response);
            }
        } catch (AuthenticationException ae) {
            log.error("Authentication failed :" +ae);
            unsuccessfulAuthentication(request, response, ae);
        }

    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Authentication success. Updating SecurityContextHolder to contain: " + authResult);
        }

        SecurityContextHolder.getContext().setAuthentication(authResult);

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {

        String userName = getUserName(httpServletRequest);
        String password = getPassword(httpServletRequest);

        if (userName == null) {
            userName = "";
        }

        if (password == null) {
            password = "";
        }

        // create a token

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, password);

        // try to auth if we have a non-enpty use name, and return the authenntication
        if (!StringUtils.isEmpty(userName)) {
            return this.getAuthenticationManager().authenticate(token);
        } else {
            return null;
        }
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                             HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        SecurityContextHolder.clearContext();

        if (logger.isDebugEnabled()) {
            logger.debug("Authentication request failed: " + failed.toString(), failed);
            logger.debug("Updated SecurityContextHolder to contain null Authentication");
        }

        response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }


    private String getPassword(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader(BeerRestController.API_SECRET_HEADER_NAME);
    }

    private String getUserName(HttpServletRequest httpServletRequest) {

        return httpServletRequest.getHeader(BeerRestController.API_KEY_HEADER_NAME);
    }
}
