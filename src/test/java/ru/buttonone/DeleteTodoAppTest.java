package ru.buttonone;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.buttonone.services.ElementServiceImpl;

import java.math.BigInteger;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.buttonone.constants.ApiConstant.ID;
import static ru.buttonone.constants.CodeConstant.*;
import static ru.buttonone.constants.CommonConstant.*;
import static ru.buttonone.specifications.Specification.reqSpecMethodDelete;

@Slf4j
public class DeleteTodoAppTest extends BaseTest {
    public static final String TEST_ID = "21";

    /**
     * Позитивный сценарий Delete
     */

    @DisplayName("Проверка удаления элемента")
    @Test
    public void checkDeleteElement() {
        log.info("Проверка удаления элемента");
        given()
                .spec(reqSpecMethodDelete(new ElementServiceImpl().findElementById(TEST_ID).getId()))
                .auth().preemptive().basic(USERNAME, PASSWORD)
                .when()
                .delete(ID)
                .then()
                .statusCode(SUCCESSFUL_DELETION);

        assertThrows(RuntimeException.class, () -> {
            new ElementServiceImpl().findElementById(TEST_ID).getId();
        }, "Элемент не удалён");
        log.info("Проверка удаления элемента выполнена");
    }

    /**
     * Негативный сценарий Delete
     */

    @DisplayName("Проверка удаления элемента с отрицательным ID")
    @Test
    public void checkDeleteNegativeId() {
        log.info("Проверка удаления элемента с отрицательным ID");
        given()
                .spec(reqSpecMethodDelete(new BigInteger(NEGATIVE_DATA)))
                .auth().preemptive().basic(USERNAME, PASSWORD)
                .when()
                .delete(ID)
                .then()
                .statusCode(NOT_FOUND);
        log.info("Проверка удаления элемента с отрицательным ID выполнена");
    }

    @DisplayName("Проверка удаления элемента с ID превышающий максимум")
    @Test
    public void checkDeleteIdMoreThanMaximum() {
        log.info("Проверка удаления элемента с ID превышающий максимум");
        given()
                .spec(reqSpecMethodDelete(new BigInteger(MORE_MAXIMUM_DATA)))
                .auth().preemptive().basic(USERNAME, PASSWORD)
                .when()
                .delete(ID)
                .then()
                .statusCode(NOT_FOUND);
        log.info("Проверка удаления элемента с ID превышающий максимум выполнена");
    }

    @DisplayName("Проверка удаления несуществующего элемента")
    @Test
    public void checkDeleteNotExistingElement() {
        log.info("Проверка удаления несуществующего элемента");
        given()
                .spec(reqSpecMethodDelete(new BigInteger("5000")))
                .auth().preemptive().basic(USERNAME, PASSWORD)
                .when()
                .delete(ID)
                .then()
                .statusCode(NOT_FOUND);
        log.info("Проверка удаления несуществующего элемента выполнена");
    }

    @DisplayName("Проверка удаления элемента без авторизации")
    @Test
    public void checkDeleteWithoutAuth() {
        log.info("Проверка удаления элемента без авторизации");
        given()
                .spec(reqSpecMethodDelete(new ElementServiceImpl().findElementById("22").getId()))
                .when()
                .delete(ID)
                .then()
                .statusCode(ACCESS_DENIED);
        log.info("Проверка удаления элемента без авторизации выполнена");
    }
}