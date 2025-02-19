package com.spotify.oauth2.api;

import com.spotify.oauth2.utils.ConfigLoader;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;

import static com.spotify.oauth2.api.RestHelper.postAccount;
import static com.spotify.oauth2.api.Route.API;
import static com.spotify.oauth2.api.Route.TOKEN;

public class TokenManager {
    private static String access_token;
    private static Instant expiry_time;


//synchronized - when a thread is accessing this method and another thread is trying to access this method at the same time then it will have o wait
    // for the 1st thread to release this method. This avoids Race condition between threads
    public synchronized static String getToken(){
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

    private static Response renewToken(){
        HashMap<String,String> formParams = new HashMap<>();
        formParams.put("grant_type", ConfigLoader.getInstance().getGrantType());
        formParams.put("refresh_token", ConfigLoader.getInstance().getRefreshToken());
        formParams.put("client_id", ConfigLoader.getInstance().getClientId());
        formParams.put("client_secret",ConfigLoader.getInstance().getClientSecret());

        Response response = postAccount(formParams,API+TOKEN);

        if(response.statusCode() != 200){
            throw new RuntimeException("ABORT! Renew token failed");
        }
        return response;

    }
}
