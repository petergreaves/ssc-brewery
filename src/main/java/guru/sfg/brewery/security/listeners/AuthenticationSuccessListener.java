package guru.sfg.brewery.security.listeners;

import guru.sfg.brewery.domain.security.LoginSuccess;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.LoginSuccessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class AuthenticationSuccessListener {

    private final LoginSuccessRepository loginSuccessRepository;

    @EventListener
    public void authenticationSuccess(AuthenticationSuccessEvent event){

        if (event.getSource() instanceof UsernamePasswordAuthenticationToken){

            LoginSuccess.LoginSuccessBuilder builder = LoginSuccess.builder();
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)event.getSource();
            User user = (User)token.getPrincipal();
            builder.user(user);

            log.debug("User logged in name : " + user.getUsername());

            if (token.getDetails() instanceof WebAuthenticationDetails){

                WebAuthenticationDetails details = (WebAuthenticationDetails)token.getDetails();

                log.debug("User logged in from  " + details.getRemoteAddress());
                builder.sourceIp(details.getRemoteAddress());

            }
            LoginSuccess success = loginSuccessRepository.save(builder.build());
            log.debug("Login success with ID : " + success.getId());
        }
    }
}
