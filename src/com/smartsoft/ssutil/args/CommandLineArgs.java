package com.smartsoft.ssutil.args;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.smartsoft.ssutil.Console.prindent;

/**
 * Supports args of format: --key1=value1   --key2=value2
 */
public class CommandLineArgs {

    private final Schema schema;
    private final String[] args;
    private final Map<String, String> raw;

    public CommandLineArgs(Schema schema, String... args) {
        this.schema = schema;
        this.args = args;
        raw = new HashMap<String, String>();

        if (args != null) {
            for (String arg : args) {
                if (arg.matches("--[^\\W]+=[^\\s]+")) {
                    String kv = arg.replaceFirst("--", "");
                    int i = kv.indexOf('=');
                    String key = kv.substring(0, i);
                    String value = kv.substring(i + 1);
                    raw.put(key, value);
                } else {
                    throw new IllegalArgumentException("Unsupported arg format: " + arg);
                }
            }
        }

    }

    public <T> T get(String name) {
        return get(null, name);
    }

    public <T> T get(Class<T> type, String name) {
        String userValue = raw.get(name);
        Field<T> field = schema.getOrCreateField(type, name);
        return field.getValue(this, userValue);
    }

    public String getString(String name) {
        return get(String.class, name);
    }

    public Integer getInteger(String name) {
        return get(Integer.class, name);
    }

    public Map<String, String> getDefaults() {
        Set<String> mergedFieldNames = raw.keySet();
        mergedFieldNames.addAll(schema.getFieldNames());

        HashMap<String, String> defaults = new HashMap<String, String>();

        for (String fieldName : mergedFieldNames) {
            Field<Object> field = schema.getField(fieldName);
            Object defaultValue = field.getDefaultValue(this);

            if (defaultValue != null) {
                defaults.put(fieldName, defaultValue.toString());
            } else {
                defaults.put(fieldName, null);
            }
        }
        return defaults;
    }

    public Map<String, String> getMerged() {
        Set<String> mergedFieldNames = raw.keySet();
        mergedFieldNames.addAll(schema.getFieldNames());

        HashMap<String, String> merged = new HashMap<String, String>();

        for (String fieldName : mergedFieldNames) {
            Object value = get(fieldName);
            if (value != null) {
                merged.put(fieldName, value.toString());
            } else {
                merged.put(fieldName, null);
            }
        }
        return merged;
    }

    public String putRaw(String fieldName, String fieldValue) {
        return raw.put(fieldName, fieldValue);
    }

    public <T> CommandLineArgs putDefault(String fieldName, T defaultValue) {
        if (defaultValue != null) {
            Class<T> cls = (Class<T>) defaultValue.getClass();
            Field<T> field = schema.getOrCreateField(cls, fieldName);
            field.setDefaultValue(defaultValue);
            return this;
        } else {
            if (schema.containsField(fieldName)) {
                Field<T> field = schema.getField(fieldName);
                field.setDefaultValue(null);
            }
        }
        return this;
    }

    public void print() {
        System.out.println("raw = " + raw);
        System.out.println("defaults = " + getDefaults());
        System.out.println("merged = " + getMerged());
        System.out.println();
    }

    @Override
    public String toString() {
        return "CommandLineArgs{" +
                "raw=" + raw +
                ", defaults=" + getDefaults() +
                ", merged=" + getMerged() +
                '}';
    }


    public Map<String, String> getRaw() {
        return raw;
    }

    public void printOptions(int depth) {
        for (Map.Entry<String, String> entry : getDefaults().entrySet()) {
            prindent(depth, "--" + entry.getKey() + "=" + entry.getValue());
        }
    }

    public void printMerged(int depth) {
        Map<String, String> merged = getMerged();
        for (Map.Entry<String, String> entry : merged.entrySet()) {
            prindent(depth, "--" + entry.getKey() + "=" + entry.getValue());
        }
    }

    public static CommandLineArgs parse(Schema schema, String[] args) {
        return new CommandLineArgs(schema, args);
    }

    public static CommandLineArgs parse(String[] args) {
        return new CommandLineArgs(new Schema(), args);
    }
}
