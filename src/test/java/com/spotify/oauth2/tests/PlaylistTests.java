package com.spotify.oauth2.tests;

import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.spotify.oauth2.api.applicationAPI.PlaylistApi.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistTests {

    public Playlist playlistBuilder(String name, String description, boolean _public){
        return Playlist.builder()
                .name(name)
                .description(description)
                ._public(_public)
                .build();
    }

    public void assertPlaylistEqual(Playlist requestPlaylist,Playlist responsePlaylist){
        assertThat(requestPlaylist.getName(), equalTo(responsePlaylist.getName()));
        assertThat(requestPlaylist.getDescription(), equalTo(responsePlaylist.getDescription()));
        assertThat(requestPlaylist.get_public(),equalTo(responsePlaylist.get_public()));
    }

    public void assertError(Error responseError, int expectedStatusCode, String expectedMsg){
        assertThat(responseError.getError().getStatus(), equalTo(expectedStatusCode));
        assertThat(responseError.getError().getMessage(), equalTo(expectedMsg));
    }

    @Test
    public void createPlaylist(){
        Playlist requestPlaylist = playlistBuilder("Taylor Swift","Top Taylor Swift songs",true);
        Response response = postMethod(requestPlaylist);
        assertThat(response.statusCode(), equalTo(201));
        assertPlaylistEqual(requestPlaylist,response.as(Playlist.class));
    }

    @Test
    public void getPlaylist(){
        Playlist requestPlaylist = playlistBuilder("Taylor Swift","Top Taylor Swift songs",true);
        Response response = getMethod(DataLoader.getInstance().getPlaylistId());
        assertThat(response.statusCode(), equalTo(200));
        assertPlaylistEqual(requestPlaylist,response.as(Playlist.class));
    }

    @Test
    public void updatePlaylist(){
        Playlist requestPlaylist = playlistBuilder("Taylor Swift","Top Taylor Swift songs",true);
        Response response = putMethod(requestPlaylist, DataLoader.getInstance().updatePlaylistId());
        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    public void testCreatePlaylistWithoutName(){
        Playlist requestPlaylist = playlistBuilder("","Updated playlist description",true);
        Response response = postMethod(requestPlaylist);
        assertThat(response.statusCode(), equalTo(400));
        assertError(response.as(Error.class),400,"Missing required field: name");
    }

    @Test
    public void createPlaylistWithExpiredToken(){
        String invalidToken = "12345";
        Playlist requestPlaylist = playlistBuilder("Taylor Swift","Top Taylor Swift songs",true);
        Response response = postMethod(requestPlaylist,invalidToken);
        assertThat(response.statusCode(), equalTo(401));
        assertError(response.as(Error.class),401,"Invalid access token");
    }

}
