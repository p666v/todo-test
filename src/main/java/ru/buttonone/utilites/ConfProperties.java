package ru.buttonone.utilites;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfProperties {
    private final static Properties PROPERTIES = new Properties();

    static {
        try {
            PROPERTIES.load(ConfProperties.class
                    .getClassLoader()
                    .getResourceAsStream("config.properties"));
        } catch (IOException e) {
            log.error("Ошибка при загрузке файла 'config.properties'!");
            throw new RuntimeException();
        }
    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }
}