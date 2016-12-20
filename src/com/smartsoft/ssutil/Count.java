package com.smartsoft.ssutil;

import javax.annotation.Nullable;
import java.io.Serializable;

public final class Count implements Serializable {

    private int value;

    public Count() {
        this(0);
    }

    public Count(int value) {
        this.value = value;
    }

    public int get() {
        return value;
    }

    public int getAndAdd(int delta) {
        int result = value;
        value = result + delta;
        return result;
    }

    public int addAndGet(int delta) {
        return value += delta;
    }

    public void set(int newValue) {
        value = newValue;
    }

    public int getAndSet(int newValue) {
        int result = value;
        value = newValue;
        return result;
    }

    /**
     *
     * @return the new value
     */
    public int increment() {
        value++;
        return value;
    }

    /**
     *
     * @return the new value
     */
    public int decrement() {
        value--;
        return value;
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof Count && ((Count) obj).value == value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }


    public boolean isZero() {
        return value == 0;
    }
}

