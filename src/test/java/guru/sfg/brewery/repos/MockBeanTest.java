package guru.sfg.brewery.repos;

import guru.sfg.brewery.domain.Brewery;
import guru.sfg.brewery.repositories.BreweryRepository;
import guru.sfg.brewery.services.BreweryService;
import guru.sfg.brewery.services.BreweryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class MockBeanTest {

    @MockBean
    BreweryRepository repo;

    BreweryService svc;

    @BeforeEach
    void setUp() {
        svc = new BreweryServiceImpl(repo);
    }

    @Test
    public void testBreweries(){

        List<Brewery> retFromMock = new ArrayList<>();
        retFromMock.add(Brewery.builder().breweryName("foo").build());
        retFromMock.add(Brewery.builder().breweryName("bar").build());
        when(repo.findAll()).thenReturn(retFromMock);


        Assertions.assertEquals(svc.getAllBreweries().size(),2);

        verify(repo, times(1)).findAll();


    }
}
