package services;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiClient {
    public Response get(String endpoint) {
        return RestAssured.given().get(endpoint);
    }

    public Response post(String endpoint, Object body) {
        return RestAssured.given().body(body).post(endpoint);
    }

    public Response delete(String endpoint) {
        return RestAssured.given().delete(endpoint);
    }
}
