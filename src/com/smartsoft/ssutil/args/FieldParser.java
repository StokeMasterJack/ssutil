package com.smartsoft.ssutil.args;

import javax.annotation.Nonnull;

public interface FieldParser<T> {

    T parseValue(@Nonnull String fieldValue, Class<T> outType);
}
