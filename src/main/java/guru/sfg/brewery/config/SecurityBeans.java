package guru.sfg.brewery.config;

import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class SecurityBeans {

     @Bean
     public PersistentTokenRepository persistentTokenRepository(DataSource dataSource){

         JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
         jdbcTokenRepository.setDataSource(dataSource);
         return jdbcTokenRepository;

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
