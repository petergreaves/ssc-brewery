package guru.sfg.brewery.security.listeners;

import guru.sfg.brewery.domain.security.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
@Slf4j
public class AuthenticationFailureListener {

    @EventListener
    public void authenticationFailure(AuthenticationFailureBadCredentialsEvent authenticationFailureBadCredentialsEvent){

        log.debug("login failure");

        if (authenticationFailureBadCredentialsEvent.getSource() instanceof UsernamePasswordAuthenticationToken){

            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)authenticationFailureBadCredentialsEvent.getSource();
            String name = (String)token.getPrincipal();
            String creds =(String)token.getCredentials();
            log.debug("User log in failure for name/pwd : {} / {}", name, creds );

            if (token.getDetails() instanceof WebAuthenticationDetails){

                WebAuthenticationDetails details = (WebAuthenticationDetails)token.getDetails();

                log.debug("User logged in from  " + details.getRemoteAddress());

            }
        }
    }
}
