package com.catsoft.demo.icecreamparlor.web;

import com.catsoft.demo.icecreamparlor.dto.ConeDTO;
import com.catsoft.demo.icecreamparlor.dto.FlavorDTO;
import com.catsoft.demo.icecreamparlor.dto.ProductDTO;
import com.catsoft.demo.icecreamparlor.dto.ToppingDTO;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductIntegrationTests extends AbstractIntegrationTests {



    @Test
    void menuEntryGETShouldReturnAllProducts() {
        ProductDTO[] response = this.restTemplate.getForObject(super.getUrl("menu-entry", -1), ProductDTO[].class);
        assertThat(response).isNotNull();
        assertThat(response.length).isEqualTo(4);
    }




    @Test
    void menuEntryPOSTShouldReturnCreatedProduct_WhenRequestBodyIsCorrect() {
        int nrExistingCones = this.restTemplate.getForObject(super.getUrl("cone", -1), ConeDTO[].class).length;
        int nrExistingFlavors = this.restTemplate.getForObject(super.getUrl("cone", -1), FlavorDTO[].class).length;
        int nrExistingToppings = this.restTemplate.getForObject(super.getUrl("topping", -1), ToppingDTO[].class).length;

        ProductDTO payload = new ProductDTO(
                -1,
                "New product",
                new ConeDTO(1, "nameDoesntMatter"),
                new FlavorDTO(1, "nameDoesntMatter"),
                List.of(new ToppingDTO(1, "nameDoesntMatter"))
        );
        var body = new HttpEntity<>(payload, null);
        ProductDTO response = this.restTemplate.postForObject(super.getUrl("menu-entry", -1), body, ProductDTO.class);
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
        try {
            this.restTemplate.postForEntity(super.getUrl("menu-entry", -1), null, ProductDTO.class);
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
        }
    }

    @Test
    void menuEntryPOSTShouldReturnBadRequest_WhenRequestBodyIsMalformed() {
        ProductDTO payload = new ProductDTO(
                -1,
                "New product",
                null,
                new FlavorDTO(0, "nameDoesntMatter"),
                List.of(new ToppingDTO(0, "nameDoesntMatter"))
        );
        var body = new HttpEntity<ProductDTO>(payload, null);
        try {
            this.restTemplate.postForEntity(super.getUrl("menu-entry", -1), body, ProductDTO.class);
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
        }
    }




    @Test
    void menuEntryGETWithIdShouldReturnProduct_WhenIdFound() {
        ProductDTO response = this.restTemplate.getForObject(super.getUrl("menu-entry", 1), ProductDTO.class);
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1);
        assertThat(response.name()).isEqualTo("Fudge Sundae");
    }

    @Test
    void menuEntryGETWithIdShouldReturnNotFound_WhenIdNotFound() {
        try {
            this.restTemplate.getForEntity(super.getUrl("menu-entry", 1234), ProductDTO.class);
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
        }
    }




    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void menuEntryDELETEWithIdShouldDeleteProduct_WhenIdFound() {
        final int deletedId = 1;

        // Product exists
        var response = this.restTemplate.getForEntity(super.getUrl("menu-entry", deletedId), ProductDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));

        // Product delete succeeds
        this.restTemplate.delete(super.getUrl("menu-entry", deletedId));

        // Product doesn't exist
        try {
            this.restTemplate.getForEntity(super.getUrl("menu-entry", deletedId), ProductDTO.class);
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
        }
    }

    @Test
    void menuEntryDELETEWithIdShouldReturnNotFound_WhenIdNotFound() {
        try {
            this.restTemplate.delete(super.getUrl("menu-entry", 1234));
        } catch (HttpClientErrorException ex) {
            assertThat(ex.getStatusCode()).isEqualTo(HttpStatusCode.valueOf((404)));
        }
    }

}