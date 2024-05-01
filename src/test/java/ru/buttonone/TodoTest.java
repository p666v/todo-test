package ru.buttonone;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import ru.buttonone.models.TodoInstance;
import ru.buttonone.services.TodoInstanceService;
import ru.buttonone.services.TodoInstanceServiceImpl;
import ru.buttonone.specifications.Specification;

import static io.restassured.RestAssured.given;
import static ru.buttonone.constants.CommonConstant.INCORRECT_INPUT;
import static ru.buttonone.constants.CommonConstant.SUCCESSFUL_CREATION;

@Slf4j
public class TodoTest {
    private final TodoInstanceService instance = new TodoInstanceServiceImpl();
    private TodoInstance todoInstance;

    /**
     * Тест запускается первым, для формирования сущностей для тестирования
     */
    @Disabled
    @DisplayName("Добавление элементов с валидными данными")
    @ParameterizedTest
    @CsvFileSource(resources = "/createTestData.csv", delimiter = ';')
    public void addElementWithValidData(String idExpected, String textExpected, boolean completedExpected) {
        log.info("Добавления элемент с валидными данными: {}, {}, {}", idExpected, textExpected, completedExpected);
        given()
                .spec(Specification.requestSpecWithoutAuthorization())
                .when()
                .body(instance.createTodoInstance(idExpected, textExpected, completedExpected))
                .post()
                .then()
                .statusCode(SUCCESSFUL_CREATION);
        log.info("Добавление элементов addElementWithValidData выполнено");
    }

    /**
     * Позитивный сценарий Create
     */

    @DisplayName("Проверка добавления элемента с полными данными")
    @ParameterizedTest
    @MethodSource("ru.buttonone.TodoTestData#checkAddCompletedItemData")
    public void checkAddCompletedItem(String idExpected, String textExpected, boolean completedExpected) {
        log.info("Проверка добавления элемента с полными данными: {}, {}, {}", idExpected, textExpected, completedExpected);
        given()
                .spec(Specification.requestSpecWithoutAuthorization())
                .when()
                .body(instance.createTodoInstance(idExpected, textExpected, completedExpected))
                .post()
                .then()
                .statusCode(SUCCESSFUL_CREATION);
        log.info("Проверка checkAddCompletedItem выполнена");
    }

    @DisplayName("Проверка добавления элемента с ID=0")
    @ParameterizedTest
    @MethodSource("ru.buttonone.TodoTestData#checkAddElementWithIdZeroData")
    public void checkAddElementWithIdZero(String idExpected, String textExpected, boolean completedExpected) {
        log.info("Проверка добавления элемента с ID=0: {}, {}, {}", idExpected, textExpected, completedExpected);
        given()
                .spec(Specification.requestSpecWithoutAuthorization())
                .when()
                .body(instance.createTodoInstance(idExpected, textExpected, completedExpected))
                .post()
                .then()
                .statusCode(SUCCESSFUL_CREATION);
        log.info("Проверка checkAddElementWithIdZero выполнена");
    }

    @DisplayName("Проверка добавления элемента с ID=max")
    @ParameterizedTest
    @MethodSource("ru.buttonone.TodoTestData#checkAddElementWithIdMaxData")
    public void checkAddElementWithIdMax(String idExpected, String textExpected, boolean completedExpected) {
        log.info("Проверка добавления элемент элемента с ID=max: {}, {}, {}", idExpected, textExpected, completedExpected);
        given()
                .spec(Specification.requestSpecWithoutAuthorization())
                .when()
                .body(instance.createTodoInstance(idExpected, textExpected, completedExpected))
                .post()
                .then()
                .statusCode(SUCCESSFUL_CREATION);
        log.info("Проверка checkAddElementWithIdMax выполнена");
    }

    @DisplayName("Проверка добавления элемента без заполненного параметра text")
    @ParameterizedTest
    @MethodSource("ru.buttonone.TodoTestData#checkAddElementWithoutCompletedParameterData")
    public void checkAddElementWithoutCompletedParameter(String idExpected, String textExpected, boolean completedExpected) {
        log.info("Проверка добавления элемента без заполненного параметра text: {}, {}, {}", idExpected, textExpected, completedExpected);
        given()
                .spec(Specification.requestSpecWithoutAuthorization())
                .when()
                .body(instance.createTodoInstance(idExpected, textExpected, completedExpected))
                .post()
                .then()
                .statusCode(SUCCESSFUL_CREATION);
        log.info("Проверка checkAddElementWithoutCompletedParameter выполнена");
    }

    /**
     * Негативный сценарий Create
     */

    @DisplayName("Проверка добавления элемента с повторяющимися идентификатором")
    @ParameterizedTest
    @MethodSource("ru.buttonone.TodoTestData#checkAddElementWithDuplicateIdData")
    public void checkAddElementWithDuplicateId(String idExpected, String textExpected, boolean completedExpected) {
        log.info("Проверка добавления элемента с повторяющимися идентификатором: {}, {}, {}", idExpected, textExpected, completedExpected);
        given()
                .spec(Specification.requestSpecWithoutAuthorization())
                .when()
                .body(instance.createTodoInstance(idExpected, textExpected, completedExpected))
                .post()
                .then()
                .statusCode(INCORRECT_INPUT);
        log.info("Проверка checkAddElementWithDuplicateId выполнена");
    }

    @DisplayName("Проверка добавления элемента с отрицательным идентификатором")
    @ParameterizedTest
    @MethodSource("ru.buttonone.TodoTestData#checkAddElementWithNegativeIdData")
    public void checkAddElementWithNegativeId(String idExpected, String textExpected, boolean completedExpected) {
        log.info("ППроверка добавления элемента с отрицательным идентификатором: {}, {}, {}", idExpected, textExpected, completedExpected);
        given()
                .spec(Specification.requestSpecWithoutAuthorization())
                .when()
                .body(instance.createTodoInstance(idExpected, textExpected, completedExpected))
                .post()
                .then()
                .statusCode(INCORRECT_INPUT);
        log.info("Проверка checkAddElementWithNegativeId выполнена");
    }

    @DisplayName("Проверка добавления элемента с идентификатором, превышающим максимум")
    @ParameterizedTest
    @MethodSource("ru.buttonone.TodoTestData#checkAddElementWithIdMoreThanMaximumData")
    public void checkAddElementWithIdMoreThanMaximum(String idExpected, String textExpected, boolean completedExpected) {
        log.info("Проверка добавления элемента с идентификатором, превышающим максимум: {}, {}, {}", idExpected, textExpected, completedExpected);
        given()
                .spec(Specification.requestSpecWithoutAuthorization())
                .when()
                .body(instance.createTodoInstance(idExpected, textExpected, completedExpected))
                .post()
                .then()
                .statusCode(INCORRECT_INPUT);
        log.info("Проверка checkAddElementWithIdMoreThanMaximum выполнена");
    }

    @DisplayName("Проверка добавления элемента без ID")
    @ParameterizedTest
    @MethodSource("ru.buttonone.TodoTestData#checkAddElementWithoutIdData")
    public void checkAddElementWithoutId(String idExpected, String textExpected, boolean completedExpected) {
        log.info("Проверка добавления элемента без ID: {}, {}", textExpected, completedExpected);
        given()
                .spec(Specification.requestSpecWithoutAuthorization())
                .when()
                .body(instance.createTodoTextCompleted(idExpected, textExpected, completedExpected))
                .post()
                .then()
                .statusCode(INCORRECT_INPUT);
        log.info("Проверка checkAddElementWithoutId выполнена");
    }

    @DisplayName("Проверка добавления элемента без Text")
    @ParameterizedTest
    @MethodSource("ru.buttonone.TodoTestData#checkAddElementWithoutTextData")
    public void checkAddElementWithoutText(String idExpected, String textExpected, boolean completedExpected) {
        log.info("Проверка добавления элемента без Text: {}, {}", idExpected, completedExpected);
        given()
                .spec(Specification.requestSpecWithoutAuthorization())
                .when()
                .body(instance.createTodoIdCompleted(idExpected, textExpected, completedExpected))
                .post()
                .then()
                .statusCode(INCORRECT_INPUT);
        log.info("Проверка checkAddElementWithoutText выполнена");
    }

    @DisplayName("Проверка добавления элемента без Completed")
    @ParameterizedTest
    @MethodSource("ru.buttonone.TodoTestData#checkAddElementWithoutCompletedData")
    public void checkAddElementWithoutCompleted(String idExpected, String textExpected, boolean completedExpected) {
        log.info("Проверка добавления элемента без Completed: {}, {}", idExpected, textExpected);
        given()
                .spec(Specification.requestSpecWithoutAuthorization())
                .when()
                .body(instance.createTodoIdText(idExpected, textExpected, completedExpected))
                .post()
                .then()
                .statusCode(INCORRECT_INPUT);
        log.info("Проверка checkAddElementWithoutCompleted выполнена");
    }

    @DisplayName("Проверка добавления элемента без body")
    @Test
    public void checkAddElementWithoutBody() {
        log.info("Проверка добавления элемента без body");
        given()
                .spec(Specification.requestSpecWithoutAuthorization())
                .when()
                .post()
                .then()
                .statusCode(INCORRECT_INPUT);
        log.info("Проверка checkAddElementWithoutBody выполнена");
    }


}