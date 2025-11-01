package com.catsoft.demo.icecreamparlor.web;

import com.catsoft.demo.icecreamparlor.dto.ToppingDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ToppingIntegrationTests extends AbstractIntegrationTests {



    @Test
    void toppingGETShouldReturnAllToppings() {
        ToppingDTO[] response = super.exchangeAndExpectStatus(super.getUrl("topping", -1), HttpMethod.GET, null, 200, ToppingDTO[].class);
        assertThat(response).isNotNull();
        assertThat(response.length).isEqualTo(7);
    }




    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void toppingPOSTShouldReturnCreatedTopping_WhenParametersCorrect() {
        final String toppingName = "new topping";
        var body = new HttpEntity<>(new ToppingDTO(-1, toppingName, null), null);
        ToppingDTO response = super.exchangeAndExpectStatus(super.getUrl("topping", -1), HttpMethod.POST, body, 200, ToppingDTO.class);
        assertThat(response).isNotNull();
        assertThat(response.id()).isGreaterThan(0);
        assertThat(response.name()).isEqualTo(toppingName);
    }

    @Test
    void toppingPOSTShouldReturnBadRequest_WhenBodyMissing() {
        super.exchangeAndExpectStatus(super.getUrl("topping", -1), HttpMethod.POST, null, 400, ToppingDTO.class);
    }




    @Test
    void toppingGETWithIdShouldReturnTopping_WhenIdFound() {
        ToppingDTO response = super.exchangeAndExpectStatus(super.getUrl("topping", 1), HttpMethod.GET, null, 200, ToppingDTO.class);
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1);
        assertThat(response.name()).isEqualTo("chocolate chips");
        assertThat(response.products().size()).isEqualTo(2);
        assertThat(response.products()).filteredOn(t -> t.name().equals("Chocolate Dream")).isNotEmpty();
        assertThat(response.products()).filteredOn(t -> t.name().equals("Strawberry Cloud")).isNotEmpty();
    }

    @Test
    void toppingGETWithIdShouldReturnNotFound_WhenIdNotFound() {
        super.exchangeAndExpectStatus(super.getUrl("topping", 1234), HttpMethod.GET, null, 404, ToppingDTO.class);
    }




    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void toppingDELETEWithIdShouldDeleteTopping_WhenIdFound_AndToppingIsNotReferenced() {

        // Topping id=2 is never referenced in a product
        final int deleteId = 2;

        // Topping exists
        super.exchangeAndExpectStatus(super.getUrl("topping", deleteId), HttpMethod.GET, null, 200, ToppingDTO.class);

        // Topping delete succeeds
        super.exchangeAndExpectStatus(super.getUrl("topping", deleteId), HttpMethod.DELETE, null, 200, ToppingDTO.class);

        // Topping doesn't exist
        super.exchangeAndExpectStatus(super.getUrl("topping", deleteId), HttpMethod.GET, null, 404, ToppingDTO.class);
    }

    @Test
    void toppingDELETEWithIdShouldReturnConflict_WhenIdFound_AndToppingIsReferenced() {

        // Topping id=1 is referenced in a product
        final int deleteId = 1;

        super.exchangeAndExpectStatus(super.getUrl("topping", deleteId), HttpMethod.DELETE, null, 409, ToppingDTO.class);
    }

    @Test
    void toppingDELETEWithIdShouldReturnNotFound_WhenIdNotFound() {
        super.exchangeAndExpectStatus(super.getUrl("topping", 1234), HttpMethod.DELETE, null, 204, ToppingDTO.class);
    }

}