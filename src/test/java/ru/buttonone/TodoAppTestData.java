package ru.buttonone;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class TodoAppTestData {

    static Stream<Arguments> checkAddCompletedItemData() {
        String id = "201";
        String text = "Полная загрузка";
        boolean completed = true;
        return Stream.of(Arguments.of(id, text, completed));
    }

    static Stream<Arguments> checkAddElementWithIdZeroData() {
        String id = "0";
        String text = "ID Zero";
        boolean completed = false;
        return Stream.of(Arguments.of(id, text, completed));
    }

    static Stream<Arguments> checkAddElementWithIdMaxData() {
        String id = "18446744073709551615";
        String text = "ID Max";
        boolean completed = true;
        return Stream.of(Arguments.of(id, text, completed));
    }

    static Stream<Arguments> checkAddElementWithoutCompletedParameterData() {
        String id = "302";
        String text = "";
        boolean completed = true;
        return Stream.of(Arguments.of(id, text, completed));
    }

    static Stream<Arguments> checkAddElementWithDuplicateIdData() {
        String id = "1";
        String text = "Я объект №1";
        boolean completed = true;
        return Stream.of(Arguments.of(id, text, completed));
    }

    static Stream<Arguments> checkAddElementWithNegativeIdData() {
        String id = "-1";
        String text = "ID Negative";
        boolean completed = false;
        return Stream.of(Arguments.of(id, text, completed));
    }

    static Stream<Arguments> checkAddElementWithIdMoreThanMaximumData() {
        String id = "18446744073709551616";
        String text = "ID more than the maximum";
        boolean completed = false;
        return Stream.of(Arguments.of(id, text, completed));
    }

    static Stream<Arguments> checkAddElementWithoutIdData() {
        String id = "507";
        String text = "ID is not present";
        boolean completed = false;
        return Stream.of(Arguments.of(id, text, completed));
    }

    static Stream<Arguments> checkAddElementWithoutTextData() {
        String id = "508";
        String text = "Text is not present";
        boolean completed = false;
        return Stream.of(Arguments.of(id, text, completed));
    }

    static Stream<Arguments> checkAddElementWithoutCompletedData() {
        String id = "509";
        String text = "Completed is not present";
        boolean completed = false;
        return Stream.of(Arguments.of(id, text, completed));
    }
}