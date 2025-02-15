package com.spotify.oauth2.tests;

import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.spotify.oauth2.api.applicationAPI.PlaylistApi.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistTests {

    @Test
    public void createPlaylist(){
        Playlist requestPlaylist = new Playlist()
                .setName("Taylor Swift")
                .setDescription("Top Taylor Swift songs")
                .setPublic(true);

        Response response = postMethod(requestPlaylist);
        assertThat(response.statusCode(), equalTo(201));
        Playlist responsePlaylist = response.as(Playlist.class);
        assertThat(requestPlaylist.getName(), equalTo(responsePlaylist.getName()));
        assertThat(requestPlaylist.getDescription(), equalTo(responsePlaylist.getDescription()));
        assertThat(requestPlaylist.getPublic(),equalTo(responsePlaylist.getPublic()));
    }

    @Test
    public void getPlaylist(){
        Playlist requestPlaylist = new Playlist()
                .setName("Taylor Swift")
                .setDescription("Top Taylor Swift songs")
                .setPublic(true);

        Response response = getMethod("67LASEfGlQjBthaGKlG5ZF");
        assertThat(response.statusCode(), equalTo(200));
        Playlist responsePlaylist = response.as(Playlist.class);
        assertThat(requestPlaylist.getName(), equalTo(responsePlaylist.getName()));
        assertThat(requestPlaylist.getDescription(), equalTo(responsePlaylist.getDescription()));
        assertThat(requestPlaylist.getPublic(),equalTo(responsePlaylist.getPublic()));
    }

    @Test
    public void updatePlaylist(){
        Playlist requestPlaylist = new Playlist()
                .setName("Taylor Swift")
                .setDescription("Top Taylor Swift songs")
                .setPublic(true);

        Response response = putMethod(requestPlaylist,"538lw3X2jsW0v2OJAdmTmY");
        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    public void testCreatePlaylistWithoutName(){
        Playlist requestPlaylist = new Playlist()
                .setName("")
                .setDescription("Updated playlist description")
                .setPublic(true);

        Response response = postMethod(requestPlaylist);
        assertThat(response.statusCode(), equalTo(400));
        Error error = response.as(Error.class);
        assertThat(error.getError().getStatus(), equalTo(400));
        assertThat(error.getError().getMessage(), equalTo("Missing required field: name"));
    }

    @Test
    public void createPlaylistWithExpiredToken(){
        String invalidToken = "12345";
        Playlist requestPlaylist = new Playlist()
                .setName("Taylor Swift")
                .setDescription("Top Taylor Swift songs")
                .setPublic(true);

        Response response = postMethod(requestPlaylist,invalidToken);
        assertThat(response.statusCode(), equalTo(401));
        Error error = response.as(Error.class);
        assertThat(error.getError().getStatus(), equalTo(401));
        assertThat(error.getError().getMessage(), equalTo("Invalid access token"));
    }

}
