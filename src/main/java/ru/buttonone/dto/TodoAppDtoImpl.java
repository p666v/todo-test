package ru.buttonone.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.extern.slf4j.Slf4j;
import ru.buttonone.models.TodoApp;

import java.math.BigInteger;

@Slf4j
public class TodoAppDtoImpl implements TodoAppDto {

    @Override
    public String todoAppToDto(String idExpected, String textExpected, boolean completedExpected) {
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("myFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id", "text", "completed"));
        TodoApp todoApp = new TodoApp(new BigInteger(idExpected), textExpected, completedExpected);

        return convertObjectToJason(filters, todoApp);
    }

    @Override
    public String todoAppToDtoWithoutCompleted(String idExpected, String textExpected, boolean completedExpected) {
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("myFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id", "text"));
        TodoApp todoApp = new TodoApp(new BigInteger(idExpected), textExpected, completedExpected);

        return convertObjectToJason(filters, todoApp);
    }

    @Override
    public String todoAppToDtoWithoutText(String idExpected, String textExpected, boolean completedExpected) {
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("myFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id", "completed"));
        TodoApp todoApp = new TodoApp(new BigInteger(idExpected), textExpected, completedExpected);

        return convertObjectToJason(filters, todoApp);
    }

    @Override
    public String todoAppToDtoWithoutId(String idExpected, String textExpected, boolean completedExpected) {
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("myFilter", SimpleBeanPropertyFilter.filterOutAllExcept("text", "completed"));
        TodoApp todoApp = new TodoApp(new BigInteger(idExpected), textExpected, completedExpected);

        return convertObjectToJason(filters, todoApp);
    }

    @Override
    public String todoAppToDtoEmpty() {
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("myFilter", SimpleBeanPropertyFilter.filterOutAllExcept());
        TodoApp todoApp = new TodoApp();

        return convertObjectToJason(filters, todoApp);
    }

    private String convertObjectToJason(FilterProvider filters, TodoApp todoApp) {
        String todoInstanceJson;
        try {
            todoInstanceJson = new ObjectMapper().writer(filters).writeValueAsString(todoApp);
        } catch (JsonProcessingException e) {
            log.error("Ошибка при мапинге объекта в JASON: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return todoInstanceJson;
    }


}