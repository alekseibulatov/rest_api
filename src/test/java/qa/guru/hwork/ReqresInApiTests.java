package qa.guru.hwork;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import qa.guru.hwork.models.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static qa.guru.hwork.specs.LoginSpecs.loginRequestSpec;
import static qa.guru.hwork.specs.LoginSpecs.responseSpecification;


public class ReqresInApiTests {

    @Test
    @Tag("API")
    @DisplayName("Проверка  что на 2 странице в body содержит 6 пользователей")
    public void requestGetListUser() {
        ListUsersModel listUsers = given(loginRequestSpec)
                .when()
                .get("/users?page=2")
                .then()
                .spec(responseSpecification(200))
                .body("data.findAll{it.first_name}.first_name.flatten()", hasItem("Lindsay"))
                .extract().as(ListUsersModel.class);
        assertThat(listUsers.getPage(), is(2));
        assertThat(listUsers.getData().size(), is(6));
    }

    @Test
    @Tag("API")
    @DisplayName("Проверка отсутвия пользователя в списке")
    public void requestGetSingleNotFound() {
        given(loginRequestSpec)
                .when()
                .get("/unknown/23")
                .then()
                .spec(responseSpecification(404));
    }

    @Test
    @Tag("API")
    @DisplayName("Проверка изменения пользователя")
    public void requestPatch() {
        CreateUpdateUserModel data = new CreateUpdateUserModel();
        data.setName("morpheus");
        data.setJob("zion resident");

        UpdateUserModel updateUserModel = given(loginRequestSpec)
                .body(data)
                .when()
                .patch("/users/2")
                .then()
                .spec(responseSpecification(200))
                .extract().as(UpdateUserModel.class);
        assertThat(updateUserModel.getName(), is("morpheus"));
        assertThat(updateUserModel.getJob(), is("zion resident"));
        assertThat(updateUserModel.getUpdatedAt(), is(notNullValue()));
    }

    @Test
    @Tag("API")
    @DisplayName("Проверка удаления пользователя")
    public void requestDelete() {
        given(loginRequestSpec)
                .when()
                .delete("/users/2")
                .then()
                .spec(responseSpecification(204));
    }

    @Test
    @Tag("API")
    @DisplayName("Проверка регистрации пользователя")
    public void registerTest() {
        CreateRegisterUserModel data = new CreateRegisterUserModel();
        data.setEmail("eve.holt@reqres.in");
        data.setPassword("pistol");

        RegisterUserModel registerUserModel = given(loginRequestSpec)
                .body(data)
                .when()
                .post("/register")
                .then()
                .spec(responseSpecification(200))
                .extract().as(RegisterUserModel.class);
        assertThat(registerUserModel.getId(), is(4));
        assertThat(registerUserModel.getToken(), is("QpwL5tke4Pnpja7X4"));
    }
}
