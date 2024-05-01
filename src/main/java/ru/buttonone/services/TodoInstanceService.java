package ru.buttonone.services;

public interface TodoInstanceService {

    String createTodoInstance(String idExpected, String textExpected, boolean completedExpected);

    String createTodoIdText(String idExpected, String textExpected, boolean completedExpected);

    String createTodoIdCompleted(String idExpected, String textExpected, boolean completedExpected);

    String createTodoTextCompleted(String idExpected, String textExpected, boolean completedExpected);
}