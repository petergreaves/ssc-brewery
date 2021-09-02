package guru.sfg.brewery;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class PropertyResolutionTest {

    @Autowired
    private Environment environment;

    @Test
    public void getProperty(){

        Assertions.assertEquals(environment.getProperty("fooProp"), "bar");
    }

    @Test
    public void beanResolutionTest(){


    }



}
