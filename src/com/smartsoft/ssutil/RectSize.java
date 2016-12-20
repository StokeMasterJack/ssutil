package com.smartsoft.ssutil;

import javax.annotation.concurrent.Immutable;
import java.io.Serializable;

@Immutable
public class RectSize implements Serializable {

    private static final long serialVersionUID = -6018887310406228906L;

    public static final RectSize STD_PNG = new RectSize(599, 366);

    private /* final */ int width;
    private /* final */ int height;

    public RectSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    private RectSize() {
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getWidthAsDouble() {
        return (double) width;
    }

    public double getHeightAsDouble() {
        return (double) height;
    }

    public double getWidthHeightRatio() {
        return getWidthAsDouble() / getHeightAsDouble();
    }

    public RectSize scaleToWidth(int newWidth) {
        double whRatio = getWidthHeightRatio();
        double newHeightDouble = newWidth / whRatio;
        double newHeightRounded = (double) Math.round(newHeightDouble);

        int newHeightInt = (int) newHeightRounded;

        double roundingError = newHeightDouble - newHeightRounded;
        if (roundingError != 0D) {
            String msg = "Height rounded from " + newHeightDouble + " to " + newHeightRounded;
            System.out.println(msg);
//            throw new IllegalStateException(msg);
        }
        return new RectSize(newWidth, newHeightInt);

    }

    @Override
    public String toString() {
        return "ImageSize{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RectSize that = (RectSize) o;
        return height == that.height && width == that.width;
    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        return result;
    }

    public String getKey() {
        return width + "_" + height;
    }

    public static void main(String[] args) {
        RectSize stdPng = STD_PNG;
        RectSize rectSize = stdPng.scaleToWidth(480);
        System.out.println("rectSize = " + rectSize);
    }
}
