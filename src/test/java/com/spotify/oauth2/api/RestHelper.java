package com.spotify.oauth2.api;

import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotify.oauth2.api.SpecBuilder.*;
import static io.restassured.RestAssured.given;

public class RestHelper {

    public static Response postMethod(Object requestPlaylist, String path, String token) {
        return
                given(getRequestSpec())
                        .body(requestPlaylist)
                        .auth().oauth2(token)
                        .when()
                        .post(path)
                        .then()
                        .spec(getResponseSpec())
                        .extract()
                        .response();

    }

    public static Response getMethod(String path, String token) {
        return
                given(getRequestSpec())
                        .auth().oauth2(token)
                        .when()
                        .get(path)
                        .then()
                        .spec(getResponseSpec())
                        .extract()
                        .response();
    }

    public static Response putMethod(Object requestPlaylist, String path, String token) {
        return
                given(getRequestSpec())
                        .body(requestPlaylist)
                        .auth().oauth2(token)
                        .when()
                        .put(path)
                        .then()
                        .spec(getResponseSpec())
                        .extract()
                        .response();

    }

    public static Response postAccount(HashMap<String, String> formParams, String path) {
        return
                given(getAccountRequestSpec())
                        .formParams(formParams)
                        .when()
                        .post(path)
                        .then()
                        .spec(getResponseSpec())
                        .extract()
                        .response();
    }
}


