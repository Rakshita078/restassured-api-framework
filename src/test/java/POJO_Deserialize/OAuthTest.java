package POJO_Deserialize;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class OAuthTest {

    @Test
    public void testOAuth2_0() throws InterruptedException {
        String courseTitles[] = {"Selenium Webdriver Java",
                "Cypress",
                "Protractor",
        };

//        String chromeDriverPath = System.getProperty("user.dir") + "/chromedriver";
//        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
//        WebDriver driver = new ChromeDriver();
//        driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifysbgf");
//        driver.findElement(By.id("identifierId")).sendKeys("rakshita0778");
//        driver.findElement(By.id("identifierId")).sendKeys(Keys.ENTER);
//        Thread.sleep(5000);
//        driver.findElement(By.cssSelector("#password > div.aCsJod.oJeWuf > div > div.Xb9hP > input")).sendKeys("password");
//        driver.findElement(By.cssSelector("#password > div.aCsJod.oJeWuf > div > div.Xb9hP > input")).sendKeys(Keys.ENTER);
        // String url = driver.getCurrentUrl();
        String url = "https://rahulshettyacademy.com/getCourse.php?state=verifysbgf&code=4%2F0AX4XfWh3FPe-Zbhx7J-IkvyGQgQaNLTFwjMkU_3fr0LY4joGNHSJMkV8FdqtCJLRXR7DDA&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none#";
        String partialCode = url.split("code=")[1];
        String code = partialCode.split("&scope")[0];
        System.out.println(code);


        String accessTokenResponse = given().log().all().urlEncodingEnabled(false)
                .queryParams("code", code)
                .queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                .queryParams("grant_type", "authorization_code")
                .when().post("https://www.googleapis.com/oauth2/v4/token").asString();
        System.out.println("accessTokenResponse" + accessTokenResponse);

        JsonPath js = new JsonPath(accessTokenResponse);
        String accessToken = js.getString("access_token");
        System.out.println("access Token: " + accessToken);


        GetCourse gc = given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
                .when()
                .get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
        System.out.println(gc.getLinkedIn());
        System.out.println(gc.getInstructor());

        System.out.println( gc.getCourses().getApi().get(1).getCourseTitle());
        List<Api> apiCourses = gc.getCourses().getApi();
        for(int i=0; i<apiCourses.size(); i++)
        {
            if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")){
                System.out.println( apiCourses.get(i).getPrice());
            }
        }
        System.out.println(gc.getCourses().getWebAutomation().get(1).getCourseTitle());

        ArrayList<String> a = new ArrayList<>();
        List<WebAutomation> webAutomationCourses = gc.getCourses().getWebAutomation();
        for(int i=0; i<webAutomationCourses.size(); i++)
        {
                a.add( webAutomationCourses.get(i).getCourseTitle());
            }
        List<String> expectedList = Arrays.asList(courseTitles);
        //Assert.assertEquals(a,expectedList);
        Assert.assertTrue(a.equals(expectedList));
        }
    }




