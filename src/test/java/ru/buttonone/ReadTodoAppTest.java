package ru.buttonone;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.buttonone.models.TodoApp;
import ru.buttonone.services.ElementService;
import ru.buttonone.services.ElementServiceImpl;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.*;
import static ru.buttonone.constants.ApiConstant.LIMIT;
import static ru.buttonone.constants.ApiConstant.OFFSET;
import static ru.buttonone.constants.CodConstant.INCORRECT_INPUT;
import static ru.buttonone.constants.CodConstant.SUCCESSFUL_REQUEST;
import static ru.buttonone.constants.CommonConstant.*;
import static ru.buttonone.specifications.Specification.*;

@Slf4j
public class ReadTodoAppTest {
    private final ElementService elementService = new ElementServiceImpl();
    private List<TodoApp> todoAppList;

    /**
     * Позитивный сценарий Read
     */

    @DisplayName("Проверка получения элементов")
    @Test
    public void checkReturnElements() {
        log.info("Проверка получения элементов");
        given()
                .spec(reqSpecMethodGet())
                .when()
                .get()
                .then()
                .body(matchesJsonSchemaInClasspath("todo-schema.json"))
                .statusCode(SUCCESSFUL_REQUEST);
        log.info("Проверка получения элементов выполнена");
    }

    @DisplayName("Проверка limit")
    @Test
    public void checkLimit() {
        log.info("Проверка limit");
        todoAppList = Arrays.asList(given()
                .spec(reqSpecMethodGetWithParameter(LIMIT, "1"))
                .when()
                .get()
                .then()
                .spec(resSpecMethodGetWithStatus(SUCCESSFUL_REQUEST))
                .extract().body().as(TodoApp[].class));

        assertEquals(1, todoAppList.size(),
                "Возвращаемый список элементов не соответствует limit, переданному в запросе");
        log.info("Проверка limit выполнена");
    }

    @DisplayName("Проверка limit = 0")
    @Test
    public void checkLimitWithZero() {
        log.info("Проверка limit = 0");
        todoAppList = Arrays.asList(given()
                .spec(reqSpecMethodGetWithParameter(LIMIT, MINIMUM_ID))
                .when()
                .get()
                .then()
                .spec(resSpecMethodGetWithStatus(SUCCESSFUL_REQUEST))
                .extract().body().as(TodoApp[].class));

        assertTrue(todoAppList.isEmpty(),
                "Возвращаемый список элементов не соответствует limit = 0, переданному в запросе");
        log.info("Проверка limit = 0 выполнена");
    }

    @DisplayName("Проверка limit = max")
    @Test
    public void checkLimitMax() {
        log.info("Проверка limit = max");
        todoAppList = Arrays.asList(given()
                .spec(reqSpecMethodGetWithParameter(LIMIT, MAXIMUM_ID))
                .when()
                .get()
                .then()
                .spec(resSpecMethodGetWithStatus(SUCCESSFUL_REQUEST))
                .extract().body().as(TodoApp[].class));

        assertEquals(elementService.getElementsList().size(), todoAppList.size(),
                "Возвращаемый список элементов не соответствует limit = max, переданному в запросе");
        log.info("Проверка limit = max выполнена");
    }

    @DisplayName("Проверка offset")
    @Test
    public void checkOffset() {
        log.info("Проверка offset");
        todoAppList = Arrays.asList(given()
                .spec(reqSpecMethodGetWithParameter(OFFSET, "2"))
                .when()
                .get()
                .then()
                .spec(resSpecMethodGetWithStatus(SUCCESSFUL_REQUEST))
                .extract().body().as(TodoApp[].class));

        assertEquals(elementService.getElementsList().get(2), todoAppList.get(0),
                "Возвращаемый список элементов не соответствует offset, переданному в запросе");
        log.info("Проверка offset выполнена");
    }

    @DisplayName("Проверка offset = 0")
    @Test
    public void checkOffsetWithZero() {
        log.info("Проверка offset = 0");
        todoAppList = Arrays.asList(given()
                .spec(reqSpecMethodGetWithParameter(OFFSET, MINIMUM_ID))
                .when()
                .get()
                .then()
                .spec(resSpecMethodGetWithStatus(SUCCESSFUL_REQUEST))
                .extract().body().as(TodoApp[].class));

        assertEquals(elementService.getElementsList().size(), todoAppList.size(),
                "Возвращаемый список элементов не соответствует offset = 0, переданному в запросе");
        log.info("Проверка offset = 0 выполнена");
    }

    @DisplayName("Проверка offset = max")
    @Test
    public void checkOffsetMax() {
        log.info("Проверка offset = max");
        todoAppList = Arrays.asList(given()
                .spec(reqSpecMethodGetWithParameter(OFFSET, MAXIMUM_ID))
                .when()
                .get()
                .then()
                .spec(resSpecMethodGetWithStatus(SUCCESSFUL_REQUEST))
                .extract().body().as(TodoApp[].class));

        assertTrue(todoAppList.isEmpty(),
                "Возвращаемый список элементов не соответствует offset = max, переданному в запросе");
        log.info("Проверка offset = max выполнена");
    }

    @DisplayName("Проверка совместной работы offset и limit")
    @Test
    public void checkCollaborationOffsetAndLimit() {
        log.info("Проверка совместной работы offset и limit");
        todoAppList = Arrays.asList(given()
                .spec(reqSpecMethodGetWithOffsetAndLimit("1", "1"))
                .when()
                .get()
                .then()
                .spec(resSpecMethodGetWithStatus(SUCCESSFUL_REQUEST))
                .extract().body().as(TodoApp[].class));

        assertAll(
                () -> assertEquals(1, todoAppList.size(),
                        "Возвращаемый список элементов не соответствует limit, переданному в запросе"),
                () -> assertEquals(elementService.getElementsList().get(1), todoAppList.get(0),
                        "Возвращаемый список элементов не соответствует offset, переданному в запросе"));
        log.info("Проверка совместной работы offset и limit выполнена");
    }

    @DisplayName("Проверка совместной работы offset и limit с максимальными значениями")
    @Test
    public void checkOffsetAndLimitWithMaximum() {
        log.info("Проверка совместной работы offset и limit с максимальными значениями");
        todoAppList = Arrays.asList(given()
                .spec(reqSpecMethodGetWithOffsetAndLimit(MAXIMUM_ID, MAXIMUM_ID))
                .when()
                .get()
                .then()
                .spec(resSpecMethodGetWithStatus(SUCCESSFUL_REQUEST))
                .extract().body().as(TodoApp[].class));

        assertTrue(todoAppList.isEmpty(),
                "Возвращаемый список элементов не соответствует offset и limit с максимальными значениями, переданными в запросе");
        log.info("Проверка совместной работы offset и limit с максимальными значениями выполнена");
    }

    @DisplayName("Проверка совместной работы offset и limit с минимальными значениями")
    @Test
    public void checkOffsetAndLimitWithMinimum() {
        log.info("Проверка совместной работы offset и limit с минимальными значениями");
        todoAppList = Arrays.asList(given()
                .spec(reqSpecMethodGetWithOffsetAndLimit(MINIMUM_ID, MINIMUM_ID))
                .when()
                .get()
                .then()
                .spec(resSpecMethodGetWithStatus(SUCCESSFUL_REQUEST))
                .extract().body().as(TodoApp[].class));

        assertTrue(todoAppList.isEmpty(),
                "Возвращаемый список элементов не соответствует offset и limit с максимальными значениями, переданными в запросе");
        log.info("Проверка совместной работы offset и limit с минимальными значениями выполнена");
    }

    /**
     * Негативный сценарий Read
     */

    @DisplayName("Проверка limit = -1")
    @Test
    public void checkNegativeLimit() {
        log.info("Проверка limit = -1");
        given()
                .spec(reqSpecMethodGetWithParameter(LIMIT, NEGATIVE_DATA))
                .when()
                .get()
                .then()
                .statusCode(INCORRECT_INPUT);
        log.info("Проверка limit = -1 выполнена");
    }

    @DisplayName("Проверка limit больше максимума")
    @Test
    public void checkLimitMoreMaximum() {
        log.info("Проверка limit больше максимума");
        given()
                .spec(reqSpecMethodGetWithParameter(LIMIT, MORE_MAXIMUM_DATA))
                .when()
                .get()
                .then()
                .statusCode(INCORRECT_INPUT);
        log.info("Проверка limit больше максимума выполнена");
    }

    @DisplayName("Проверка offset = -1")
    @Test
    public void checkNegativeOffset() {
        log.info("Проверка offset = -1");
        given()
                .spec(reqSpecMethodGetWithParameter(OFFSET, NEGATIVE_DATA))
                .when()
                .get()
                .then()
                .statusCode(INCORRECT_INPUT);
        log.info("Проверка offset = -1 выполнена");
    }

    @DisplayName("Проверка offset больше максимума")
    @Test
    public void checkOffsetMoreMaximum() {
        log.info("Проверка offset больше максимума");
        given()
                .spec(reqSpecMethodGetWithParameter(OFFSET, MORE_MAXIMUM_DATA))
                .when()
                .get()
                .then()
                .statusCode(INCORRECT_INPUT);
        log.info("Проверка offset больше максимума выполнена");
    }

    @DisplayName("Проверка совместной работы offset и limit с отрицательными значениями")
    @Test
    public void checkNegativeOffsetAndLimit() {
        log.info("Проверка совместной работы offset и limit с отрицательными значениями");
        given()
                .spec(reqSpecMethodGetWithOffsetAndLimit(NEGATIVE_DATA, NEGATIVE_DATA))
                .when()
                .get()
                .then()
                .statusCode(INCORRECT_INPUT);
        log.info("Проверка совместной работы offset и limit с отрицательными значениями выполнена");
    }

    @DisplayName("Проверка совместной работы offset и limit со значениями больше максимума")
    @Test
    public void checkOffsetAndLimitMoreMaximum() {
        log.info("Проверка совместной работы offset и limit со значениями больше максимума");
        given()
                .spec(reqSpecMethodGetWithOffsetAndLimit(MORE_MAXIMUM_DATA, MORE_MAXIMUM_DATA))
                .when()
                .get()
                .then()
                .statusCode(INCORRECT_INPUT);
        log.info("Проверка совместной работы offset и limit со значениями больше максимума выполнена");
    }
}