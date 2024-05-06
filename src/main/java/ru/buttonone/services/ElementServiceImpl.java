package ru.buttonone.services;

import lombok.extern.slf4j.Slf4j;
import ru.buttonone.models.TodoApp;
import ru.buttonone.specifications.Specification;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static ru.buttonone.constants.CodConstant.SUCCESSFUL_REQUEST;

@Slf4j
public class ElementServiceImpl implements ElementService {

    @Override
    public List<TodoApp> getElementsList() {
        log.info("Получение всех элементов из базы данных");
        return Arrays.asList(given()
                .spec(Specification.reqSpecMethodGet())
                .when()
                .get()
                .then()
                .body(matchesJsonSchemaInClasspath("todo-schema.json"))
                .statusCode(SUCCESSFUL_REQUEST)
                .extract().body().as(TodoApp[].class));
    }

    @Override
    public TodoApp findElementById(String id) {
        TodoApp instance = getElementsList().stream()
                .filter(todoApp -> new BigInteger(id).equals(todoApp.getId()))
                .findFirst()
                .orElse(null);

        if (instance == null) {
            log.error("Сущность с ID = {} не найдена", id);
            throw new RuntimeException("Сущность с ID = " + id + " не найдена");
        }
        return instance;
    }
}