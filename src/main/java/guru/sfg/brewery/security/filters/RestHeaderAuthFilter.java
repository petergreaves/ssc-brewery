package guru.sfg.brewery.security.filters;

import guru.sfg.brewery.web.controllers.api.BeerRestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class RestHeaderAuthFilter extends AbstractAuthenticationProcessingFilter {

    public RestHeaderAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {

        String userName = getUserName(httpServletRequest);
        String password = getPassword(httpServletRequest);

       if (userName == null){
           userName="";
       }

        if (password == null){
            password="";
        }

        // create a token

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, password);

        return this.getAuthenticationManager().authenticate(token);
    }

    private String getPassword(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader(BeerRestController.API_SECRET_HEADER_NAME);
    }

    private String getUserName(HttpServletRequest httpServletRequest) {

        return httpServletRequest.getHeader(BeerRestController.API_KEY_HEADER_NAME);
    }
}
