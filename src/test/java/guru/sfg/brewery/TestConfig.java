package guru.sfg.brewery;

import guru.sfg.brewery.domain.Brewery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean("myBrewery")
    public Brewery brewery(){

        return Brewery.builder().build();
    }
}
