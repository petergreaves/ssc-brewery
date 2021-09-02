package guru.sfg.brewery.monitoring;

import guru.sfg.brewery.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class CustomerDataLoaded implements HealthIndicator, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public Health health() {

        Map<String, String> initializations = new HashMap<>();
        int loaded = checkCustomerPopulationHealth(applicationContext.getBean(CustomerRepository.class));
        if (loaded ==4) {
            initializations.put("customerInit", "SUCCESS");
            initializations.put("loadCount", loaded+"");
            return Health.up().withDetails(initializations).build();
        } else {
            initializations.put("loadCount", loaded+"");
            initializations.put("customerInit", "FAILED");
            return Health.down().withDetails(initializations).build();
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private int checkCustomerPopulationHealth(CustomerRepository customerRepository) {

        int customerCount = customerRepository.findAll().size();

        log.debug("Customer data loaded : " + customerCount);

        return (customerCount);


    }
}
