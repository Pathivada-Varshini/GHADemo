package com.example.springdemo_docker.mocktest;

import com.example.springdemo_docker.controller.ProductController;
import com.example.springdemo_docker.model.Product;
import com.example.springdemo_docker.repository.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductRepo productRepository;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void testGetAllProducts() throws Exception {
        // Arrange: Mock the data to be returned by the repository
        Product product1 = new Product(1L, "Product 1", 10.0);
        Product product2 = new Product(2L, "Product 2", 20.0);
        List<Product> products = Arrays.asList(product1, product2);
        when(productRepository.findAll()).thenReturn(products);

        // Act & Assert: Perform the GET request and verify the response
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())  // Expect HTTP 200 OK
                .andExpect(jsonPath("$.length()").value(products.size()))  // Check if the response contains the expected number of products
                .andExpect(jsonPath("$[0].id").value(product1.getId()))  // Verify the first product's ID
                .andExpect(jsonPath("$[0].name").value(product1.getName()))  // Verify the first product's name
                .andExpect(jsonPath("$[0].price").value(product1.getPrice()));  // Verify the first product's price
    }
}
