package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.param;

@SpringBootTest
public class CustomerControllerIT extends BaseIT{

    @DisplayName("list tests")
    @Nested
    class ListTests {
        @ParameterizedTest(name = "#{index} with [{arguments}]")
        @MethodSource("guru.sfg.brewery.web.controllers.BeerControllerIT#getStreamCustomerOnly")
        void listCustomersCUSTOMER(String username, String password) throws Exception {
            mockMvc.perform(get("/customers")
                    .with(httpBasic(username, password)))
                    .andExpect(status().isOk());
        }

        @ParameterizedTest(name = "#{index} with [{arguments}]")
        @MethodSource("guru.sfg.brewery.web.controllers.BeerControllerIT#getStreamUserOnly")
        void listCustomersAsUser(String username, String password) throws Exception {
            mockMvc.perform(get("/customers")
                    .with(httpBasic(username, password)))
                    .andExpect(status().isForbidden());
        }

        @Test
        void listCustomersUNAUTH() throws Exception {
            mockMvc.perform(get("/customers"))
                    .andExpect(status().isUnauthorized());
        }
    }
    @DisplayName("add tests")
    @Nested
    class AddTests {
        @ParameterizedTest(name = "#{index} with [{arguments}]")
        @MethodSource("guru.sfg.brewery.web.controllers.BeerControllerIT#getStreamAdminOnly")
        void addCustomersADMIN(String username, String password) throws Exception {
            mockMvc.perform(post("/customers/new").with(csrf())
                    .with(httpBasic(username, password)))
                    .andExpect(status().is3xxRedirection());
        }

        @ParameterizedTest(name = "#{index} with [{arguments}]")
        @MethodSource("guru.sfg.brewery.web.controllers.BeerControllerIT#getStreamNotAdmin")
        void addCustomersAsNOTADMIN(String username, String password) throws Exception {
            mockMvc.perform(post("/customers/new",param("customerName", "foo customer"))
                    .with(httpBasic(username, password)))
                    .andExpect(status().isForbidden());
        }

        @Test
        void addCustomersUNAUTH() throws Exception {
            mockMvc.perform(post("/customers/new").with(csrf())
            .param("customerName", "foo customer"))
                    .andExpect(status().isUnauthorized());
        }
    }
}
