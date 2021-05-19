package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.web.controllers.api.BeerRestController;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySetOf;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
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
    void deleteBeerByID() throws Exception {

//        doAnswer((i) -> {
//            System.out.println("Beer removed = " + i.getArgument(0));
//            return null;
//        }).when(beerRepository).deleteById(any());

        mockMvc.perform(delete("/api/v1/beer/3f617357-174a-4f48-a19b-b0d5d76e227c")
                .header(BeerRestController.API_KEY_HEADER_NAME,"spring").header(BeerRestController.API_SECRET_HEADER_NAME, "guru"))
                .andExpect(status().isOk());

      //  verify(beerRepository).deleteById(ArgumentMatchers.any());
    }


}
