package ApiTests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class ApiTestBase {
    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "https://reqres.in";
    }
}
