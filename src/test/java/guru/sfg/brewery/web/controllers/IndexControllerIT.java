package guru.sfg.brewery.web.controllers;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import guru.sfg.brewery.repositories.BeerInventoryRepository;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.repositories.BreweryRepository;
import guru.sfg.brewery.repositories.CustomerRepository;
import guru.sfg.brewery.repositories.security.LoginFailureRepository;
import guru.sfg.brewery.repositories.security.LoginSuccessRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import guru.sfg.brewery.services.BeerOrderService;
import guru.sfg.brewery.services.BeerService;
import guru.sfg.brewery.services.BreweryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.criteria.CriteriaBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {IndexControllerIT.ConfigForGA.class})
public class IndexControllerIT extends BaseIT {
    @MockBean
    BeerRepository beerRepository;

    @MockBean
    BeerInventoryRepository beerInventoryRepository;

    @MockBean
    BreweryService breweryService;

    @MockBean
    CustomerRepository customerRepository;

    @MockBean
    LoginSuccessRepository loginSuccessRepository;

    @MockBean
    LoginFailureRepository loginFailureRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    PersistentTokenRepository persistentTokenRepository;

    @MockBean
    BeerService beerService;

    @MockBean
    BeerOrderService beerOrderService;

    @Test
    void testGetIndexSlash() throws Exception{
        mockMvc.perform(get("/" ))
                .andExpect(status().isOk());
    }

    @Configuration
    static class  ConfigForGA{

        @Bean
        public GoogleAuthenticator googleAuthenticator (){

            return new GoogleAuthenticator();
        }
    }
}