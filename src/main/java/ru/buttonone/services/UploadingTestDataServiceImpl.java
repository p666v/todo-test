package ru.buttonone.services;

import ru.buttonone.dto.TodoAppDto;
import ru.buttonone.dto.TodoAppDtoImpl;
import ru.buttonone.models.TodoApp;
import ru.buttonone.specifications.Specification;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static ru.buttonone.constants.CodConstant.SUCCESSFUL_CREATION;

public class UploadingTestDataServiceImpl implements UploadingTestDataService {

    @Override
    public void uploadData() {
        TodoAppDto instance = new TodoAppDtoImpl();

        List<TodoApp> dataList = Arrays.asList(
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
                    .body(instance.todoAppToDto(id, text, completed))
                    .post()
                    .then()
                    .statusCode(SUCCESSFUL_CREATION);
        }
    }
}