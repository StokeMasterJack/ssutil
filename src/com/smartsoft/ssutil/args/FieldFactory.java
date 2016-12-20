package com.smartsoft.ssutil.args;

public abstract class FieldFactory<T> {

    protected final Class<T> type;

    protected FieldFactory(Class<T> type) {
        this.type = type;
    }

    public Class<T> getType() {
        return type;
    }

    public abstract Field<T> createField(String fieldName, T defaultValue);

    public Field<T> createField(String fieldName) {
        return createField(fieldName, null);
    }

}
