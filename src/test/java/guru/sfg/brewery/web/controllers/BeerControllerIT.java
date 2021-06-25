package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.function.RequestPredicates.param;

@SpringBootTest
public class BeerControllerIT extends BaseIT{

    @Test
    void initCreationFormWithSpring() throws Exception {
        mockMvc.perform(get("/beers/new").with(httpBasic("spring", "guru")))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/createBeer"))
                .andExpect(model().attributeExists("beer"));
    }

    @Test
    void initCreationForm() throws Exception {
        mockMvc.perform(get("/beers/new").with(httpBasic("spring", "guru")))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/createBeer"))
                .andExpect(model().attributeExists("beer"));
    }

    @Test
    void initCreationFormNoPermission() throws Exception {
        mockMvc.perform(get("/beers/new").with(httpBasic("scott", "tiger")))
                .andExpect(status().isForbidden());
    }

    @Test
    void initCreationFormWithAdmin() throws Exception {
        mockMvc.perform(get("/beers/new").with(httpBasic("admin", "bar")))
                .andExpect(status().isOk())
                .andExpect(view().name("beers/createBeer"))
                .andExpect(model().attributeExists("beer"));
    }

    @Test
    void findBeersUNAUTH() throws Exception{
        mockMvc.perform(get("/beers/find"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void addBeersUNAUTH() throws Exception {
        mockMvc.perform(post("/beers/new").with(csrf())
                .param("beerName", "old smokey"))
                .andExpect(status().isUnauthorized());
    }


    @ParameterizedTest(name = "#{index} with [{arguments}]")
    @MethodSource("guru.sfg.brewery.web.controllers.BeerControllerIT#getStreamNotAdmin")
    void addBeerAsNOTADMIN(String username, String password) throws Exception {
        mockMvc.perform(post("/beers/new",param("beerName", "Norse mullah"))
                .with(httpBasic(username, password)))
                .andExpect(status().isForbidden());
    }

    @ParameterizedTest(name = "#{index} with [{arguments}]")
    @MethodSource("guru.sfg.brewery.web.controllers.BeerControllerIT#getStreamAdminOnly")
    void addBeerAsADMIN(String username, String password) throws Exception {
        mockMvc.perform(post("/beers/new").with(csrf())
                .with(httpBasic(username, password)))
                .andExpect(status().is3xxRedirection());
    }

}
