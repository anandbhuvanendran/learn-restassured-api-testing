package api.sample;

import files.Paylod;
import io.restassured.path.json.JsonPath;

public class SampleTest2 {
    public static void main(String[] args){
        JsonPath js = new JsonPath(Paylod.getSampleJson());

        // print number of course
        int count = js.getInt("courses.size");
        System.out.println(count);

        // Print purchase amount
        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println(purchaseAmount);

        // Title of first course
        String tileFirstCourse = js.getString("courses[0].title");
        System.out.println(tileFirstCourse);

        //print all title and price
        for(int i=0;i<count;i++){
            String title = js.getString("courses["+i+"].title");
            String price = js.getString("courses["+i+"].price");
            System.out.println(title);
            System.out.println(price);
        }

        // Number of copies sell for RPA
        for(int i=0;i<count;i++){
            if(js.getString("courses["+i+"].title").equalsIgnoreCase("RPA")) {
                String copiesSold = js.getString("courses[" + i + "].copies");
                System.out.println(copiesSold);
                break;
            }
        }

    }
}
