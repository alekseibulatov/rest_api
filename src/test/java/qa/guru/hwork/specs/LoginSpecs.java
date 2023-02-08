package qa.guru.hwork.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;
import static qa.guru.hwork.helpers.CustomApiListener.withCustomTemplates;

public class LoginSpecs {
    public static RequestSpecification loginRequestSpec = with()
            .log().uri()
            .log().headers()
            .log().body()
            .filter(withCustomTemplates())
            .contentType(JSON)
            .baseUri("https://reqres.in")
            .basePath("/api");



    public static ResponseSpecification responseSpecification(int status) {
        return new ResponseSpecBuilder()
                .log(ALL)
                .expectStatusCode(status)
                .build();
    }
}
