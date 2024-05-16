package api.sample;

import api.pojo.serialization.AddPlace;
import api.pojo.serialization.Location;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SerializeLearning {
    @Test
    public void serializationTest(){
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
        String response = given().relaxedHTTPSValidation().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(place).log().all()
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200).extract().response().asString();
        System.out.println(response);
    }
}
