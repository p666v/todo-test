package ru.buttonone.specifications;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.math.BigInteger;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static ru.buttonone.constants.ApiConstant.*;

public class Specification {

    public static RequestSpecification reqSpecMethodPost() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .log(LogDetail.METHOD)
                .log(LogDetail.BODY)
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .build();
    }

    public static RequestSpecification reqSpecMethodGet() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .log(LogDetail.METHOD)
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .build();
    }

    public static ResponseSpecification resSpecMethodGetWithStatus(int code) {
        return new ResponseSpecBuilder()
                .expectBody(matchesJsonSchemaInClasspath("todo-schema.json"))
                .expectStatusCode(code)
                .build();
    }

    public static RequestSpecification reqSpecMethodGetWithParameter(String nameParameter, String parameter) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .log(LogDetail.METHOD)
                .setContentType(ContentType.JSON)
                .addParam(nameParameter, new BigInteger(parameter))
                .addFilter(new AllureRestAssured())
                .build();
    }

    public static RequestSpecification reqSpecMethodGetWithOffsetAndLimit(String paramLimit, String paramOffset) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .log(LogDetail.METHOD)
                .setContentType(ContentType.JSON)
                .addParam(LIMIT, new BigInteger(paramLimit))
                .addParam(OFFSET, new BigInteger(paramOffset))
                .addFilter(new AllureRestAssured())
                .build();
    }
}