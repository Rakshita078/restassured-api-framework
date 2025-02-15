package com.spotify.oauth2.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.HeaderConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.config;

public class SpecBuilder {

    public static RequestSpecification getRequestSpec(){
       return new RequestSpecBuilder()
               .setBaseUri("https://api.spotify.com")
               .setBasePath("/v1")
               .setContentType(ContentType.JSON)
               .log(LogDetail.ALL)
               .build()
               .config(config().headerConfig(HeaderConfig.headerConfig().overwriteHeadersWithName("Authorization")));

   }

   public static ResponseSpecification getResponseSpec(){
       return new ResponseSpecBuilder()
               .log(LogDetail.ALL)
               .build();
   }
}
