package org.rafferty;

import io.restassured.response.Response;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductsApiTest extends BaseApiTest {

    private static String createdProductId;

    @Test
    @Order(1)
    void getProductsCollection_returns200() {
        Response response = given()
                .header("x-api-key", PUBLIC_KEY)
                .header("X-Reqres-Env", "prod")
                .when()
                .get("/collections/products/records");

        assertEquals(200, response.statusCode());
        assertNotNull(response.jsonPath().getList("data"));
    }

    @Test
    @Order(2)
    void createProduct_returns201AndPersists() {
        String requestBody = """
        {"data":{"name":"Portfolio Test Widget","price":19.99}}
        """;

        Response response = given()
                .header("x-api-key", MANAGE_KEY)
                .header("X-Reqres-Env", "prod")
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/collections/products/records");

        System.out.println("Status: " + response.statusCode());
        System.out.println("Body: " + response.getBody().asString());

        assertEquals(201, response.statusCode());
        assertEquals("Portfolio Test Widget", response.jsonPath().getString("data.data.name"));

        createdProductId = response.jsonPath().getString("data.id");
        assertNotNull(createdProductId, "Created product should return an id");
    }

    @Test
    @Order(3)
    void updateProduct_returns200AndReflectsChange() {
        String requestBody = """
            {"data":{"name":"Portfolio Test Widget (Updated)","price":24.99}}
            """;

        Response response = given()
                .header("x-api-key", MANAGE_KEY)
                .header("X-Reqres-Env", "prod")
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .put("/collections/products/records/" + createdProductId);

            System.out.println("Status: " + response.statusCode());
            System.out.println("Body" + response.getBody().asString());


        assertEquals(200, response.statusCode());
        assertEquals("Portfolio Test Widget (Updated)", response.jsonPath().getString("data.data.name"));
    }

    @Test
    @Order(4)
    void deleteProduct_returns204() {
        Response response = given()
                .header("x-api-key", MANAGE_KEY)
                .header("X-Reqres-Env", "prod")
                .when()
                .delete("/collections/products/records/" + createdProductId);

        assertEquals(204, response.statusCode());
    }
}