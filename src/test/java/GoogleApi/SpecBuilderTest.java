package GoogleApi;

import POJO_Serialize.AddPlace;
import POJO_Serialize.Location;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class SpecBuilderTest {
    RequestSpecification req;
    ResponseSpecification res;

    @BeforeClass
    public void beforeClass(){
        req = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
        res = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

    }

    //@Test
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

        Response response = given().spec(req).body(p)
                .when().post("/maps/api/place/add/json")
                .then().spec(res).body(matchesJsonSchema(new File("src/test/java/Utilities/jsonSchema.json"))).extract().response();
                assertThat(response.path("scope"), equalTo("APP"));

    }
}
