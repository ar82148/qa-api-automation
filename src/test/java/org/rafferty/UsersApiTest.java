package org.rafferty;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class UsersApiTest {

    private static final String API_KEY = System.getenv("REQRES_API_KEY");

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    void getProductsCollection_returns200() {
        Response response = given()
                .header("x-api-key", API_KEY)
                .header("X-Reqres-Env", "prod")
                .when()
                .get("/collections/products/records");

        assertEquals(200, response.statusCode());
    }
}