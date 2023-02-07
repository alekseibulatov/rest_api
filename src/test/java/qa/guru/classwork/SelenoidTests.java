package qa.guru.classwork;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SelenoidTests {
     /*
        1. Make request to https://selenoid.autotests.cloud/status
        2. Get response { total: 20, used: 0, queued: 0, pending: 0, browsers: { android: { 8.1: { } },
            chrome: { 100.0: { }, 99.0: { } }, chrome-mobile: { 86.0: { } }, firefox: { 97.0: { }, 98.0: { } },
            opera: { 84.0: { }, 85.0: { } } } }
        3. Check total is 20
     */

    @Test
    public void checkTotal() {
        get("https://selenoid.autotests.cloud/status")
                .then()
                .body("total", is(20));
    }

    @Test
    public void checkTotalWithStatus() {
        get("https://selenoid.autotests.cloud/status")
                .then()
                .statusCode(200)
                .body("total", is(20));
    }

    @Test
    public void checkTotalWithGiven() {
        given()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .statusCode(200)
                .body("total", is(20));
    }

    @Test
    public void checkTotalWithLogs() {
        given()
                .log().all()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("total", is(20));
    }

    @Test
    public void checkTotalWithSomeLogs() {
        given()
                .log().uri()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("total", is(20));
    }

    @Test
    public void checkChromeVersion() {
        given()
                .log().uri()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .body("browsers.chrome", hasKey("100.0"));
    }

    @Test
    void checkResponseBadPractice() {
        String expectedResponseString = "{\"total\":20,\"used\":0,\"queued\":0,\"pending\":0,\"browsers\":" +
                "{\"android\":{\"8.1\":{}}," +
                "\"chrome\":{\"100.0\":{},\"99.0\":{}}," +
                "\"chrome-mobile\":{\"86.0\":{}}," +
                "\"firefox\":{\"97.0\":{},\"98.0\":{}}," +
                "\"opera\":{\"84.0\":{},\"85.0\":{}}}}\n";

        Response actualResponse = given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().response();

        assertEquals(expectedResponseString, actualResponse.asString());
    }

    @Test
    void checkResponseGoodPractice() {
        Integer expectedTotal = 20;

        Integer actualTotal = given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().path("total");

        assertEquals(expectedTotal, actualTotal);
    }


     /*
        1. Make request to https://selenoid.autotests.cloud/wd/hub/status
        2. Get response { value: { message: "Selenoid 1.10.7 built at 2021-11-21_05:46:32AM", ready: true } }
        3. Check value.ready is true
     */

    @Test
    void checkWdHubStatus401() {
        given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(401);
    }

    @Test
    void checkWdHubStatus() {
        given()
                .log().uri()
                .when()
                .get("https://user1:1234@selenoid.autotests.cloud/wd/hub/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("value.ready", is(true));
    }

    @Test
    void checkWdHubWithAuthStatus() {
        given()
                .log().uri()
                .auth().basic("user1", "1234")
                .when()
                .get("https://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("value.ready", is(true));
    }
}
