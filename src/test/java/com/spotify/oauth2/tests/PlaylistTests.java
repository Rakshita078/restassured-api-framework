package com.spotify.oauth2.tests;

import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.spotify.oauth2.api.applicationAPI.PlaylistApi.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Epic("Spotify Oauth 2.0")
@Feature("Playlist API")
public class PlaylistTests extends BaseTest {

    @Step
    public Playlist playlistBuilder(String name, String description, boolean _public){
        return Playlist.builder()
                .name(name)
                .description(description)
                ._public(_public)
                .build();
    }

    @Step
    public void assertPlaylistEqual(Playlist requestPlaylist,Playlist responsePlaylist){
        assertThat(requestPlaylist.getName(), equalTo(responsePlaylist.getName()));
        assertThat(requestPlaylist.getDescription(), equalTo(responsePlaylist.getDescription()));
        assertThat(requestPlaylist.get_public(),equalTo(responsePlaylist.get_public()));
    }

    @Step
    public void assertError(Error responseError, StatusCode statusCode){
        assertThat(responseError.getError().getStatus(), equalTo(statusCode.code));
        assertThat(responseError.getError().getMessage(), equalTo(statusCode.msg));
    }

    @Story("Create a playlist story")
    @Test(description = " Create a Playlist")
    @Description("This test creates a Spotify Playlist which is public")
    @Link(name = "Website", url = "https://dev.example.com/")
    @Issue("AUTH-123")
    @TmsLink("TMS-456")
    public void createPlaylist(){
        Playlist requestPlaylist = playlistBuilder("Taylor Swift","Top Taylor Swift songs",true);
        Response response = postMethod(requestPlaylist);
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_201.code));
        assertPlaylistEqual(requestPlaylist,response.as(Playlist.class));
    }

    @Test
    public void getPlaylist(){
        Playlist requestPlaylist = playlistBuilder("Taylor Swift","Top Taylor Swift songs",true);
        Response response = getMethod(DataLoader.getInstance().getPlaylistId());
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_200.code));
        assertPlaylistEqual(requestPlaylist,response.as(Playlist.class));
    }

    @Test
    public void updatePlaylist(){
        Playlist requestPlaylist = playlistBuilder("Taylor Swift","Top Taylor Swift songs",true);
        Response response = putMethod(requestPlaylist, DataLoader.getInstance().updatePlaylistId());
        assertThat(response.statusCode(), equalTo(StatusCode.CODE_200.code));
    }

    @Story("Create a playlist story")
    @Test
    public void testCreatePlaylistWithoutName(){
        Playlist requestPlaylist = playlistBuilder("","Updated playlist description",true);
        Response response = postMethod(requestPlaylist);
        assertThat(response.statusCode(), equalTo(400));
        assertError(response.as(Error.class),StatusCode.CODE_400);
    }

    @Story("Create a playlist story")
    @Test
    public void createPlaylistWithExpiredToken(){
        String invalidToken = "12345";
        Playlist requestPlaylist = playlistBuilder("Taylor Swift","Top Taylor Swift songs",true);
        Response response = postMethod(requestPlaylist,invalidToken);
        assertThat(response.statusCode(), equalTo(401));
        assertError(response.as(Error.class),StatusCode.CODE_401);
    }

}
