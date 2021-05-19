package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class BeerControllerIT extends BaseIT{



    @Test
    void initCreateBeerFormForFoo() throws Exception {
        mockMvc.perform(get("/beers/new")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("foo", "bar")))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/createBeer"))
                .andExpect(model().attributeExists("beer"));

        verifyNoInteractions(beerRepository);

    }

    @Test
    void initCreateBeerFormForUser() throws Exception {
        mockMvc.perform(get("/beers/new")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/createBeer"))
                .andExpect(model().attributeExists("beer"));

        verifyNoInteractions(beerRepository);

    }

    @Test
    void initCreateBeerFormForScott() throws Exception {
        mockMvc.perform(get("/beers/new")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("scott", "tiger")))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/createBeer"))
                .andExpect(model().attributeExists("beer"));

        verifyNoInteractions(beerRepository);

    }

    @Test
    void findBeers() throws Exception{
        mockMvc.perform(get("/beers/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/findBeers"))
                .andExpect(model().attributeExists("beer"));

        verifyNoInteractions(beerRepository);
    }

    @Test
    void findBeersWithAnonAccess() throws Exception{
        mockMvc.perform(get("/beers/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/findBeers"))
                .andExpect(model().attributeExists("beer"));

        verifyNoInteractions(beerRepository);
    }
}
