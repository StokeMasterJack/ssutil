package com.smartsoft.ssutil.args;

import javax.annotation.Nonnull;

public class Field<T> {

    private Class<T> type;
    private String name;

    private T defaultValue;
    private FieldDefaulter<T> fieldDefaulter;
    private FieldParser<T> fieldParser;

    public Field(Class<T> type, String name, T defaultValue, FieldParser<T> fieldParser, FieldDefaulter<T> fieldDefaulter) {
        this.type = type;
        this.name = name;
        this.defaultValue = defaultValue;
        this.fieldParser = fieldParser == null ? new GenericFieldParser<T>() : fieldParser;
        this.fieldDefaulter = fieldDefaulter;
    }

    public Field(Class<T> type, String name, T defaultValue, FieldParser<T> fieldParser) {
        this(type, name, defaultValue, fieldParser, null);
    }

    public Field(Class<T> type, String name, T defaultValue) {
        this(type, name, defaultValue, null);
    }


    public Field(String name, @Nonnull T defaultValue) {
        this((Class<T>) defaultValue.getClass(), name, defaultValue, null);
    }

    public Field(Class<T> type, String name) {
        this(type, name, null);
    }

    public String getName() {
        return name;
    }

    public Class<T> getType() {
        return type;
    }

    public T getDefaultValue(CommandLineArgs context) {
        if (fieldDefaulter != null) {
            return fieldDefaulter.getDefaultValue(this.defaultValue, context);
        } else {
            return defaultValue;
        }
    }

    public T getValue(CommandLineArgs context, String userValue) throws IllegalArgumentException {
        if (userValue == null) {
            T fd = getDefaultValue(context);
            if (fd != null) {
                return fd;
            } else {
                return null;
            }
        } else {
            return fieldParser.parseValue(userValue, type);
        }
    }

    public boolean isMatch(String name) {
        return this.name.equalsIgnoreCase(name);
    }

    public <T> boolean isMatch(Class<T> type, String name) {
        return this.name.equalsIgnoreCase(name) && this.type == type;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

    public Field<T> setDefaultValue(T defaultValue) {
        if (defaultValue != null) {
            if (type == null) {
                type = (Class<T>) defaultValue.getClass();
            }
        }
        return this;
    }

    public Field<T> setFieldDefaulter(FieldDefaulter<T> fieldDefaulter) {
        this.fieldDefaulter = fieldDefaulter;
        return this;
    }
}
