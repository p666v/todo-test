package ru.buttonone;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.buttonone.dto.TodoAppDtoImpl;

import static io.restassured.RestAssured.given;
import static ru.buttonone.constants.CodeConstant.INCORRECT_INPUT;
import static ru.buttonone.constants.CodeConstant.SUCCESSFUL_CREATION;
import static ru.buttonone.specifications.Specification.reqSpecMethodPost;

@Slf4j
public class CreateTodoAppTest extends BaseTest {
    /**
     * Позитивный сценарий Create
     */

    @DisplayName("Проверка добавления элемента")
    @ParameterizedTest
    @MethodSource("ru.buttonone.TodoAppTestData#checkAddCompletedItemData")
    public void checkAddCompletedElement(String idExpected, String textExpected, boolean completedExpected) {
        log.info("Проверка добавления элемента: {}, {}, {}", idExpected, textExpected, completedExpected);
        given()
                .spec(reqSpecMethodPost())
                .when()
                .body(new TodoAppDtoImpl().todoAppToDto(idExpected, textExpected, completedExpected))
                .post()
                .then()
                .statusCode(SUCCESSFUL_CREATION);
        log.info("Проверка добавления элемента выполнена");
    }

    @DisplayName("Проверка добавления элемента с ID=0")
    @ParameterizedTest
    @MethodSource("ru.buttonone.TodoAppTestData#checkAddElementWithIdZeroData")
    public void checkAddElementWithIdZero(String idExpected, String textExpected, boolean completedExpected) {
        log.info("Проверка добавления элемента с ID=0: {}, {}, {}", idExpected, textExpected, completedExpected);
        given()
                .spec(reqSpecMethodPost())
                .when()
                .body(new TodoAppDtoImpl().todoAppToDto(idExpected, textExpected, completedExpected))
                .post()
                .then()
                .statusCode(SUCCESSFUL_CREATION);
        log.info("Проверка добавления элемента с ID=0 выполнена");
    }

    @DisplayName("Проверка добавления элемента с ID=max")
    @ParameterizedTest
    @MethodSource("ru.buttonone.TodoAppTestData#checkAddElementWithIdMaxData")
    public void checkAddElementWithIdMax(String idExpected, String textExpected, boolean completedExpected) {
        log.info("Проверка добавления элемент элемента с ID=max: {}, {}, {}", idExpected, textExpected, completedExpected);
        given()
                .spec(reqSpecMethodPost())
                .when()
                .body(new TodoAppDtoImpl().todoAppToDto(idExpected, textExpected, completedExpected))
                .post()
                .then()
                .statusCode(SUCCESSFUL_CREATION);
        log.info("Проверка добавления элемента с ID=max выполнена");
    }

    @DisplayName("Проверка добавления элемента без заполненного параметра text")
    @ParameterizedTest
    @MethodSource("ru.buttonone.TodoAppTestData#checkAddElementWithoutCompletedParameterData")
    public void checkAddElementWithoutCompletedParameter(String idExpected, String textExpected, boolean completedExpected) {
        log.info("Проверка добавления элемента без заполненного параметра text: {}, {}, {}", idExpected, textExpected, completedExpected);
        given()
                .spec(reqSpecMethodPost())
                .when()
                .body(new TodoAppDtoImpl().todoAppToDto(idExpected, textExpected, completedExpected))
                .post()
                .then()
                .statusCode(SUCCESSFUL_CREATION);
        log.info("Проверка добавления элемента без заполненного параметра text выполнена");
    }

    /**
     * Негативный сценарий Create
     */

    @DisplayName("Проверка добавления элемента с повторяющимися идентификатором")
    @ParameterizedTest
    @MethodSource("ru.buttonone.TodoAppTestData#checkAddElementWithDuplicateIdData")
    public void checkAddElementWithDuplicateId(String idExpected, String textExpected, boolean completedExpected) {
        log.info("Проверка добавления элемента с повторяющимися идентификатором: {}, {}, {}", idExpected, textExpected, completedExpected);
        given()
                .spec(reqSpecMethodPost())
                .when()
                .body(new TodoAppDtoImpl().todoAppToDto(idExpected, textExpected, completedExpected))
                .post()
                .then()
                .statusCode(INCORRECT_INPUT);
        log.info("Проверка добавления элемента с повторяющимися идентификатором выполнена");
    }

    @DisplayName("Проверка добавления элемента с отрицательным идентификатором")
    @ParameterizedTest
    @MethodSource("ru.buttonone.TodoAppTestData#checkAddElementWithNegativeIdData")
    public void checkAddElementWithNegativeId(String idExpected, String textExpected, boolean completedExpected) {
        log.info("ППроверка добавления элемента с отрицательным идентификатором: {}, {}, {}", idExpected, textExpected, completedExpected);
        given()
                .spec(reqSpecMethodPost())
                .when()
                .body(new TodoAppDtoImpl().todoAppToDto(idExpected, textExpected, completedExpected))
                .post()
                .then()
                .statusCode(INCORRECT_INPUT);
        log.info("Проверка добавления элемента с отрицательным идентификатором выполнена");
    }

    @DisplayName("Проверка добавления элемента с идентификатором, превышающим максимум")
    @ParameterizedTest
    @MethodSource("ru.buttonone.TodoAppTestData#checkAddElementWithIdMoreThanMaximumData")
    public void checkAddElementWithIdMoreThanMaximum(String idExpected, String textExpected, boolean completedExpected) {
        log.info("Проверка добавления элемента с идентификатором, превышающим максимум: {}, {}, {}", idExpected, textExpected, completedExpected);
        given()
                .spec(reqSpecMethodPost())
                .when()
                .body(new TodoAppDtoImpl().todoAppToDto(idExpected, textExpected, completedExpected))
                .post()
                .then()
                .statusCode(INCORRECT_INPUT);
        log.info("Проверка добавления элемента с идентификатором, превышающим максимум выполнена");
    }

    @DisplayName("Проверка добавления элемента без ID")
    @ParameterizedTest
    @MethodSource("ru.buttonone.TodoAppTestData#checkAddElementWithoutIdData")
    public void checkAddElementWithoutId(String textExpected, boolean completedExpected) {
        log.info("Проверка добавления элемента без ID: {}, {}", textExpected, completedExpected);
        given()
                .spec(reqSpecMethodPost())
                .when()
                .body(new TodoAppDtoImpl().todoAppToDtoWithoutId(textExpected, completedExpected))
                .post()
                .then()
                .statusCode(INCORRECT_INPUT);
        log.info("Проверка добавления элемента без ID выполнена");
    }

    @DisplayName("Проверка добавления элемента без Text")
    @ParameterizedTest
    @MethodSource("ru.buttonone.TodoAppTestData#checkAddElementWithoutTextData")
    public void checkAddElementWithoutText(String idExpected, boolean completedExpected) {
        log.info("Проверка добавления элемента без Text: {}, {}", idExpected, completedExpected);
        given()
                .spec(reqSpecMethodPost())
                .when()
                .body(new TodoAppDtoImpl().todoAppToDtoWithoutText(idExpected, completedExpected))
                .post()
                .then()
                .statusCode(INCORRECT_INPUT);
        log.info("Проверка добавления элемента без Text выполнена");
    }

    @DisplayName("Проверка добавления элемента без Completed")
    @ParameterizedTest
    @MethodSource("ru.buttonone.TodoAppTestData#checkAddElementWithoutCompletedData")
    public void checkAddElementWithoutCompleted(String idExpected, String textExpected) {
        log.info("Проверка добавления элемента без Completed: {}, {}", idExpected, textExpected);
        given()
                .spec(reqSpecMethodPost())
                .when()
                .body(new TodoAppDtoImpl().todoAppToDtoWithoutCompleted(idExpected, textExpected))
                .post()
                .then()
                .statusCode(INCORRECT_INPUT);
        log.info("Проверка добавления элемента без Completed выполнена");
    }

    @DisplayName("Проверка добавления элемента без body")
    @Test
    public void checkAddElementWithoutBody() {
        log.info("Проверка добавления элемента без body");
        given()
                .spec(reqSpecMethodPost())
                .when()
                .post()
                .then()
                .statusCode(INCORRECT_INPUT);
        log.info("Проверка добавления элемента без body выполнена");
    }
}