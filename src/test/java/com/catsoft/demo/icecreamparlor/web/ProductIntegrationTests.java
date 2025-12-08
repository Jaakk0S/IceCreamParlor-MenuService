package com.catsoft.demo.icecreamparlor.web;

import com.catsoft.demo.icecreamparlor.dto.ConeDTO;
import com.catsoft.demo.icecreamparlor.dto.FlavorDTO;
import com.catsoft.demo.icecreamparlor.dto.ProductDTO;
import com.catsoft.demo.icecreamparlor.dto.ToppingDTO;
import com.catsoft.demo.icecreamparlor.repository.ProductRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private ProductRepository productRepository;


    @Test
    void menuEntryGETShouldReturnAllProducts() {
        ProductDTO[] response = super.exchangeAndExpectStatus(super.getUrl("product", -1), HttpMethod.GET, null, 200, ProductDTO[].class);
        assertThat(response).isNotNull();
        assertThat(response.length).isEqualTo(4);
    }




    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void menuEntryPOSTShouldReturnCreatedProduct_WhenRequestBodyIsCorrect() {
        int nrExistingCones = this.restTemplate.getForObject(super.getUrl("cone", -1), ConeDTO[].class).length;
        int nrExistingFlavors = this.restTemplate.getForObject(super.getUrl("cone", -1), FlavorDTO[].class).length;
        int nrExistingToppings = this.restTemplate.getForObject(super.getUrl("topping", -1), ToppingDTO[].class).length;

        ProductDTO payload = new ProductDTO(
                -1,
                "New product",
                new ConeDTO(1, "nameDoesntMatter"),
                new FlavorDTO(1, "nameDoesntMatter"),
                List.of(new ToppingDTO(1, "nameDoesntMatter", null))
        );
        var body = new HttpEntity<>(payload, null);
        ProductDTO response = super.exchangeAndExpectStatus(super.getUrl("product", -1), HttpMethod.POST, body, 200, ProductDTO.class);
        assertThat(response).isNotNull();
        assertThat(response.id()).isGreaterThan(0);

        int nrConesAfter = this.restTemplate.getForObject(super.getUrl("cone", -1), ConeDTO[].class).length;
        int nrFlavorsAfter = this.restTemplate.getForObject(super.getUrl("cone", -1), FlavorDTO[].class).length;
        int nrToppingsAfter = this.restTemplate.getForObject(super.getUrl("topping", -1), ToppingDTO[].class).length;

        assertThat(nrConesAfter).isEqualTo(nrExistingCones);
        assertThat(nrFlavorsAfter).isEqualTo(nrExistingFlavors);
        assertThat(nrToppingsAfter).isEqualTo(nrExistingToppings);
    }

    @Test
    void menuEntryPOSTShouldReturnBadRequest_WhenRequestBodyIsMissing() {
        super.exchangeAndExpectStatus(super.getUrl("product", -1), HttpMethod.POST, null, 400, ProductDTO.class);
    }

    @Test
    void menuEntryPOSTShouldReturnBadRequest_WhenRequestBodyIsMalformed() {
        ProductDTO payload = new ProductDTO(
                -1,
                "New product",
                null,
                new FlavorDTO(0, "nameDoesntMatter"),
                List.of(new ToppingDTO(0, "nameDoesntMatter", null))
        );
        var body = new HttpEntity<ProductDTO>(payload, null);
        ResponseEntity<Object> result = this.restTemplate.exchange(super.getUrl("product", -1), HttpMethod.POST, body, Object.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
    }




    @Test
    void menuEntryGETWithIdShouldReturnProduct_WhenIdFound() {
        ProductDTO response = super.exchangeAndExpectStatus(super.getUrl("product", 1), HttpMethod.GET, null, 200, ProductDTO.class);
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1);
        assertThat(response.name()).isEqualTo("Fudge Sundae");
        assertThat(response.cone().name()).isEqualTo("waffle bowl");
        assertThat(response.flavor().name()).isEqualTo("chocolate");
        assertThat(response.toppings().size()).isEqualTo(2);
        assertThat(response.toppings()).filteredOn(t -> t.name().equals("mini candy cane")).isNotEmpty();
        assertThat(response.toppings()).filteredOn(t -> t.name().equals("melted fudge")).isNotEmpty();
    }

    @Test
    void menuEntryGETWithIdShouldReturnNotFound_WhenIdNotFound() {
        super.exchangeAndExpectStatus(super.getUrl("product", 1234), HttpMethod.GET, null, 404, ProductDTO.class);
    }




    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void menuEntryDELETEWithIdShouldDeleteProduct_WhenIdFound() {
        final int deleteId = 1;

        // Product exists
        super.exchangeAndExpectStatus(super.getUrl("product", deleteId), HttpMethod.GET, null, 200, ProductDTO.class);

        // Product delete succeeds
        super.exchangeAndExpectStatus(super.getUrl("product", deleteId), HttpMethod.DELETE, null, 204, ProductDTO.class);

        // Product doesn't exist
        super.exchangeAndExpectStatus(super.getUrl("product", deleteId), HttpMethod.GET, null, 404, ProductDTO.class);

        // ManyToMany join table is cascaded upon deletion
        Object[] productToppingsWithProductId = this.productRepository.findRowsInProductToppings(deleteId);
        assertThat(productToppingsWithProductId).isEmpty();
    }

    @Test
    void menuEntryDELETEWithIdShouldReturnNotFound_WhenIdNotFound() {
        super.exchangeAndExpectStatus(super.getUrl("product", 1234), HttpMethod.DELETE, null, 204, ProductDTO.class);
    }

}