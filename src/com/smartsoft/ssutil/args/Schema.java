package com.smartsoft.ssutil.args;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

public class Schema {

//    private final StockFieldFactory factory = new StockFieldFactory();

    private Map<String, Field<?>> fieldDefs = new HashMap<String, Field<?>>();

    //Class<T> type, String name, T defaultValue, FieldParser<T> fieldParser, FieldDefaulter<T> fieldDefaulter

    public <T> Schema addField(Class<T> type, String name, T defaultValue, FieldParser<T> fieldParser, FieldDefaulter<T> fieldDefaulter) {
        return put(new Field<T>(type, name, defaultValue, fieldParser, fieldDefaulter));
    }

    public <T> Schema addField(Class<T> type, String name, T defaultValue, FieldParser<T> fieldParser) {
        return put(new Field<T>(type, name, defaultValue, fieldParser));
    }


    public <T> Schema addField(Class<T> type, String name, T defaultValue) {
        return put(new Field<T>(type, name, defaultValue));
    }

    public <T> Schema addField(Class<T> type, String name, FieldDefaulter<T> fieldDefaulter) {
        Field<T> field = new Field<T>(type, name);
        field.setFieldDefaulter(fieldDefaulter);
        return put(field);
    }

    public <T> Schema addField(Class<T> type, String name) {
        return put(new Field<T>(type, name));
    }

    public <T> Schema addField(String name, T defaultValue) {
        return put(new Field<T>(name, defaultValue));
    }

    public Schema addField(String name) {
        return put(new Field<String>(String.class, name));
    }


    public Schema addField(String name, String defaultValue) {
        return put(new Field(String.class, name, defaultValue));
    }

    public <T> Schema addField(Field<T> field) {
        return put(field);
    }

    public <T> Schema put(Field<T> field) {
        fieldDefs.put(field.getName(), field);
        return this;
    }

    public boolean containsField(String name) {
        return fieldDefs.containsKey(name);
    }

    @Nonnull
    public <T> Field<T> getField(String name) throws IllegalArgumentException {
        if (!containsField(name)) {
            throw new IllegalArgumentException("No fieldDef[" + name + "]");
        }
        Field<T> field = (Field<T>) fieldDefs.get(name);
        assert field != null;
        return field;
    }

    @Nonnull
    public <T> Field<T> getOrCreateField(@Nonnull Class<T> type, String name) {

        Field<?> field = fieldDefs.get(name);
        if (field != null) {
            checkState(field.getClass() == type);
            return (Field<T>) field;
        }

        //no user-defined field for the type, try using a stock field handler
        field = new Field<T>(type, name);
        addField(field);
        put(field);
        return (Field<T>) field;
    }


    public Set<String> getFieldNames() {
        HashSet<String> set = new HashSet<String>();
        for (String fieldName : fieldDefs.keySet()) {
            set.add(fieldName);
        }
        return set;
    }

//    public <T> Schema setFieldDefaulter(String fieldName, FieldDefaulter<T> fieldDefaulter) {
//        Field<T> field = getField(fieldName);
//        field.setFieldDefaulter(fieldDefaulter);
//        return this;
//    }
}
