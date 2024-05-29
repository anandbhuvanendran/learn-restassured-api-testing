package api.ecom;

import api.pojo.ecom.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
        String productId =addProductResponse.getProductId();
        System.out.println(addProductResponse.getMessage());

        //Create Order
        RequestSpecification createOrderRequest = new RequestSpecBuilder().setRelaxedHTTPSValidation().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization",token).setContentType(ContentType.JSON).build();
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setCountry("India");
        orderDetails.setProductOrderedId(productId);
        List<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();
        Order order = new Order();
        order.setOrders(orderDetailsList);
        RequestSpecification reqSpecCreateOrder = given().log().all().spec(createOrderRequest).body(order);
        String responseCheck = reqSpecCreateOrder.when().post("/api/ecom/order/create-order").then().log().all().extract().response().asString();

        //Delete Product
        RequestSpecification deleteOrderRequest = new RequestSpecBuilder().setRelaxedHTTPSValidation().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization",token).setContentType(ContentType.JSON).build();
        RequestSpecification reqSpecDeleteOrder = given().log().all().spec(deleteOrderRequest).pathParams("productId",productId);
        String deleteResponse = deleteOrderRequest.when().delete("/api/ecom/product/delete-product/{productId}")
                .then().log().all().extract().response().asString();
        JsonPath jp = new JsonPath(deleteResponse);
        System.out.println(jp.getString("message"));
        Assert.assertEquals("Product Deleted Succesfully",jp.getString("message"));


    }


}
