package ru.buttonone.constants;

import static ru.buttonone.utilites.ConfProperties.getProperty;

public final class CommonConstant {
    public static final String MAXIMUM_ID = "18446744073709551615";
    public static final String MINIMUM_ID = "0";
    public static final String NEGATIVE_DATA = "-1";
    public static final String MORE_MAXIMUM_DATA = "18446744073709551616";
    public final static String USERNAME = getProperty("username");
    public final static String PASSWORD = getProperty("password");
}