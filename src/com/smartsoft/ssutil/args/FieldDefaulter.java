package com.smartsoft.ssutil.args;

import javax.annotation.Nonnull;

public interface FieldDefaulter<T> {

    @Nonnull
    T getDefaultValue(T defaultValue, CommandLineArgs context);

}
