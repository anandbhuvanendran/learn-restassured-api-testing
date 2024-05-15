package api.sample;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.io.File;

public class JiraTest {
    @Test
    public void jiraApiSampleTest(){
        RestAssured.baseURI = "http://localhost:8080";
        SessionFilter session = new SessionFilter();
        given().log().all().header("content-type","application/json").filter(session)
                .body("{ \"username\": \"anandbnair6@gmail.com\", \"password\": \"password@1234\" }")
                .when().post("/jira/rest/auth/1/session").then().assertThat().statusCode(200);

        //Add Comments
        String expectedComment = "Jira comment check for rest api automation";

        String addCommentResponse = given().filter(session).pathParams("key","10100").body("{\n" +
                "    \"body\": \""+expectedComment+".\",\n" +
                "    \"visibility\": {\n" +
                "        \"type\": \"role\",\n" +
                "        \"value\": \"Administrators\"\n" +
                "    }\n" +
                "}").when().post("/rest/api/2/issue/{key}/comment").then().assertThat().statusCode(201)
                .extract().response().asString();
        JsonPath jp = new JsonPath(addCommentResponse);
        //String commentId = jp.get("id").toString();

        // Add attachment
        given().header("X-Atlassian-Token","no-check").pathParams("key","10100")
                .header("Content-Type","multipart/form-data")
                .multiPart("file",new File("sample.txt"))
                .when().post(" /rest/api/2/issue/{key}/attachments")
                .then().log().all().assertThat().statusCode(200);
    }
}
