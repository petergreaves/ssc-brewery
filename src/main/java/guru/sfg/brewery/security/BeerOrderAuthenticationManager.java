package guru.sfg.brewery.security;

import guru.sfg.brewery.domain.security.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class BeerOrderAuthenticationManager {

        //does the authentication user's customer match the provided UUID?
        public boolean customerIDMatches(Authentication authentication, UUID customerID){

            return ((User)authentication.getPrincipal()).getCustomer().getId().equals(customerID);
        }

}
