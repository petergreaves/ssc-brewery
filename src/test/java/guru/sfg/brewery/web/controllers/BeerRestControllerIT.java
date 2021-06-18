package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.web.controllers.api.BeerRestController;
import org.junit.jupiter.api.Test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest
public class BeerRestControllerIT extends BaseIT{

    @Test
    void getBeers() throws Exception{

        mockMvc.perform(get("/api/v1/beer"))
                .andExpect(status().isOk());

    }

   @Test
    void getBeerByID() throws Exception{
        mockMvc.perform(get("/api/v1/beer/3f617357-174a-4f48-a19b-b0d5d76e227c"))
                .andExpect(status().isOk());

    }

    @Test
    void getBeerByUpc() throws Exception{
        mockMvc.perform(get("/api/v1/beerUpc/0631234200036"))
                .andExpect(status().isOk());

    }

    @Test
    void deleteBeerByIDWithHeaderAuth() throws Exception {


        mockMvc.perform(delete("/api/v1/beer/3f617357-174a-4f48-a19b-b0d5d76e227c")
                .header(BeerRestController.API_KEY_HEADER_NAME,"spring").header(BeerRestController.API_SECRET_HEADER_NAME, "guru"))
                .andExpect(status().isOk());
    }
    @Test
    void deleteBeerByIDWithBasicAuth() throws Exception {

        mockMvc.perform(delete("/api/v1/beer/3f617357-174a-4f48-a19b-b0d5d76e227c")
                .with(httpBasic("spring", "guru")))
                .andExpect(status().is2xxSuccessful());
    }
    @Test
    void deleteBeerByIDWithNoAuth() throws Exception {


        mockMvc.perform(delete("/api/v1/beer/3f617357-174a-4f48-a19b-b0d5d76e227c"))
                .andExpect(status().isUnauthorized());
    }


    @Test
    void deleteBeerByIDWithBadAuth() throws Exception {


        mockMvc.perform(delete("/api/v1/beer/3f617357-174a-4f48-a19b-b0d5d76e227c"))
                .andExpect(status().isUnauthorized());
    }
    @Test
    void deleteBeerByIDWithBadCreds() throws Exception {


        mockMvc.perform(delete("/api/v1/beer/3f617357-174a-4f48-a19b-b0d5d76e227c")
                .header(BeerRestController.API_KEY_HEADER_NAME,"foo").header(BeerRestController.API_SECRET_HEADER_NAME, "barxxx"))
                .andExpect(status().isUnauthorized());
    }



}
