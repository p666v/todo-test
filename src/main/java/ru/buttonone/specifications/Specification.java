package ru.buttonone.specifications;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static ru.buttonone.constants.ApiConstant.BASE_URL;

public class Specification {

    public static RequestSpecification requestSpecWithoutAuthorization() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .log(LogDetail.METHOD)
                .log(LogDetail.BODY)
                .addFilter(new AllureRestAssured())
                .build();
    }
}