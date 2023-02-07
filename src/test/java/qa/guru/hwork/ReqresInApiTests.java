package qa.guru.hwork;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static qa.guru.hwork.specs.LoginSpecs.loginRequestSpec;
import static qa.guru.hwork.specs.LoginSpecs.responseSpecification;


public class ReqresInApiTests {

    @Test
    @DisplayName("Проверка  что на 2 странице в body содержит 6 пользователей")
    public void requestGetListUser() {
        given(loginRequestSpec)
                .when()
                .get("/users?page=2")
                .then()
                .spec(responseSpecification(200))
                .body("page", is(2))
                .body("size()", equalTo(6));
    }

    @Test
    @DisplayName("Проверка отсутвия пользователя в списке")
    public void requestGetSingleNotFound() {
        given(loginRequestSpec)
                .when()
                .get("/unknown/23")
                .then()
                .spec(responseSpecification(404));
    }

    @Test
    @DisplayName("Проверка изменения пользователя")
    public void requestPatch() {
        String data = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";

        given(loginRequestSpec)
                .body(data)
                .when()
                .patch("/users/2")
                .then()
                .spec(responseSpecification(200))
                .body("name", is("morpheus"))
                .body("job", is("zion resident"))
                .body("updatedAt", is(notNullValue()));
    }

    @Test
    @DisplayName("Проверка удаления пользователя")
    public void requestDelete() {
        given(loginRequestSpec)
                .when()
                .delete("/users/2")
                .then()
                .spec(responseSpecification(204));
    }

    @Test
    @DisplayName("Проверка регистрации пользователя")
    public void registerTest() {
        String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";

        given(loginRequestSpec)
                .body(data)
                .when()
                .post("/register")
                .then()
                .spec(responseSpecification(200))
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }
}
