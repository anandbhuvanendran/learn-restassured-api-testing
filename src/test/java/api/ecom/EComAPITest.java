package api.ecom;

import api.pojo.ecom.AddProductResponse;
import api.pojo.ecom.LoginRequest;
import api.pojo.ecom.LoginResponse;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.File;

import static io.restassured.RestAssured.given;

public class EComAPITest {
    public static void main(String[] args){

        RequestSpecification requestSpec = new RequestSpecBuilder().setRelaxedHTTPSValidation().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON)
                .build();
        ResponseSpecification respSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("postman@gmail.com");
        loginRequest.setUserPassword("Hello123@");
        RequestSpecification loginReq = given().log().all().spec(requestSpec).body(loginRequest);
        LoginResponse loginResponse = loginReq.when().post("/api/ecom/auth/login").then().log().all().extract().response().as(LoginResponse.class);
        System.out.println(loginResponse.getToken());
        String token = loginResponse.getToken();
        String userId = loginResponse.getUserId();
        System.out.println(loginResponse.getUserId());

        // Add Product
        RequestSpecification addProductRequest = new RequestSpecBuilder().setRelaxedHTTPSValidation().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization",token).build();

        RequestSpecification reqSpecAddProduct = given().log().all().spec(addProductRequest).param("productName","Laptop")
                .param("productAddedBy",userId).param("productCategory","Electronics")
                .param("productSubCategory","Computer").param("productPrice","21200")
                .param("productDescription","Laptop with good configuration").param("productFor","All")
                .multiPart("productImage",new File("C:\\Automation\\postman.PNG"));
        AddProductResponse addProductResponse = reqSpecAddProduct.log().all().when().post("/api/ecom/product/add-product")
                .then().log().all().extract().response().as(AddProductResponse.class);
        System.out.println(addProductResponse.getProductId());
        System.out.println(addProductResponse.getMessage());


    }


}
