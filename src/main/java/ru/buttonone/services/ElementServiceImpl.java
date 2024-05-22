package ru.buttonone.services;

import lombok.extern.slf4j.Slf4j;
import ru.buttonone.dto.TodoAppDtoImpl;
import ru.buttonone.models.TodoApp;
import ru.buttonone.specifications.Specification;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static ru.buttonone.constants.ApiConstant.ID;
import static ru.buttonone.constants.CodeConstant.SUCCESSFUL_CREATION;
import static ru.buttonone.constants.CodeConstant.SUCCESSFUL_REQUEST;
import static ru.buttonone.constants.CommonConstant.PASSWORD;
import static ru.buttonone.constants.CommonConstant.USERNAME;
import static ru.buttonone.specifications.Specification.reqSpecMethodDelete;

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
                .statusCode(SUCCESSFUL_REQUEST)
                .extract().body().as(TodoApp[].class));
    }

    @Override
    public TodoApp findElementById(String id) {
        Optional<TodoApp> instance = getElementsList().stream()
                .filter(todoApp -> new BigInteger(id).equals(todoApp.getId()))
                .findFirst();

        return instance.orElseThrow();
    }

    @Override
    public void uploadData() {
        List<TodoApp> dataList = List.of(
                new TodoApp(new BigInteger("1"), "Я объект №1", true),
                new TodoApp(new BigInteger("2"), "Я объект №2", false),
                new TodoApp(new BigInteger("3"), "Я объект №3", true),
                new TodoApp(new BigInteger("4"), "Я объект №4", false),
                new TodoApp(new BigInteger("5"), "Я объект №5", true),
                new TodoApp(new BigInteger("6"), "Я объект №6", false),
                new TodoApp(new BigInteger("7"), "Я объект №7", true),
                new TodoApp(new BigInteger("8"), "Я объект №8", true),
                new TodoApp(new BigInteger("9"), "Я объект №9", false),
                new TodoApp(new BigInteger("10"), "Я объект №10", true),
                new TodoApp(new BigInteger("11"), "Я объект №11", false),
                new TodoApp(new BigInteger("20"), "Я объект №20", true),
                new TodoApp(new BigInteger("21"), "Я объект №21", false),
                new TodoApp(new BigInteger("22"), "Я объект №22", true),
                new TodoApp(new BigInteger("23"), "Я объект №23", false));

        for (TodoApp data : dataList) {
            String id = String.valueOf(data.getId());
            String text = data.getText();
            boolean completed = data.isCompleted();

            given()
                    .spec(Specification.reqSpecMethodPost())
                    .when()
                    .body(new TodoAppDtoImpl().todoAppToDto(id, text, completed))
                    .post()
                    .then()
                    .statusCode(SUCCESSFUL_CREATION);
        }
    }

    @Override
    public void removeData() {
        for (TodoApp data : getElementsList()) {
            given()
                    .spec(reqSpecMethodDelete(data.getId()))
                    .auth().preemptive().basic(USERNAME, PASSWORD)
                    .when()
                    .delete(ID)
                    .then();

        }
    }
}