package com.catsoft.demo.icecreamparlor.web;

import com.catsoft.demo.icecreamparlor.dto.ConeDTO;
import com.catsoft.demo.icecreamparlor.dto.FlavorDTO;
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
public class FlavorIntegrationTests extends AbstractIntegrationTests {



    @Test
    void flavorGETShouldReturnAllFlavors() {
        FlavorDTO[] response = this.restTemplate.getForObject(super.getUrl("flavor", -1), FlavorDTO[].class);
        assertThat(response).isNotNull();
        assertThat(response.length).isEqualTo(6);
    }




    @Test
    void flavorPOSTShouldReturnCreatedFlavor_WhenParametersCorrect() {
        final String flavorName = "new flavor";
        var body = new HttpEntity<>(new FlavorDTO(-1, flavorName), null);
        FlavorDTO response = this.restTemplate.postForObject(super.getUrl("flavor", -1), body, FlavorDTO.class);
        assertThat(response).isNotNull();
        assertThat(response.id()).isGreaterThan(0);
        assertThat(response.name()).isEqualTo(flavorName);
    }

    @Test
    void flavorPOSTShouldReturnBadRequest_WhenParametersMissing() {
        try {
            this.restTemplate.postForObject(super.getUrl("flavor", -1), null, FlavorDTO.class, new HashMap<String, String>());
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
        }
    }




    @Test
    void flavorGETWithIdShouldReturnFlavor_WhenIdFound() {
        FlavorDTO response = this.restTemplate.getForObject(super.getUrl("flavor", 1), FlavorDTO.class);
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1);
        assertThat(response.name()).isEqualTo("vanilla");
    }

    @Test
    void flavorGETWithIdShouldReturnNotFound_WhenIdNotFound() {
        try {
            this.restTemplate.getForEntity(super.getUrl("flavor", 1234), FlavorDTO.class);
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
        }
    }




    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void flavorDELETEWithIdShouldDeleteFlavor_WhenIdFound() {
        final int deletedId = 1;

        // Flavor exists
        var response = this.restTemplate.getForEntity(super.getUrl("flavor", deletedId), FlavorDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));

        // Flavor delete succeeds
        this.restTemplate.delete(super.getUrl("flavor", deletedId));

        // Flavor doesn't exist
        try {
            this.restTemplate.getForEntity(super.getUrl("flavor", deletedId), FlavorDTO.class);
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
        }
    }

    @Test
    void flavorDELETEWithIdShouldReturnNotFound_WhenIdNotFound() {
        try {
            this.restTemplate.delete(super.getUrl("flavor", 1234));
        } catch (HttpClientErrorException ex) {
            assertThat(ex.getStatusCode()).isEqualTo(HttpStatusCode.valueOf((404)));
        }
    }

}