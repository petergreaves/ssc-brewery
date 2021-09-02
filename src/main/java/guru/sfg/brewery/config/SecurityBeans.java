package guru.sfg.brewery.config;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorConfig;
import com.warrenstrange.googleauth.ICredentialRepository;
import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

@Configuration
public class SecurityBeans {


    @Bean
    public GoogleAuthenticator googleAuthenticator(ICredentialRepository credentialRepository){

        GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder builder =
                new GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder();

        builder.setTimeStepSizeInMillis(TimeUnit.SECONDS.toMillis(30))
                .setWindowSize(10)
                .setNumberOfScratchCodes(0);

        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator(builder.build());
        googleAuthenticator.setCredentialRepository(credentialRepository);
        return googleAuthenticator;

    }

     @Bean
     public PersistentTokenRepository persistentTokenRepository(DataSource dataSource){

         JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
         jdbcTokenRepository.setDataSource(dataSource);
         return jdbcTokenRepository;

     }

     @Bean
     public AuthenticationEventPublisher authenticationEventPublisher(ApplicationEventPublisher applicationEventPublisher){

         return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
     }

    @Bean
    PasswordEncoder passwordEncoder() {

        //      return NoOpPasswordEncoder.getInstance();
        // return new LdapShaPasswordEncoder();
        //   return new StandardPasswordEncoder();
        //   return new BCryptPasswordEncoder();
        return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
