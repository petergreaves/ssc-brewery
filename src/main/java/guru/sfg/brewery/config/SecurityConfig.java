package guru.sfg.brewery.config;

import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import guru.sfg.brewery.security.filters.RestHeaderAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.naming.NoPermissionException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    public RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager){

        RestHeaderAuthFilter filter= new RestHeaderAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return filter;

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // setup the custom auth filter
        http.addFilterBefore(restHeaderAuthFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);

        http
                .authorizeRequests(authorize -> {
                    authorize.antMatchers("/", "/webjars/**", "/login")
                            .permitAll()
                            .antMatchers("/beers/find", "/beers*").permitAll()
                            .antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
                            .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll();
                })
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic();
    }


    @Bean
    PasswordEncoder noOpEncoder(){

        //      return NoOpPasswordEncoder.getInstance();
       // return new LdapShaPasswordEncoder();
     //   return new StandardPasswordEncoder();
     //   return new BCryptPasswordEncoder();
        return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("foo")
                .password("{bcrypt}$2a$10$xYlbcsTUXlzKAiODlYhJnuPXTFUkTAOsBeuY0gfrQeGwCgBCM9bMy")
                .roles("ADMIN")
                .and()
                .withUser("user")
                .password("{ldap}{SSHA}j9m/Y1nt0I+/QxlmdIozfr0HyVFN2WVDzGJ/LQ==")
                .roles("USER")
                .and()
                .withUser("scott")
                .password("{bcrypt15}$2a$15$pJd/zo3VDjZUJUMv0ZIM9.VJijtmSbzaceBTIV81q3JBh203MCz2y")
                .roles("CUSTOMER");
    }


    //    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails admin = User.withDefaultPasswordEncoder().username("foo").password("bar").roles("ADMIN").build();
//        UserDetails user = User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }
}
