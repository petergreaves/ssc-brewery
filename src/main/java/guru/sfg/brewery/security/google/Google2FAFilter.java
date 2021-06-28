package guru.sfg.brewery.security.google;

import guru.sfg.brewery.domain.security.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class Google2FAFilter extends GenericFilter {


    private final AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication!=null && !authenticationTrustResolver.isAnonymous(authentication)){
                log.debug("Processing 2fa filter");
                if (authentication.getPrincipal()!=null && authentication.getPrincipal() instanceof User){

                    User user = (User)authentication.getPrincipal();
                    if (user.getUseGoogle2fa() && user.getGoogle2faRequired()){
                        log.debug("2fa required for user : {}", user.getUsername());
                    }
            }
        }

        filterChain.doFilter(request, response);
    }
}
