package com.hybrid.sorter.units;

public class Assert {

    public static void check(boolean v) {
        if(!v) {
            throw new AssertionError();
        }
    }

    public static void check(boolean v, String st) {
        if(!v) {
            throw new AssertionError(st);
        }
    }

}
