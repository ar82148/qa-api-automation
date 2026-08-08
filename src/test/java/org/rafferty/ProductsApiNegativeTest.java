package org.rafferty;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductsApiNegativeTest extends BaseApiTest {

    @Test
    void getProducts_withMissingApiKey_returns401() {
        Response response = given()
                .header("X-Reqres-Env", "prod")
                .when()
                .get("/collections/products/records");

        assertEquals(401, response.statusCode());
    }

    @Test
    void getProducts_withInvalidApiKey_returns403() {
        Response response = given()
                .header("x-api-key", "not_a_real_key")
                .header("X-Reqres-Env", "prod")
                .when()
                .get("/collections/products/records");

        assertEquals(403, response.statusCode());
    }

    @Test
    void createProduct_withPublicKeyInsteadOfManageKey_returns403() {
        String requestBody = """
            {"data":{"name":"Should Not Be Created","price":1.00}}
            """;

        Response response = given()
                .header("x-api-key", PUBLIC_KEY)
                .header("X-Reqres-Env", "prod")
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/collections/products/records");

        assertEquals(403, response.statusCode());
    }

    @Test
    void getProduct_withNonExistentId_returns404() {
        Response response = given()
                .header("x-api-key", PUBLIC_KEY)
                .header("X-Reqres-Env", "prod")
                .when()
                .get("/collections/products/records/nonexistent-id-12345");

        assertEquals(404, response.statusCode());
    }
}