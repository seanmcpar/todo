package com.sean.todo.service;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertTypeHelper {

    private ConvertTypeHelper() {
    }

    public static  <T> T convertType(Object value, Class<T> type) {
        if (type.isInstance(value)) {
            return type.cast(value);
        } else if (type == Date.class && value instanceof String) {
            try {
                return type.cast(new SimpleDateFormat("yyyy-MM-dd").parse((String) value));
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid date format: " + value);
            }
        }
        throw new IllegalArgumentException("Unsupported type conversion for: " + value);
    }

}
