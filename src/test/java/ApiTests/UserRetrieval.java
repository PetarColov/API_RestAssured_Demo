package ApiTests;

import models.User;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import services.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;

public class UserRetrieval extends ApiTestBase {

    @Test
    public void getUser(){
        int userId = 2;
        String email = "janet.weaver@reqres.in";
        String firstName = "Janet";
        String lastName = "Weaver";

        Response response = userService.getUser("2");
//        System.out.println(response.then().extract().headers().toString());

        Assertions.assertEquals(userId, response.jsonPath().getInt("data.id"));
        Assertions.assertEquals(email, response.jsonPath().getString("data.email"));
        Assertions.assertEquals(firstName, response.jsonPath().getString("data.first_name"));
        Assertions.assertEquals(lastName, response.jsonPath().getString("data.last_name"));
    }

    @Test
    public void listUsers(){
        int page = 1;
        Response response = RestAssured.given()
                .queryParam("page", Collections.singleton(page))
                .when()
                .get("/api/users")
                .then()
//                .log()
//                .body()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        Assertions.assertEquals(page, response.jsonPath().getInt("page"));
        Assertions.assertNotNull(response.jsonPath().getList("data", User.class), "User data should not be null");

        System.out.println("User Id: " + response.jsonPath().getInt("data[0].id"));
        System.out.println("User Email: " + response.jsonPath().getString("data[0].email"));

        List<User> users = response.jsonPath().getList("data", User.class);

        List<User> sorted = users.stream().sorted((u1, u2) -> u1.getFirst_name().compareToIgnoreCase(u2.getFirst_name())).toList();

        for (User user : sorted) {
            System.out.println(user.getFirst_name() + " " + user.getLast_name());
        }

        long timeInMS = response.time();
        long timeInSec = response.timeIn(TimeUnit.SECONDS);

        Assertions.assertEquals(timeInSec, timeInMS / 1000);
        System.out.println("Time in MS: " + timeInMS);
    }

    @Test
    public void getUserDetails(){
        Integer userId = Integer.valueOf(2);
        String email = "janet.weaver@reqres.in";
        String firstName = "Janet";
        String lastName = "Weaver";

        Response response = RestAssured.given()
                .pathParam("userId", userId)
                .when()
                .get("/api/users/{userId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        Assertions.assertEquals(response.jsonPath().getInt("data.id"), userId);
        Assertions.assertEquals(email, response.jsonPath().getString("data.email"));
        Assertions.assertEquals(firstName, response.jsonPath().getString("data.first_name"));
        Assertions.assertEquals(lastName, response.jsonPath().getString("data.last_name"));
    }

    @Test
    public void getNonExistingUser(){
        Integer userId = Integer.valueOf(44);
        Response response = RestAssured.given()
                .pathParam("userId", userId)
                .when()
                .get("/api/users/{userId}")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body(equalTo("{}"))
                .extract()
                .response();

        Assertions.assertEquals("{}", response.asString());
    }
}
