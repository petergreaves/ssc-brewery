package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class BeerRestControllerIT extends BaseIT{

    @Test
    void getBeers() throws Exception{

        mockMvc.perform(get("/api/v1/beer"))
                .andExpect(status().isOk());

    }

   @Test
    void getBeersWithAnonAccess() throws Exception{
        mockMvc.perform(get("/api/v1/beer/3f617357-174a-4f48-a19b-b0d5d76e227c"))
                .andExpect(status().isOk());

 //       verifyNoInteractions(beerRepository);
    }


}
