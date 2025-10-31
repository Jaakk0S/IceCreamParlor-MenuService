package com.catsoft.demo.icecreamparlor.web;

import com.catsoft.demo.icecreamparlor.dto.ConeDTO;
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
public class ConeIntegrationTests extends AbstractIntegrationTests {



    @Test
    void coneGETShouldReturnAllCones() {
        ConeDTO[] response = this.restTemplate.getForObject(super.getUrl("cone", -1), ConeDTO[].class);
        assertThat(response).isNotNull();
        assertThat(response.length).isEqualTo(5);
    }




    @Test
    void conePOSTShouldReturnCreatedCone_WhenParametersCorrect() {
        final String coneName = "new cone";
        var body = new HttpEntity<>(new ConeDTO(-1, coneName), null);
        ConeDTO response = this.restTemplate.postForObject(super.getUrl("cone", -1), body, ConeDTO.class);
        assertThat(response).isNotNull();
        assertThat(response.id()).isGreaterThan(0);
        assertThat(response.name()).isEqualTo(coneName);
    }

    @Test
    void conePOSTShouldReturnBadRequest_WhenParametersMissing() {
        try {
            this.restTemplate.postForObject(super.getUrl("cone", -1), null, ConeDTO.class, new HashMap<String, String>());
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
        }
    }




    @Test
    void coneGETWithIdShouldReturnCone_WhenIdFound() {
        ConeDTO response = this.restTemplate.getForObject(super.getUrl("cone", 1), ConeDTO.class);
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1);
        assertThat(response.name()).isEqualTo("waffle cone");
    }

    @Test
    void coneGETWithIdShouldReturnNotFound_WhenIdNotFound() {
        try {
            this.restTemplate.getForEntity(super.getUrl("cone", 1234), ConeDTO.class);
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
        }
    }




    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void coneDELETEWithIdShouldDeleteCone_WhenIdFound() {
        final int deletedId = 1;

        // Cone exists
        var response = this.restTemplate.getForEntity(super.getUrl("cone", deletedId), ConeDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));

        // Cone delete succeeds
        this.restTemplate.delete(super.getUrl("cone", deletedId));

        // Cone doesn't exist
        try {
            this.restTemplate.getForEntity(super.getUrl("cone", deletedId), ConeDTO.class);
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
        }
    }

    @Test
    void coneDELETEWithIdShouldReturnNotFound_WhenIdNotFound() {
        try {
            this.restTemplate.delete(super.getUrl("cone", 1234));
        } catch (HttpClientErrorException ex) {
            assertThat(ex.getStatusCode()).isEqualTo(HttpStatusCode.valueOf((404)));
        }
    }

}