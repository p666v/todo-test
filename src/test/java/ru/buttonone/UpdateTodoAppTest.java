package ru.buttonone;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.buttonone.dto.TodoAppDto;
import ru.buttonone.dto.TodoAppDtoImpl;
import ru.buttonone.models.TodoApp;
import ru.buttonone.services.ElementService;
import ru.buttonone.services.ElementServiceImpl;

import java.math.BigInteger;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static ru.buttonone.constants.ApiConstant.ID;
import static ru.buttonone.constants.CodConstant.*;
import static ru.buttonone.constants.CommonConstant.*;
import static ru.buttonone.specifications.Specification.reqSpecMethodPut;

@Slf4j
public class UpdateTodoAppTest  {
    public static final String ID_EXPECTED = "18";
    public static final String TEXT_EXPECTED = "Изменён";
    public static final boolean COMPLETED_EXPECTED = true;
    private final TodoAppDto todoAppDto = new TodoAppDtoImpl();
    private final ElementService elementService = new ElementServiceImpl();

    /**
     * Позитивный сценарий Read
     */

    @DisplayName("Проверка внесение изменений в Text и Completed")
    @Test
    public void checkUpdateTextCompleted() {
        log.info("Проверка внесение изменений в Text и Completed");
        TodoApp elementBefore = elementService.findElementById("8");

        given()
                .spec(reqSpecMethodPut(elementBefore.getId()))
                .auth().preemptive().basic(USERNAME, PASSWORD)
                .when()
                .body(todoAppDto.todoAppToDto(String.valueOf(elementBefore.getId()), TEXT_EXPECTED, COMPLETED_EXPECTED))
                .put(ID)
                .then()
                .statusCode(SUCCESSFUL_REQUEST);

        TodoApp elementAfter = elementService.findElementById(String.valueOf(elementBefore.getId()));
        assertAll(
                () -> assertEquals(elementBefore.getId(), elementAfter.getId()),
                () -> assertEquals(TEXT_EXPECTED, elementAfter.getText()),
                () -> assertTrue(elementAfter.isCompleted())
        );
        log.info("Проверка внесение изменений в Text и Completed выполнена");
    }

    @DisplayName("Проверка внесение изменений в ID")
    @Test
    public void checkUpdateId() {
        log.info("Проверка внесение изменений в ID");
        TodoApp elementBefore = elementService.findElementById("2");

        given()
                .spec(reqSpecMethodPut(elementBefore.getId()))
                .auth().preemptive().basic(USERNAME, PASSWORD)
                .when()
                .body(todoAppDto.todoAppToDto(ID_EXPECTED, TEXT_EXPECTED, COMPLETED_EXPECTED))
                .put(ID)
                .then()
                .statusCode(SUCCESSFUL_REQUEST);

        TodoApp elementAfter = elementService.findElementById(ID_EXPECTED);
        assertAll(
                () -> assertThrows(RuntimeException.class, () -> {
                    elementService.findElementById(String.valueOf(elementBefore.getId()));
                }),
                () -> assertEquals(ID_EXPECTED, String.valueOf(elementAfter.getId())),
                () -> assertEquals(TEXT_EXPECTED, elementAfter.getText()),
                () -> assertTrue(elementAfter.isCompleted())
        );
        log.info("Проверка внесение изменений в ID выполнена");
    }

    /**
     * Негативный сценарий Read
     */

    @DisplayName("Проверка передачи body без ID")
    @Test
    public void checkUpdateFieldWithoutId() {
        log.info("Проверка передачи body без ID");
        TodoApp elementBefore = elementService.findElementById("10");

        given()
                .spec(reqSpecMethodPut(elementBefore.getId()))
                .auth().preemptive().basic(USERNAME, PASSWORD)
                .when()
                .body(todoAppDto.todoAppToDtoWithoutId(ID_EXPECTED, TEXT_EXPECTED, COMPLETED_EXPECTED))
                .put(ID)
                .then()
                .statusCode(INCORRECT_INPUT);
        log.info("Проверка передачи body без ID выполнена");
    }

    @DisplayName("Проверка передачи body без Text")
    @Test
    public void checkUpdateFieldWithoutText() {
        log.info("Проверка передачи body без Text");
        TodoApp elementBefore = elementService.findElementById("10");

        given()
                .spec(reqSpecMethodPut(elementBefore.getId()))
                .auth().preemptive().basic(USERNAME, PASSWORD)
                .when()
                .body(todoAppDto.todoAppToDtoWithoutText(ID_EXPECTED, TEXT_EXPECTED, COMPLETED_EXPECTED))
                .put(ID)
                .then()
                .statusCode(INCORRECT_INPUT);
        log.info("Проверка передачи body без Text выполнена");
    }

    @DisplayName("Проверка передачи body без Completed")
    @Test
    public void checkUpdateFieldWithoutCompleted() {
        log.info("Проверка передачи body без Completed");
        TodoApp elementBefore = elementService.findElementById("10");

        given()
                .spec(reqSpecMethodPut(elementBefore.getId()))
                .auth().preemptive().basic(USERNAME, PASSWORD)
                .when()
                .body(todoAppDto.todoAppToDtoWithoutCompleted(ID_EXPECTED, TEXT_EXPECTED, COMPLETED_EXPECTED))
                .put(ID)
                .then()
                .statusCode(INCORRECT_INPUT);
        log.info("Проверка передачи body без Completed выполнена");
    }

    @DisplayName("Проверка передачи запроса без body")
    @Test
    public void checkUpdateWithoutBody() {
        log.info("Проверка передачи запроса без body");
        TodoApp elementBefore = elementService.findElementById("10");

        given()
                .spec(reqSpecMethodPut(elementBefore.getId()))
                .auth().preemptive().basic(USERNAME, PASSWORD)
                .when()
                .put(ID)
                .then()
                .statusCode(INCORRECT_INPUT);
        log.info("Проверка передачи запроса без body выполнена");
    }

    @DisplayName("Проверка выполнения запроса без авторизации")
    @Test
    public void checkUpdateWithoutAuth() {
        log.info("Проверка выполнения запроса без авторизации");
        TodoApp elementBefore = elementService.findElementById("10");

        given()
                .spec(reqSpecMethodPut(elementBefore.getId()))
                .when()
                .body(todoAppDto.todoAppToDtoWithoutCompleted("10", TEXT_EXPECTED, COMPLETED_EXPECTED))
                .put(ID)
                .then()
                .statusCode(ACCESS_DENIED);
        log.info("Проверка выполнения запроса без авторизации выполнена");
    }

    @DisplayName("Проверка выполнения запроса с пустым body")
    @Test
    public void checkUpdateBodyEmpty() {
        log.info("Проверка выполнения запроса с пустым body");
        TodoApp elementBefore = elementService.findElementById("11");

        given()
                .spec(reqSpecMethodPut(elementBefore.getId()))
                .auth().preemptive().basic(USERNAME, PASSWORD)
                .when()
                .body(todoAppDto.todoAppToDtoEmpty())
                .put(ID)
                .then()
                .statusCode(INCORRECT_INPUT);
        log.info("Проверка выполнения запроса с пустым body выполнена");
    }

    @DisplayName("Проверка выполнения запроса к несуществующей сущности")
    @Test
    public void checkUpdateNotExistingElement() {
        log.info("Проверка выполнения запроса к несуществующей сущности");

        given()
                .spec(reqSpecMethodPut(new BigInteger("10102")))
                .auth().preemptive().basic(USERNAME, PASSWORD)
                .when()
                .body(todoAppDto.todoAppToDto("10102", TEXT_EXPECTED, COMPLETED_EXPECTED))
                .put(ID)
                .then()
                .statusCode(NOT_FOUND);
        log.info("Проверка выполнения запроса к несуществующей сущности выполнена");
    }

    @DisplayName("Проверка дублирования ID")  //todo Баг в приложении
    @Test
    public void checkUpdateDuplicateId() {
        log.info("Проверка дублирования ID");
        TodoApp elementBefore1 = elementService.findElementById("5");
        BigInteger elementBefore2 = elementService.findElementById("6").getId();

        given()
                .spec(reqSpecMethodPut(elementBefore1.getId()))
                .auth().preemptive().basic(USERNAME, PASSWORD)
                .when()
                .body(todoAppDto.todoAppToDto(String.valueOf(elementBefore2), TEXT_EXPECTED, COMPLETED_EXPECTED))
                .put(ID)
                .then()
                .statusCode(INCORRECT_INPUT);
        log.info("Проверка дублирования ID выполнена");
    }

    @DisplayName("Проверка замены ID на отрицательный идентификатор")
    @Test
    public void checkUpdateNegativeId() {
        log.info("Проверка замены ID на отрицательный идентификатор");
        TodoApp elementBefore = elementService.findElementById("3");

        given()
                .spec(reqSpecMethodPut(elementBefore.getId()))
                .auth().preemptive().basic(USERNAME, PASSWORD)
                .when()
                .body(todoAppDto.todoAppToDto(NEGATIVE_DATA, TEXT_EXPECTED, COMPLETED_EXPECTED))
                .put(ID)
                .then()
                .statusCode(INCORRECT_INPUT);
        log.info("Проверка замены ID на отрицательный идентификатор выполнена");
    }

    @DisplayName("Проверка замены ID на идентификатор, превышающий максимум")
    @Test
    public void checkUpdateIdMoreThanMaximum() {
        log.info("Проверка замены ID на идентификатор, превышающий максимум");
        TodoApp elementBefore = elementService.findElementById("3");

        given()
                .spec(reqSpecMethodPut(elementBefore.getId()))
                .auth().preemptive().basic(USERNAME, PASSWORD)
                .when()
                .body(todoAppDto.todoAppToDto(MORE_MAXIMUM_DATA, TEXT_EXPECTED, COMPLETED_EXPECTED))
                .put(ID)
                .then()
                .statusCode(INCORRECT_INPUT);
        log.info("Проверка замены ID на идентификатор, превышающий максимум выполнена");
    }
}