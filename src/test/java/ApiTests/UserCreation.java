package ApiTests;

import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserCreation extends ApiTestBase {

    @Test
    public void createUser() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", "Petar");
        jsonObject.addProperty("job", "driver");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(jsonObject)
                .when()
                .post("/api/users")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response();

        System.out.println(response.jsonPath().getString("id"));
        System.out.println(response.jsonPath().getString("name"));
        System.out.println(response.jsonPath().getString("job"));

        Assertions.assertNotNull(response.jsonPath().getString("id"), "Id is null");
        Assertions.assertEquals("Petar", response.jsonPath().getString("name"));
        Assertions.assertEquals("driver", response.jsonPath().getString("job"));
    }
}
