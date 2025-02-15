package spotify.oauth2.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistTests {
    RequestSpecification req;
    ResponseSpecification res;
    String access_token = "BQDYC02VPEOc0-_VngpNKUbMCspGJASvcx3eeUuP5ECtEJHqzAxihwm3ZEzbQLwSr-MiReCtul4TqZMtI5arTmjERTWUCRErXsjE9JKT9Bv9c6j6Z2DajHB6XA_E89W7Qs63xs6H1MsN6lCeScTBRVUM0Qt-2tOVStdFKbjgepTWXbRA4phS4t7M-V3iyffswawaNXzFcoNvW8f2hzgQQEFvopzjJp2TCtg72bOKuSf6yCiifPOJ5vnIRCF_TRmiv0HBDdT3IjSWcnUHyvaOU2MRjMdrZk9Stt_X8bYFOmI64HJG";

    @BeforeClass
    public void beforeClass(){
        req = new RequestSpecBuilder()
                .setBaseUri("https://api.spotify.com")
                .setBasePath("/v1")
                .addHeader("Authorization","Bearer "+ access_token)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
        res = new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();
    }

    @Test
    public void createPlaylist(){
        String payload = "{\n" +
                "    \"name\": \"Taylor Swift\",\n" +
                "    \"description\": \"Top Taylor Swift songs\",\n" +
                "    \"public\": false\n" +
                "}   ";
        given().spec(req)
                .body(payload)
                .when().post("/users/31ykimvasihu3exjkzvkhuli2ixq/playlists")
                .then().spec(res).assertThat().statusCode(201)
                .body("name", equalTo("Taylor Swift"),
                "description", equalTo("Top Taylor Swift songs"),
                "public", equalTo(false));
    }

    @Test
    public void getPlaylist(){
        given().spec(req)
                .when().get("playlists/2gnG2P7QRE5Tonr1R723dz")
                .then().spec(res).assertThat().statusCode(200)
                .body("name", equalTo("Taylor Swift"),
                        "description", equalTo("Top Taylor Swift songs"),
                        "public", equalTo(true));
    }

    @Test
    public void updatePlaylist(){
        String payload = "{\n" +
                "    \"name\": \"Updated Playlist Name\",\n" +
                "    \"description\": \"Updated playlist description\",\n" +
                "    \"public\": false\n" +
                "}";
        given().spec(req)
                .body(payload)
                .when().put("/playlists/2gnG2P7QRE5Tonr1R723dz")
                .then().spec(res).assertThat().statusCode(200);
    }

    @Test
    public void testCreatePlaylistWithoutName(){
        String payload = "{\n" +
                "    \"name\": \"\",\n" +
                "    \"description\": \"Top Taylor Swift songs\",\n" +
                "    \"public\": false\n" +
                "}   ";
        given().spec(req)
                .body(payload)
                .when().post("/users/31ykimvasihu3exjkzvkhuli2ixq/playlists")
                .then().spec(res).assertThat().statusCode(400)
                .body("error.message", equalTo("Missing required field: name"),
                        "error.status", equalTo(400));
    }

    @Test
    public void createPlaylistWithExpiredToken(){
        String payload = "{\n" +
                "    \"name\": \"Taylor Swift\",\n" +
                "    \"description\": \"Top Taylor Swift songs\",\n" +
                "    \"public\": false\n" +
                "}   ";
        given().baseUri("https://api.spotify.com")
                .basePath("/v1")
                .header("Authorization","Bearer "+ "12345")
                .contentType(ContentType.JSON)
                .log().all()
                .body(payload)
                .when().post("/users/31ykimvasihu3exjkzvkhuli2ixq/playlists")
                .then().spec(res).assertThat().statusCode(401)
                .body("error.message", equalTo("Invalid access token"),
                        "error.status", equalTo(401));
    }



}
