package api.sample;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OAuthTest {
    @Test
    public void oAuthTest(){
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().relaxedHTTPSValidation().formParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret","erZOWM9g3UtwNRj340YYaK_W").formParam("grant_type","client_credentials")
                .formParam("scope","trust")
                .when().post("/oauthapi/oauth2/resourceOwner/token").then().log().all().extract().response().asString();
        JsonPath jp = new JsonPath(response);
        String accessToken = jp.getString("access_token");

        String bookResponse = given().relaxedHTTPSValidation().queryParam("access_token",accessToken).when().get("/oauthapi/getCourseDetails")
                .then().log().all().extract().response().asString();

        JsonPath js = new JsonPath(bookResponse);
        String bookUrl = js.getString("url");
        Assert.assertEquals(bookUrl,"rahulshettycademy.com");


    }
}
