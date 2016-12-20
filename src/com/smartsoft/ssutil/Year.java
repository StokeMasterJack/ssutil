package com.smartsoft.ssutil;

public class Year {

    private int value;

    public Year(int value) {
        if (value > 9999 || value < 1000) {
            throw new IllegalArgumentException("Bad year value: " + value);
        }
        this.value = value;
    }

    public Year(){
        this.value = new Date().getYear();
    }

    public boolean isLeap() {
        return (value % 4) == 0;
    }

    public int getDayCount() {
        if (isLeap()) return 366;
        else return 365;
    }

    public void next() {
        value++;
    }


    public String toString() {
        return value + "";
    }

    public void previous() {
        value--;
    }

    public Year copy() {
        return new Year(this.value);
    }

    public int intValue() {
        return value;
    }

    public Date getFirstDayOfYear() {
        return new Date(value, 1, 1);
    }

}

