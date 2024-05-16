package api.sample;

import api.pojo.deserialization.Api;
import api.pojo.deserialization.GetCourse;
import api.pojo.deserialization.WebAutomation;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class PojoTest {
    @Test
    public void authPojoTest(){
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().relaxedHTTPSValidation().formParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret","erZOWM9g3UtwNRj340YYaK_W").formParam("grant_type","client_credentials")
                .formParam("scope","trust")
                .when().post("/oauthapi/oauth2/resourceOwner/token").then().log().all().extract().response().asString();
        JsonPath jp = new JsonPath(response);
        String accessToken = jp.getString("access_token");

        GetCourse gc = given().relaxedHTTPSValidation().queryParam("access_token",accessToken).when().get("/oauthapi/getCourseDetails")
                .as(GetCourse.class);
        System.out.println(gc.getInstructor());
        System.out.println(gc.getLinkedIn());
        System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
        List<Api> getBookTitle = gc.getCourses().getApi();
        for(int i=0;i<getBookTitle.size();i++){
            if(getBookTitle.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
                System.out.println(getBookTitle.get(i).getPrice());
        }
        String[] expectedList ={"Selenium Webdriver Java","Cypress","Protractor"};
        // Print all Webautomation course list
        ArrayList<String> actualList = new ArrayList<String>();
        List<WebAutomation> webAutomationCourseList = gc.getCourses().getWebAutomation();
        for (WebAutomation webAutomation : webAutomationCourseList) {
            actualList.add(webAutomation.getCourseTitle());
        }
       List<String> eList = Arrays.asList(expectedList);
        Assert.assertTrue(actualList.equals(eList));

//        JsonPath js = new JsonPath(bookResponse);
//        String bookUrl = js.getString("url");
//        Assert.assertEquals(bookUrl,"rahulshettycademy.com");
    }
}
