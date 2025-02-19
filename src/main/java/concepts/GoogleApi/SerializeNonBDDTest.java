package concepts.GoogleApi;

import concepts.POJO_Serialize.AddPlace;
import concepts.POJO_Serialize.Location;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class SerializeNonBDDTest {

    ResponseSpecification res;

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder req = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        RestAssured.requestSpecification = req.build(); // Default request specification to completely move to non BDD

        ResponseSpecBuilder res = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        RestAssured.responseSpecification = res.build();

    }

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

        Response response = with()
                .body(p)
                .post("/maps/api/place/add/json");
        assertThat(response.path("scope"), equalTo("APP"));
        System.out.println(response.asString());

    }
}
