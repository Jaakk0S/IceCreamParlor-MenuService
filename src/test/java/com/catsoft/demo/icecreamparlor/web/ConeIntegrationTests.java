package com.catsoft.demo.icecreamparlor.web;

import com.catsoft.demo.icecreamparlor.dto.ConeDTO;
import com.catsoft.demo.icecreamparlor.dto.ToppingDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ConeIntegrationTests extends AbstractIntegrationTests {



    @Test
    void coneGETShouldReturnAllCones() {
        ConeDTO[] response = super.exchangeAndExpectStatus(super.getUrl("cone", -1), HttpMethod.GET, null, 200, ConeDTO[].class);
        assertThat(response).isNotNull();
        assertThat(response.length).isEqualTo(5);
    }




    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void conePOSTShouldReturnCreatedCone_WhenCorrectBody() {
        final String coneName = "new cone";
        var body = new HttpEntity<>(new ConeDTO(-1, coneName), null);
        ConeDTO response = super.exchangeAndExpectStatus(super.getUrl("cone", -1), HttpMethod.POST, body, 200, ConeDTO.class);
        assertThat(response).isNotNull();
        assertThat(response.id()).isGreaterThan(0);
        assertThat(response.name()).isEqualTo(coneName);
    }

    @Test
    void conePOSTShouldReturnBadRequest_WhenBodyMissing() {
        super.exchangeAndExpectStatus(super.getUrl("cone", -1), HttpMethod.POST, null, 400, ConeDTO.class);
    }




    @Test
    void coneGETWithIdShouldReturnCone_WhenIdFound() {
        ConeDTO response = super.exchangeAndExpectStatus(super.getUrl("cone", 1), HttpMethod.GET, null, 200, ConeDTO.class);
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1);
        assertThat(response.name()).isEqualTo("waffle cone");
    }

    @Test
    void coneGETWithIdShouldReturnNotFound_WhenIdNotFound() {
        super.exchangeAndExpectStatus(super.getUrl("cone", 1234), HttpMethod.GET, null, 404, ConeDTO.class);
    }




    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void coneDELETEWithIdShouldDeleteCone_WhenIdFound() {
        final int deleteId = 1;

        // Cone exists
        super.exchangeAndExpectStatus(super.getUrl("cone", deleteId), HttpMethod.GET, null, 200, ConeDTO.class);

        // Cone delete succeeds
        super.exchangeAndExpectStatus(super.getUrl("cone", deleteId), HttpMethod.DELETE, null, 200, ConeDTO.class);

        // Cone doesn't exist
        super.exchangeAndExpectStatus(super.getUrl("cone", deleteId), HttpMethod.GET, null, 404, ConeDTO.class);
    }

    @Test
    void coneDELETEWithIdShouldReturnConflict_WhenIdFound_AndConeIsReferenced() {

        // Cone id=2 is referenced in a product
        final int deleteId = 2;

        super.exchangeAndExpectStatus(super.getUrl("cone", deleteId), HttpMethod.DELETE, null, 409, ConeDTO.class);
    }

    @Test
    void coneDELETEWithIdShouldReturnNoContent_WhenIdNotFound() {
        super.exchangeAndExpectStatus(super.getUrl("cone", 1234), HttpMethod.DELETE, null, 204, ConeDTO.class);
    }

}