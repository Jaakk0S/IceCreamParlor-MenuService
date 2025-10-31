package com.catsoft.demo.icecreamparlor.web;

import com.catsoft.demo.icecreamparlor.dto.ToppingDTO;
import com.catsoft.demo.icecreamparlor.dto.ToppingDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ToppingIntegrationTests extends AbstractIntegrationTests {



    @Test
    void toppingGETShouldReturnAllToppings() {
        ToppingDTO[] response = this.restTemplate.getForObject(super.getUrl("topping", -1), ToppingDTO[].class);
        assertThat(response).isNotNull();
        assertThat(response.length).isEqualTo(8);
    }




    @Test
    void toppingPOSTShouldReturnCreatedTopping_WhenParametersCorrect() {
        final String toppingName = "new topping";
        var body = new HttpEntity<>(new ToppingDTO(-1, toppingName), null);
        ToppingDTO response = this.restTemplate.postForObject(super.getUrl("topping", -1), body, ToppingDTO.class);
        assertThat(response).isNotNull();
        assertThat(response.id()).isGreaterThan(0);
        assertThat(response.name()).isEqualTo(toppingName);
    }

    @Test
    void toppingPOSTShouldReturnBadRequest_WhenParametersMissing() {
        try {
            this.restTemplate.postForObject(super.getUrl("topping", -1), null, ToppingDTO.class, new HashMap<String, String>());
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
        }
    }




    @Test
    void toppingGETWithIdShouldReturnTopping_WhenIdFound() {
        ToppingDTO response = this.restTemplate.getForObject(super.getUrl("topping", 1), ToppingDTO.class);
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1);
        assertThat(response.name()).isEqualTo("chocolate chips");
    }

    @Test
    void toppingGETWithIdShouldReturnNotFound_WhenIdNotFound() {
        try {
            this.restTemplate.getForEntity(super.getUrl("topping", 1234), ToppingDTO.class);
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
        }
    }




    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void toppingDELETEWithIdShouldDeleteTopping_WhenIdFound() {
        final int deletedId = 1;

        // Topping exists
        var response = this.restTemplate.getForEntity(super.getUrl("topping", deletedId), ToppingDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));

        // Topping delete succeeds
        this.restTemplate.delete(super.getUrl("topping", deletedId));

        // Topping doesn't exist
        try {
            this.restTemplate.getForEntity(super.getUrl("topping", deletedId), ToppingDTO.class);
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
        }
    }

    @Test
    void toppingDELETEWithIdShouldReturnNotFound_WhenIdNotFound() {
        try {
            this.restTemplate.delete(super.getUrl("topping", 1234));
        } catch (HttpClientErrorException ex) {
            assertThat(ex.getStatusCode()).isEqualTo(HttpStatusCode.valueOf((404)));
        }
    }

}