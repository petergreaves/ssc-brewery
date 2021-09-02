package guru.sfg.brewery;

import guru.sfg.brewery.domain.Brewery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//@ContextConfiguration(classes = TestConfig.class)
@ContextConfiguration //uses the XML confgif
@ExtendWith(SpringExtension.class)
public class ContextConfigTest {

    @Autowired
    @Qualifier("myBreweryBean")
    Brewery brewery;

    @Test
    public void contextLoads(){

        Assertions.assertNotNull(brewery);
    }
}
