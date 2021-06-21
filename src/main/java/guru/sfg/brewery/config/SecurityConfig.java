package guru.sfg.brewery.config;

import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception{


         http
                .authorizeRequests(authorize -> {
                    authorize.antMatchers("/h2-console/**").permitAll()
                            .antMatchers("/", "/webjars/**", "/login").permitAll()
                            .mvcMatchers("/beers/find", "/beers*").hasAnyRole("ADMIN", "CUSTOMER", "USER")
                            .antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
                            .mvcMatchers(HttpMethod.DELETE, "/api/v1/beer/**").hasRole("ADMIN")
                            .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").hasAnyRole("ADMIN", "CUSTOMER", "USER")
                            .mvcMatchers(HttpMethod.GET, "/brewery/breweries").hasAnyRole("ADMIN","CUSTOMER")
                            .mvcMatchers(HttpMethod.GET, "/api/v1/breweries").hasAnyRole("ADMIN","CUSTOMER");
                })
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                 .and()
                .httpBasic()
                 .and().csrf().disable();

        //h2-console

        http.headers().frameOptions().sameOrigin();
    }


    @Bean
    PasswordEncoder passwordEncoder() {

        //      return NoOpPasswordEncoder.getInstance();
        // return new LdapShaPasswordEncoder();
        //   return new StandardPasswordEncoder();
        //   return new BCryptPasswordEncoder();
        return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("foo")
//                .password("{bcrypt}$2a$10$xYlbcsTUXlzKAiODlYhJnuPXTFUkTAOsBeuY0gfrQeGwCgBCM9bMy")
//                .roles("ADMIN")
//                .and()
//                .withUser("user")
//                .password("{ldap}{SSHA}j9m/Y1nt0I+/QxlmdIozfr0HyVFN2WVDzGJ/LQ==")
//                .roles("USER")
//                .and()
//                .withUser("scott")
//                .password("{bcrypt15}$2a$15$pJd/zo3VDjZUJUMv0ZIM9.VJijtmSbzaceBTIV81q3JBh203MCz2y")
//                .roles("CUSTOMER");
//    }


    //    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails admin = User.withDefaultPasswordEncoder().username("foo").password("bar").roles("ADMIN").build();
//        UserDetails user = User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }
}
