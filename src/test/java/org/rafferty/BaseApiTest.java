package org.rafferty;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseApiTest {

    protected static final String PUBLIC_KEY = System.getenv("REQRES_PUBLIC_KEY");
    protected static final String MANAGE_KEY = System.getenv("REQRES_MANAGE_KEY");

    @BeforeAll
    static void baseSetup() {
        RestAssured.baseURI = "https://reqres.in/api";
    }
}