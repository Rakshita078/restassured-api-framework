package oauth2;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Base64;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class GmailAPITests {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    String accessToken = "ya29.a0AXeO80Tg6uxmH-hpV_hHqd6BNAGml9cHeBhjMRO5Ysuajg61mepKQPC5hJmpuFfoM8hTKKbUnMwJV0iPpHMfbxrEOAvTvunDwAyx2zIOAhEMzGDNNbgVOMjTFN7sp8PmTleeFGXsM_HVqkMkl9ni3wae3LjKutuU1asRCrqCPgaCgYKAaoSARASFQHGX2MituPxYiFKKYOxuhJU1LijPw0177";


    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder req = new RequestSpecBuilder()
                .setBaseUri("https://gmail.googleapis.com")
                .addHeader("Authorization","Bearer " + accessToken)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        requestSpecification = req.build();

        ResponseSpecBuilder res = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        responseSpecification = res.build();

    }

    //@Test
    public void getUserProfile(){
        given(requestSpecification)
                .basePath("/gmail/v1")
                .pathParam("userid","abc@gmail.com")
                .when()
                .get("/users/{userid}/profile")
                .then()
                .spec(responseSpecification);
    }

    //@Test
    public void sendMessage(){
        String msg = "From: abc@gmail.com\n" +
                "To: abc@gmail.com\n" +
                "Subject: Rest Assured Test Email \n" +
                "\n" +
                "Sending from the Gmail API from Automation.";

        String base64Encoded = Base64.getUrlEncoder().encodeToString(msg.getBytes());
        System.out.println("Encoded = "+ base64Encoded);
        HashMap<String,String> payload = new HashMap<>();
        payload.put("raw", base64Encoded);

        given(requestSpecification)
                .basePath("/gmail/v1")
                .pathParam("userid","abc@gmail.com")
                .body(payload)
                .when()
                .post("/users/{userid}/messages/send")
                .then()
                .spec(responseSpecification);
    }
    /*
    https://ostermiller.org/calc/encode.html
    https://developers.google.com/identity/protocols/oauth2
    https://developers.google.com/gmail/api/reference/rest/v1/users.messages#Message

     */
}

