package ru.buttonone.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.extern.slf4j.Slf4j;
import ru.buttonone.models.TodoInstance;

import java.math.BigInteger;

@Slf4j
public class TodoInstanceServiceImpl implements TodoInstanceService {

    @Override
    public String createTodoInstance(String idExpected, String textExpected, boolean completedExpected) {
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("myFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id", "text", "completed"));
        TodoInstance todoInstance = new TodoInstance(new BigInteger(idExpected), textExpected, completedExpected);

        return convertingObjectToJason(filters, todoInstance);
    }

    @Override
    public String createTodoIdText(String idExpected, String textExpected, boolean completedExpected) {
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("myFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id", "text"));
        TodoInstance todoInstance = new TodoInstance(new BigInteger(idExpected), textExpected, completedExpected);

        return convertingObjectToJason(filters, todoInstance);
    }

    @Override
    public String createTodoIdCompleted(String idExpected, String textExpected, boolean completedExpected) {
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("myFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id", "completed"));
        TodoInstance todoInstance = new TodoInstance(new BigInteger(idExpected), textExpected, completedExpected);

        return convertingObjectToJason(filters, todoInstance);
    }

    @Override
    public String createTodoTextCompleted(String idExpected, String textExpected, boolean completedExpected) {
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("myFilter", SimpleBeanPropertyFilter.filterOutAllExcept("text", "completed"));
        TodoInstance todoInstance = new TodoInstance(new BigInteger(idExpected), textExpected, completedExpected);

        return convertingObjectToJason(filters, todoInstance);
    }

    private String convertingObjectToJason(FilterProvider filters, TodoInstance todoInstance) {
        String todoInstanceJson;
        try {
            todoInstanceJson = new ObjectMapper().writer(filters).writeValueAsString(todoInstance);
        } catch (JsonProcessingException e) {
            log.error("Ошибка при мапинге объекта в JASON: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return todoInstanceJson;
    }
}