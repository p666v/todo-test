package ru.buttonone;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class TodoAppTestData {

    static Stream<Arguments> checkAddCompletedItemData() {
        return Stream.of(Arguments.of("201", "Полная загрузка", true));
    }

    static Stream<Arguments> checkAddElementWithIdZeroData() {
        return Stream.of(Arguments.of("0", "ID Zero", false));
    }

    static Stream<Arguments> checkAddElementWithIdMaxData() {
        return Stream.of(Arguments.of("18446744073709551615", "ID Max", true));
    }

    static Stream<Arguments> checkAddElementWithoutCompletedParameterData() {
        return Stream.of(Arguments.of("302", "", true));
    }

    static Stream<Arguments> checkAddElementWithDuplicateIdData() {
        return Stream.of(Arguments.of("1", "Я объект №1", true));
    }

    static Stream<Arguments> checkAddElementWithNegativeIdData() {
        return Stream.of(Arguments.of("-1", "ID Negative", false));
    }

    static Stream<Arguments> checkAddElementWithIdMoreThanMaximumData() {
        return Stream.of(Arguments.of("18446744073709551616", "ID more than the maximum", false));
    }

    static Stream<Arguments> checkAddElementWithoutIdData() {
        return Stream.of(Arguments.of("ID is not present", false));
    }

    static Stream<Arguments> checkAddElementWithoutTextData() {
        return Stream.of(Arguments.of("508", false));
    }

    static Stream<Arguments> checkAddElementWithoutCompletedData() {
        return Stream.of(Arguments.of("509", "Completed is not present"));
    }
}