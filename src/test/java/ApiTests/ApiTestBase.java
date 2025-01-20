package ApiTests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import services.UserService;

public class ApiTestBase {

    protected static UserService userService;

    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "https://reqres.in";
        userService = new UserService();
    }
}
