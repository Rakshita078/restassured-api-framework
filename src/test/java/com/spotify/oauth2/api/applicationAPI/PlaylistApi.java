package com.spotify.oauth2.api.applicationAPI;

import com.spotify.oauth2.api.RestHelper;
import com.spotify.oauth2.pojo.Playlist;
import io.restassured.response.Response;

public class PlaylistApi {

    static String access_token = "BQBGnv1cGU6UtNHkjGR-7FWIUARqrGXWh4yYOw2LPSJ_gVcoeiszEgF4PGoEW7kVMhaYQwTIORe8khT5tIUM1TK9b6Js5M5UBJh6Iamvj7VXxsSmfez6QQZEyIuJ4r7Y8EFxqWwPNDk7fAVzhm0DoeuFV0NdLoDG5EOS7cskzih6LjYbArsqBwQwqHcSbR46hNH33FGGVK1kc1q_uueavM2irV5zf_90IVNDIwNDYDEP1zAFH4XZMIC1FaQwtQQZeMhhLw3PAlxqpVeriw7KTw1p6LPIueKEzdY8i_ukr3FMWMPR";


    public static Response postMethod(Playlist requestPlaylist) {
        return
                RestHelper.postMethod(requestPlaylist, "/users/31ykimvasihu3exjkzvkhuli2ixq/playlists", access_token);
    }

    public static Response postMethod(Playlist requestPlaylist, String token) {
        return
                RestHelper.postMethod(requestPlaylist, "/users/31ykimvasihu3exjkzvkhuli2ixq/playlists", token);
    }

    public static Response getMethod(String playlistId) {
        return
                RestHelper.getMethod("playlists/" + playlistId, access_token);
    }

    public static Response putMethod(Playlist requestPlaylist, String playlistId) {
        return
                RestHelper.putMethod(requestPlaylist,"playlists/" + playlistId, access_token);
    }
}
