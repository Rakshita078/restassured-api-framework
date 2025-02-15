package com.spotify.oauth2.api.applicationAPI;

import com.spotify.oauth2.api.RestHelper;
import com.spotify.oauth2.pojo.Playlist;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.TokenManager.getToken;

public class PlaylistApi {

    public static Response postMethod(Playlist requestPlaylist) {
        return
                RestHelper.postMethod(requestPlaylist, "/users/31ykimvasihu3exjkzvkhuli2ixq/playlists", getToken());
    }

    public static Response postMethod(Playlist requestPlaylist, String token) {
        return
                RestHelper.postMethod(requestPlaylist, "/users/31ykimvasihu3exjkzvkhuli2ixq/playlists", token);
    }

    public static Response getMethod(String playlistId) {
        return
                RestHelper.getMethod("playlists/" + playlistId, getToken());
    }

    public static Response putMethod(Playlist requestPlaylist, String playlistId) {
        return
                RestHelper.putMethod(requestPlaylist,"playlists/" + playlistId, getToken());
    }
}
