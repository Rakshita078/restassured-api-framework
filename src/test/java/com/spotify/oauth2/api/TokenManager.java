package com.spotify.oauth2.api;

import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;

import static com.spotify.oauth2.api.RestHelper.postAccount;

public class TokenManager {
    private static String access_token;
    private static Instant expiry_time;

    public static String getToken(){
        try{
            if(access_token == null || Instant.now().isAfter(expiry_time)){
                System.out.println("Renewing token....");
                Response response = renewToken();
                access_token = response.path("access_token");
                int expiryDurationInSeconds = response.path("expires_in");
                expiry_time = Instant.now().plusSeconds(expiryDurationInSeconds -300);
            }else {
                System.out.println("Token is good to use");
            }
        }
        catch (Exception e){
            throw new RuntimeException("ABORT! Failed to get token");
        }
        return access_token;
    }
    static String refreshToken = "AQCeEi4D9RufSotb5G1z-nsfhNrdo9vcbGN4VasbgdgrkeA8Wf2Hpythtqd4JFXjgxOFx1MdZro0Xu1fxvdq15Oas3U--gCTgvlXM0jzpY4XgswsHal19DiWvbzHjqrkDzE";

    private static Response renewToken(){
        HashMap<String,String> formParams = new HashMap<>();
        formParams.put("grant_type","refresh_token");
        formParams.put("refresh_token", refreshToken);
        formParams.put("client_id","ecdc0989692948b586adfffd84e02da7");
        formParams.put("client_secret","456e8d42105848f98cb0cdeb21b6fe87");

        Response response = postAccount(formParams,"/api/token");

        if(response.statusCode() != 200){
            throw new RuntimeException("ABORT! Renew token failed");
        }
        return response;

    }
}
