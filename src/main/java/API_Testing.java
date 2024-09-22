import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class API_Testing {

    public static String contentType = "application/json";
    public static Map<String,String> payload = null;

    @Test(priority = 0)
    public void getListOfUsers(){
        String apiEndpoint = "https://fakerestapi.azurewebsites.net/api/v1/Users";
        Response response = given()
                .when()
                .get(apiEndpoint);
        System.out.println(response.getStatusCode());
        response
                .then()
                .statusCode(200)
                .log()
                .body();
    }

    @Test(priority = 1)
    public void getDetailsOfAuthorByID(){
        String apiEndpoint = "https://fakerestapi.azurewebsites.net/api/v1/Authors/5";
        Response response = given().when().get(apiEndpoint);
        response
                .then()
                .statusCode(200);
        response
                .then()
                .body("id", equalTo(5));
        response
                .then()
                .log()
                .body()
                .body("idBook", equalTo(2));
    }

    @Test(priority = 2)
    public void createNewUserAndGetResponse(){
        String url = "https://fakerestapi.azurewebsites.net/api/v1/Users";
        payload = new HashMap<String,String>();
        payload.put("id", "5");
        payload.put("userName", "Author");
        payload.put("password", "generic");
        given().contentType(contentType).body(payload)
                .when()
                .post(url)
                .then()
                .statusCode(200)
                .log()
                .body();
    }

    @Test(priority = 3)
    public void updateExistingUserAndVerify(){
        String url = "https://fakerestapi.azurewebsites.net/api/v1/Users/3";
        payload = new HashMap<String, String>();
        payload.put("id", "11");
        payload.put("userName", "Adam");
        payload.put("password", "Generic");
        given().contentType(contentType).body(payload)
                .when()
                .put(url)
                .then()
                .statusCode(200)
                .log()
                .body();
    }

    @Test(priority = 4)
    public void deleteAnyAuthor(){
        String url = "https://fakerestapi.azurewebsites.net/api/v1/Users/6";
        given()
                .when()
                .delete(url)
                .then()
                .statusCode(200)
                .log()
                .all();
    }


}
