package com.spotify.oauth2.api.applicationAPI;

import com.spotify.oauth2.api.RestHelper;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.ConfigLoader;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.Route.PLAYLISTS;
import static com.spotify.oauth2.api.Route.USERS;
import static com.spotify.oauth2.api.TokenManager.getToken;

public class PlaylistApi {

    public static Response postMethod(Playlist requestPlaylist) {
        return
                RestHelper.postMethod(requestPlaylist, USERS+"/" + ConfigLoader.getInstance().getUserId()+PLAYLISTS, getToken());
    }

    public static Response postMethod(Playlist requestPlaylist, String token) {
        return
                RestHelper.postMethod(requestPlaylist, USERS+ "/" + ConfigLoader.getInstance().getUserId() + PLAYLISTS, token);
    }

    public static Response getMethod(String playlistId) {
        return
                RestHelper.getMethod(PLAYLISTS + "/" + playlistId, getToken());
    }

    public static Response putMethod(Playlist requestPlaylist, String playlistId) {
        return
                RestHelper.putMethod(requestPlaylist,PLAYLISTS + "/" + playlistId, getToken());
    }
}
