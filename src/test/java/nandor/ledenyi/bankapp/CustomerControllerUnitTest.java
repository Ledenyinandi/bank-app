package nandor.ledenyi.bankapp;

import nandor.ledenyi.bankapp.controller.CustomerController;
import nandor.ledenyi.bankapp.entity.Customer;
import nandor.ledenyi.bankapp.exception.EntityNotFoundException;
import nandor.ledenyi.bankapp.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerUnitTest {

    private Customer customer;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerService customerService;

    @BeforeEach
    public void setup() {
        customer = new Customer(1L, "Szabó", "Zoltán", "Hungary", "Szeged", "Béke 11",
                "6000", "+36709876123", "szabozoli@gmail.com");
    }

    @Test
    public void testFindAll_shouldReturnListOfCustomers() throws Exception {
        when(customerService.findAll()).thenReturn(List.of(customer));
        mockMvc.perform(get("/customer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @Test
    public void testFindById_shouldReturnCustomer() throws Exception {
        when(customerService.findById(1L)).thenReturn(customer);
        mockMvc.perform(get("/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.lastName", is("Szabó")))
                .andExpect(jsonPath("$.country", is("Hungary")));
    }

    @Test
    public void testFindById_shouldReturnNotFoundStatus() throws Exception {
        when(customerService.findById(3L)).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/customer/3"))
                .andExpect(mvcResult -> Assertions.assertTrue(mvcResult.getResolvedException() instanceof EntityNotFoundException));
    }
}
