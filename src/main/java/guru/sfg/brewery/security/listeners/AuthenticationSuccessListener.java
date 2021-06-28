package guru.sfg.brewery.security.listeners;

import guru.sfg.brewery.domain.security.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationSuccessListener {

    @EventListener
    public void authenticationSuccess(AuthenticationSuccessEvent event){

        if (event.getSource() instanceof UsernamePasswordAuthenticationToken){


            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)event.getSource();
            User user = (User)token.getPrincipal();

            log.debug("User logged in name : " + user.getUsername());

            if (token.getDetails() instanceof WebAuthenticationDetails){

                WebAuthenticationDetails details = (WebAuthenticationDetails)token.getDetails();

                log.debug("User logged in from  " + details.getRemoteAddress());

            }
        }
    }
}
