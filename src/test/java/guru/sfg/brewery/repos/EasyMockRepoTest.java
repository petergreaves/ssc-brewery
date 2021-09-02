package guru.sfg.brewery.repos;

import guru.sfg.brewery.domain.Brewery;
import guru.sfg.brewery.repositories.BreweryRepository;
import guru.sfg.brewery.services.BreweryService;
import guru.sfg.brewery.services.BreweryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;

public class EasyMockRepoTest {

    private BreweryRepository mockBreweryRepository;
    private BreweryService breweryService;

    @BeforeEach
    public void setup(){
        mockBreweryRepository = createMock(BreweryRepository.class); // easy mock creates the mock
        breweryService = new BreweryServiceImpl(mockBreweryRepository); //use constructor
    }


    @Test
    public void testBreweries(){

        List<Brewery> retFromMock = new ArrayList<>();
        retFromMock.add(Brewery.builder().breweryName("foo").build());
        retFromMock.add(Brewery.builder().breweryName("bar").build());
        expect(mockBreweryRepository.findAll()).andReturn(retFromMock); // what I expect
        replay(mockBreweryRepository); // do it


        Assertions.assertEquals(breweryService.getAllBreweries().size(),2); //test the result

        verify(mockBreweryRepository); //interactions


    }

}
