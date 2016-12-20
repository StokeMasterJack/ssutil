package com.smartsoft.ssutil;

import com.google.common.base.Preconditions;

public class Bits {

    private Bits() {
    }

    /**
     * Check the value of a bit at the given index.
     *
     * @param index
     * @return
     */
    public static BitGetter isBit(int index) {
        return new BitGetter(index);
    }

    /**
     * Manipulates a bit at the given index
     * @param index index of the bit
     * @param value true set the bit, false clear's it
     * @return
     */
    public static BitManipulator setBit(int index, boolean value) {
        return new BitManipulator(index, value);
    }

    private static class BitOperation {
        final byte bitIndex;

        BitOperation(int index) {
            assertIsBetween(index, 0, 63);
            bitIndex = (byte) index;
        }

        private void assertIsBetween(int index, int min, int max) {
            Preconditions.checkArgument(index >= min && index <= max);
        }

        final void checkIndexMax(int bitSize) {
            assertIsBetween(bitIndex, 0, bitSize - 1);
        }
    }

    public static class BitGetter extends BitOperation {

        private BitGetter(int index) {
            super(index);
        }

        public boolean on(byte value) {
            checkIndexMax(Byte.SIZE);
            return getBitValueOf(value);
        }

        public boolean on(short value) {
            checkIndexMax(Short.SIZE);
            return getBitValueOf(value);
        }

        public boolean on(char value) {
            checkIndexMax(Character.SIZE);
            return getBitValueOf(value);
        }

        public boolean on(int value) {
            checkIndexMax(Integer.SIZE);
            return getBitValueOf(value);
        }

        public boolean on(long value) {
            checkIndexMax(Long.SIZE);
            return getBitValueOf(value);
        }

        private boolean getBitValueOf(long value) {
            return ((value >> bitIndex) & 1) == 1;
        }
    }

    public final static class BitManipulator extends BitOperation {

        private final boolean setValue;

        private BitManipulator(int index,
                               boolean newValue) {

            super(index);
            this.setValue = newValue;

        }

        public byte on(byte value) {
            checkIndexMax(Byte.SIZE);
            return (byte) manipulate(value);
        }

        public short on(short value) {
            checkIndexMax(Short.SIZE);
            return (short) manipulate(value);
        }

        public char of(char value) {
            checkIndexMax(Character.SIZE);
            return (char) manipulate(value);
        }

        public int of(int value) {
            checkIndexMax(Integer.SIZE);
            return (int) manipulate(value);
        }

        public long of(long value) {
            checkIndexMax(Long.SIZE);
            return manipulate(value);
        }

        private long manipulate(long value) {
            final long bitMask = 1 << bitIndex;
            if (setValue)
                return value | bitMask;
            else
                return value & ~bitMask;

        }
    }

    public static void main(String[] args) {



    }
}
