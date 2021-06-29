package guru.sfg.brewery.security.google;

import guru.sfg.brewery.domain.security.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.autoconfigure.security.servlet.StaticResourceRequest;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class Google2FAFilter extends GenericFilter {


    private final AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();
    private final AuthenticationFailureHandler authenticationFailureHandler = new Google2faFailureHandler();

    private final RequestMatcher urlIs2fa = new AntPathRequestMatcher("/users/verify2fa");
    private final RequestMatcher urlResource = new AntPathRequestMatcher("/resources/**");


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        StaticResourceRequest.StaticResourceRequestMatcher staticResourceRequestMatcher = PathRequest.toStaticResources().atCommonLocations();

        // bale if we dont care about checking for the path
        if (urlIs2fa.matches(request) || urlResource.matches(request) || staticResourceRequestMatcher.matches(request)){

            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();



        if (authentication!=null && !authenticationTrustResolver.isAnonymous(authentication)){
                log.debug("Processing 2fa filter");
                if (authentication.getPrincipal()!=null && authentication.getPrincipal() instanceof User){

                    User user = (User)authentication.getPrincipal();
                    if (user.getUseGoogle2fa() && user.getGoogle2faRequired()){
                        log.debug("2fa required for user : {}", user.getUsername());
                        authenticationFailureHandler.onAuthenticationFailure(request, response, null);
                        return;
                    }
            }
        }

        filterChain.doFilter(request, response);
    }
}
