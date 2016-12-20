package com.smartsoft.ssutil;

import java.io.Serializable;

public final class Count2 implements Serializable {

    private long value;

    public Count2() {
        this(0L);
    }

    public Count2(long value) {
        this.value = value;
    }

    public long get() {
        return value;
    }

    public long getAndAdd(long delta) {
        long result = value;
        value = result + delta;
        return result;
    }

    public long addAndGet(long delta) {
        return value += delta;
    }

    public void set(long newValue) {
        value = newValue;
    }

    public long getAndSet(long newValue) {
        long result = value;
        value = newValue;
        return result;
    }

    /**
     *
     * @return the new value
     */
    public long increment() {
        value++;
        return value;
    }

    public long increment(long amount) {
        value += amount;
        return value;
    }

    /**
     *
     * @return the new value
     */
    public long decrement() {
        value--;
        return value;
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }


    public boolean isZero() {
        return value == 0L;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Count2 count2 = (Count2) o;
        return value == count2.value;
    }

    @Override
    public int hashCode() {
        return (int) (value ^ (value >>> 32));
    }
}

