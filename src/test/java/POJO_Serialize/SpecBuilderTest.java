package POJO_Serialize;

import POJO_Serialize.AddPlace;
import POJO_Serialize.Location;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SpecBuilderTest {

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

        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON).build();
        ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        RequestSpecification response = given().spec(req)
                .body(p);
             Response response1 = response.when().post("/maps/api/place/add/json")
                .then().spec(res).extract().response();
             String output = response1.asString();
        System.out.println(output);

    }
}
