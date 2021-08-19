package POJO_Serialize;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class SerializeTest {

    @Test
    public void serialization(){
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        AddPlace p = new AddPlace();
        Location l = new Location();
        l.setLat(38.383494);
        l.setLng(33.427362);
        List<String> myList = new ArrayList<>();
        myList.add("shop");
        myList.add("shoe park");

        p.setAccuracy(50);
        p.setAddress("29, side layout, cohen 09");
        p.setLanguage("English-IN");
        p.setPhone_number("(+91) 983 893 3937");
        p.setLocation(l);
        p.setName("Frontline");
        p.setWebsite("http://google.com");
        p.setTypes(myList);

        String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(p)
                .when().post("/maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();
        System.out.println(response);

    }
}
