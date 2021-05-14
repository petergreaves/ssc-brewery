package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IndexIT extends BaseIT {

    @Test
    public void getIndexPage() throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().isOk());


    }
}
