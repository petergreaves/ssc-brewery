package guru.sfg.brewery.security.listeners;

import guru.sfg.brewery.domain.security.LoginFailure;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.LoginFailureRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class AuthenticationFailureListener {

    private final LoginFailureRepository loginFailureRepository;
    private final UserRepository userRepository;

    @EventListener
    public void authenticationFailure(AuthenticationFailureBadCredentialsEvent authenticationFailureBadCredentialsEvent){

        log.debug("login failure");

        if (authenticationFailureBadCredentialsEvent.getSource() instanceof UsernamePasswordAuthenticationToken){

            LoginFailure.LoginFailureBuilder builder = LoginFailure.builder();

            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)authenticationFailureBadCredentialsEvent.getSource();

                String name = (String)token.getName();
                Optional<User> aUser = userRepository.findByUsername(name);

                if (aUser.isPresent()) {
                    builder.user(aUser.get());
                }
                builder.username(name);

            if (token.getDetails() instanceof WebAuthenticationDetails){

                WebAuthenticationDetails details = (WebAuthenticationDetails)token.getDetails();
                builder.sourceIp(details.getRemoteAddress());

            }

            LoginFailure loginFailure = loginFailureRepository.save(builder.build());
            log.debug("login failure logged with id : " + loginFailure.getId());

            if (loginFailure!=null && loginFailure.getUser()!=null){

                lockUserAccount(loginFailure.getUser());
            }

        }
    }

    private void lockUserAccount(User user) {

        List<LoginFailure> failures = loginFailureRepository.findAllByUserAndCreatedDateAfter(user,
                Timestamp.valueOf(LocalDateTime.now().minusDays(1) ));

        if (failures.size() > 3){
            log.debug("Locking user account for {}" , user.getUsername());
            user.setAccountNonLocked(false);
            userRepository.save(user);
        }
     }
}
