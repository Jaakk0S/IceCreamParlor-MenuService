package com.catsoft.demo.icecreamparlor.web;

import com.catsoft.demo.icecreamparlor.dto.FlavorDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class FlavorIntegrationTests extends AbstractIntegrationTests {



    @Test
    void flavorGETShouldReturnAllFlavors() {
        FlavorDTO[] response = super.exchangeAndExpectStatus(super.getUrl("flavor", -1), HttpMethod.GET, null, 200, FlavorDTO[].class);
        assertThat(response).isNotNull();
        assertThat(response.length).isEqualTo(6);
    }




    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void flavorPOSTShouldReturnCreatedFlavor_WhenBodyCorrect() {
        final String flavorName = "new flavor";
        var body = new HttpEntity<>(new FlavorDTO(-1, flavorName), null);
        FlavorDTO response = super.exchangeAndExpectStatus(super.getUrl("flavor", -1), HttpMethod.POST, body, 200, FlavorDTO.class);        assertThat(response).isNotNull();
        assertThat(response.id()).isGreaterThan(0);
        assertThat(response.name()).isEqualTo(flavorName);
    }

    @Test
    void flavorPOSTShouldReturnBadRequest_WhenBodyMissing() {
        super.exchangeAndExpectStatus(super.getUrl("flavor", -1), HttpMethod.POST, null, 400, FlavorDTO.class);
    }




    @Test
    void flavorGETWithIdShouldReturnFlavor_WhenIdFound() {
        FlavorDTO response = super.exchangeAndExpectStatus(super.getUrl("flavor", 1), HttpMethod.GET, null, 200, FlavorDTO.class);
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1);
        assertThat(response.name()).isEqualTo("vanilla");
    }

    @Test
    void flavorGETWithIdShouldReturnNotFound_WhenIdNotFound() {
        super.exchangeAndExpectStatus(super.getUrl("flavor", 1234), HttpMethod.GET, null, 404, FlavorDTO.class);
    }




    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void flavorDELETEWithIdShouldDeleteFlavor_WhenIdFound() {
        final int deleteId = 1;

        // Flavor exists
        super.exchangeAndExpectStatus(super.getUrl("flavor", deleteId), HttpMethod.GET, null, 200, FlavorDTO.class);

        // Flavor delete succeeds
        super.exchangeAndExpectStatus(super.getUrl("flavor", deleteId), HttpMethod.DELETE, null, 200, FlavorDTO.class);

        // Flavor doesn't exist
        super.exchangeAndExpectStatus(super.getUrl("flavor", deleteId), HttpMethod.GET, null, 404, FlavorDTO.class);
    }

    @Test
    void flavorDELETEWithIdShouldReturnConflict_WhenIdFound_AndFlavorIsReferenced() {

        // Flavor id=2 is referenced in a product
        final int deleteId = 2;

        super.exchangeAndExpectStatus(super.getUrl("flavor", deleteId), HttpMethod.DELETE, null, 409, FlavorDTO.class);
    }

    @Test
    void flavorDELETEWithIdShouldReturnNoContent_WhenIdNotFound() {
        super.exchangeAndExpectStatus(super.getUrl("flavor", 1234), HttpMethod.DELETE, null, 204, FlavorDTO.class);
    }

}