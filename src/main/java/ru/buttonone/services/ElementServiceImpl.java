package ru.buttonone.services;

import lombok.extern.slf4j.Slf4j;
import ru.buttonone.models.TodoApp;
import ru.buttonone.specifications.Specification;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static ru.buttonone.constants.CodConstant.SUCCESSFUL_REQUEST;

@Slf4j
public class ElementServiceImpl implements ElementService {

    @Override
    public List<TodoApp> getElements() {
        log.info("Получение всех элементов из базы данных");
        return new ArrayList<>(given()
                .spec(Specification.reqSpecMethodGet())
                .when()
                .get()
                .then()
                .body(matchesJsonSchemaInClasspath("todo-schema.json"))
                .statusCode(SUCCESSFUL_REQUEST)
                .extract().as(List.class));
    }
}