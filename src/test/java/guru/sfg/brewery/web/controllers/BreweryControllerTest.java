package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.domain.Brewery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class BreweryControllerTest extends BaseIT{

    List<Brewery> breweryList;

    @BeforeEach
    public void setup(){

        breweryList = new ArrayList<Brewery>();
        breweryList.add(Brewery.builder().breweryName("OldDogBrewery").build());
        breweryList.add(Brewery.builder().breweryName("VikingAles").build());

        super.setUp();

    }

    @Test
    public void testListBreweriesForCustomer() throws Exception {

        when(breweryRepository.findAll()).thenReturn(breweryList);

        mockMvc.perform(get("/brewery/breweries")
                .with(httpBasic("scott", "tiger")))
                .andExpect(status().isOk());

    }


    @Test
    public void testListBreweriesForAdmin() throws Exception {

        when(breweryRepository.findAll()).thenReturn(breweryList);

        mockMvc.perform(get("/brewery/breweries")
                .with(httpBasic("spring", "guru")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testListBreweriesForUserRole() throws Exception {

        when(breweryRepository.findAll()).thenReturn(breweryList);

        mockMvc.perform(get("/brewery/breweries")
                .with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());


    }

    @Test
    public void testListBreweriesForAnon() throws Exception {

        when(breweryRepository.findAll()).thenReturn(breweryList);

        mockMvc.perform(get("/brewery/breweries"))
                .andExpect(status().isUnauthorized());


    }

    @Test
    public void testListBreweriesAPIForCustomer() throws Exception {

        when(breweryRepository.findAll()).thenReturn(breweryList);

        mockMvc.perform(get("/api/v1/breweries")
                .with(httpBasic("scott", "tiger")))
                .andExpect(status().isOk());


    }

    @Test
    public void testListBreweriesAPIForUser() throws Exception {

        when(breweryRepository.findAll()).thenReturn(breweryList);

        mockMvc.perform(get("/api/v1/breweries")
                .with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());


    }
    @Test
    public void testListBreweriesAPIForAdmin() throws Exception {

        when(breweryRepository.findAll()).thenReturn(breweryList);

        mockMvc.perform(get("/api/v1/breweries")
                .with(httpBasic("spring", "guru")))
                .andExpect(status().isForbidden());


    }

    @Test
    public void testListBreweriesAPIForAnon() throws Exception {

        when(breweryRepository.findAll()).thenReturn(breweryList);

        mockMvc.perform(get("/api/v1/breweries"))
                .andExpect(status().isUnauthorized());


    }


}
