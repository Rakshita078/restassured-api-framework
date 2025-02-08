package GoogleApi;

import Utilities.Payload;
import Utilities.ReusableMethods;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GoogleApiTest {

    @Test
    public void testAddPlace() {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        HashMap<String, String> nestedObject = new HashMap<>();
        nestedObject.put("lat","-38.383494");
        nestedObject.put("lng","33.427362");

        List<String> placeTypes = new ArrayList<>();
        placeTypes.add("shoe park");
        placeTypes.add("shop");


        HashMap<String,Object> mainObject = new HashMap<>();
        mainObject.put("location",nestedObject);
        mainObject.put("name","Frontline house");
        mainObject.put("phone_number","(+91) 983 893 3937");
        mainObject.put("address","29, side layout, cohen 09");
        mainObject.put("types",placeTypes);
        mainObject.put("website","http://google.com");
        mainObject.put("language","French-IN");
        mainObject.put("accuracy", 50);


        String response = given()
                //.config(config.logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))//If you don't want to use 2 times for request and response
                .log().ifValidationFails().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                //.body(Payload.addPlace())
                .body(mainObject)
                .when().post("/maps/api/place/add/json")
                .then().log().ifValidationFails().assertThat().statusCode(200).body("scope", equalTo("APP"))
                .header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();

        JsonPath js = ReusableMethods.rawToJson(response);
        String placeId = js.getString("place_id");


        String newAddress = "70 Summer walk, USA";
        Headers extractedHeader = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").body(" {\n" +
                "\"place_id\":\"" + placeId + "\",\n" +
                "\"address\":\"" + newAddress + "\",\n" +
                "\"key\":\"qaclick123\"\n" +
                "}")
                .when().put("/maps/api/place/update/json")
                .then().log().ifError().assertThat().statusCode(200).body("msg", is(equalTo("Address successfully updated"))).extract().headers();
        System.out.println("Extracted Headers"+extractedHeader);
        System.out.println("Header Connection "+ extractedHeader.get("Connection").getName());
        System.out.println("Server "+ extractedHeader.get("Server").getValue());
        System.out.println("Header Date "+ extractedHeader.getValue("Content-Length"));

        String getAddressResponse = given()
                //.log().all()
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter(LogDetail.BODY))
                .queryParam("key", "qaclick123")
                .queryParam("place_id", placeId)
                .when().get("/maps/api/place/get/json")
                .then().log().body().assertThat().statusCode(200).extract().response().asString();

        JsonPath js1 = ReusableMethods.rawToJson(getAddressResponse);
        String actualAddress = js1.getString("address");
        Assert.assertEquals(actualAddress, newAddress);
    }

}
