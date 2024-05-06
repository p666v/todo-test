package ru.buttonone.services;

import ru.buttonone.models.TodoApp;

import java.util.List;

public interface ElementService {

    List<TodoApp> getElementsList();

    TodoApp findElementById(String id);
}