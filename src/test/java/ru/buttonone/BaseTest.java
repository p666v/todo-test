package ru.buttonone;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import ru.buttonone.services.ElementServiceImpl;

public class BaseTest {

    @BeforeAll
    public static void createTestData() {
        new ElementServiceImpl().uploadData();
    }

    @AfterAll
    public static void deleteTestData() {
        new ElementServiceImpl().removeData();
    }
}