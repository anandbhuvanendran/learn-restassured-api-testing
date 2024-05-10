package api.sample;

import files.Paylod;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

public class SampleTest1 {
    @Test
    public void Test1(){
        JsonPath jp = new JsonPath(Paylod.getSampleJson());
    }
}
