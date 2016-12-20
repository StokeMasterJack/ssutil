package com.smartsoft.ssutil.args;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;

public class GenericFieldParser<T> implements FieldParser<T> {

    @Override
    public T parseValue(@Nonnull String fieldValue, Class<T> outType) {

        if (outType == String.class) {
            return (T) fieldValue;
        }

        try {
            Constructor<T> constructor = outType.getConstructor(String.class);
            return constructor.newInstance(fieldValue);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
