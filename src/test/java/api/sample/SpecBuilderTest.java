package api.sample;

import api.pojo.serialization.AddPlace;
import api.pojo.serialization.Location;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SpecBuilderTest {
    @Test
    public void specBuilderTest(){
        RestAssured.baseURI ="https://rahulshettyacademy.com";
        AddPlace place = new AddPlace();
        place.setAccuracy("50");
        place.setName("Frontline house");
        place.setPhone_number("(+91) 983 893 3937");
        place.setAddress("29, side layout, cohen 09");
        place.setWebsite("http://google.com");
        place.setLanguage("French-IN");
        Location loc = new Location();
        loc.setLng(-38.383494);
        loc.setLng(33.427362);
        place.setLocation(loc);
        List<String> myList = new ArrayList<>();
        myList.add("shoe park");
        myList.add("shop");
        place.setTypes(myList);

        //SpecBuilder//
        RequestSpecification reqSpec = new RequestSpecBuilder().setRelaxedHTTPSValidation().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key","qaclick123")
                .setContentType(ContentType.JSON).build();
        ResponseSpecification respSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        RequestSpecification response = given().spec(reqSpec);
                String actualResponse = response.body(place).log().all()
                .when().post("/maps/api/place/add/json")
                .then().spec(respSpec).extract().response().asString();
        System.out.println(actualResponse);
    }
}
