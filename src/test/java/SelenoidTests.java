import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

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
}
