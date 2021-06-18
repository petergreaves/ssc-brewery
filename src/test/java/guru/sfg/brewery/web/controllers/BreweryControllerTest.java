package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.domain.Brewery;
import guru.sfg.brewery.repositories.BreweryRepository;
import guru.sfg.brewery.services.BreweryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class BreweryControllerTest {

    @Mock
    BreweryRepository breweryRepository;

    MockMvc mockMvc;


    BreweryController breweryController;

    @InjectMocks
    BreweryServiceImpl breweryService;


    List<Brewery> breweryList;
    Page<Brewery> breweries;
    Page<Brewery>  pagedResponse;


    @BeforeEach
    public void setup(){

        breweryList = new ArrayList<Brewery>();
        breweryList.add(Brewery.builder().breweryName("OldDogBrewery").build());
        breweryList.add(Brewery.builder().breweryName("VikingAles").build());

        pagedResponse = new PageImpl(breweryList);

        breweryController=new BreweryController(breweryService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(breweryController)
                .build();
    }

    @Test
    public void testListBreweriesForCustomer() throws Exception {

        when(breweryRepository.findAll()).thenReturn(breweryList);

        mockMvc.perform(get("/brewery/breweries")
                .with(httpBasic("scott", "tiger")))
                .andExpect(status().isOk())
                .andExpect(view().name("breweries/index"))
                .andExpect(model().attribute("breweries", hasSize(2)))
                .andExpect(model().attributeExists("breweries"));

    }


    @Test
    public void testListBreweriesForAdmin() throws Exception {

        when(breweryRepository.findAll()).thenReturn(breweryList);

        mockMvc.perform(get("/brewery/breweries")
                .with(httpBasic("spring", "guru")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testListBreweriesForAnon() throws Exception {

        when(breweryRepository.findAll()).thenReturn(breweryList);

        mockMvc.perform(get("/brewery/breweries"))
                .andExpect(status().isUnauthorized());


    }

    @Test
    public void testListBreweriesAPI() throws Exception {

        when(breweryRepository.findAll()).thenReturn(breweryList);

        mockMvc.perform(get("/api/v1/breweries"))
                .andExpect(status().isOk());


    }


}
