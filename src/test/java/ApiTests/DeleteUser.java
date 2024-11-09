package ApiTests;

import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DeleteUser extends ApiTestBase{

    @Test
    public void deleteUser(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", "Petar");
        jsonObject.addProperty("job", "driver");

        Response responsePost = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(jsonObject)
                .when()
                .post("/api/users")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response();

        String userId = responsePost.jsonPath().getString("id");
        System.out.println("Newly created userId: " + userId);

        Response responseDelete = RestAssured.given()
                .pathParam("userId", userId)
                .when()
                .delete("/api/users/{userId}")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT)
                .extract()
                .response();

        Assertions.assertTrue(responseDelete.asString().isEmpty(), "Response body is not empty after deletion");
    }
}
