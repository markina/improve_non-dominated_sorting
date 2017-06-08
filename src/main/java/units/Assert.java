package main.java.units;

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
