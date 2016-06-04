import units.Assert;

import java.io.Writer;

public class TestsEvaluation {

    public static void main(String[] args) throws Exception {
        Imitation im;
        String dir = "../../PycharmProjects/improve_non-dominated_sorting/experiments-3/";
        String F1 = "1fronts", F2 = "2fronts", CUBE = "cube";
        int N = 100000;

        for(int i = 3; i < 11; i++) {
            im = new Imitation(N, i, F1, dir);
            System.out.println("F1. N = " + N + " M = " + i);
            System.out.println("find: " + im.find());
        }

        for(int i = 3; i < 11; i++) {
            im = new Imitation(N, i, F2, dir);
            System.out.println("F2. N = " + N + " M = " + i);
            System.out.println("find: " + im.find());
        }

        for(int i = 3; i < 11; i++) {
            im = new Imitation(N, i, CUBE, dir);
            System.out.println("CUBE. N = " + N + " M = " + i);
            System.out.println("find: " + im.find());
        }



        for(int i = 15; i < 31; i += 5) {
            im = new Imitation(N, i, F1, dir);
            System.out.println("F1. N = " + N + " M = " + i);
            System.out.println("find: " + im.find());
        }

        for(int i = 15; i < 31; i += 5) {
            im = new Imitation(N, i, F2, dir);
            System.out.println("F2. N = " + N + " M = " + i);
            System.out.println("find: " + im.find());
        }

        for(int i = 15; i < 31; i += 5) {
            im = new Imitation(N, i, CUBE, dir);
            System.out.println("CUBE. N = " + N + " M = " + i);
            System.out.println("find: " + im.find());
        }

//
//        im = new Imitation(100000, 10, "1fronts", dir);
//        System.out.println("find: " + im.find());
//        System.out.println("exp:  200-400");
//
//        System.out.println("--");
//
//        im = new Imitation(100000, 15, "1fronts", dir);
//        System.out.println("find: " + im.find());
//        System.out.println("exp:  400");
//
//        System.out.println("--");
//
//        im = new Imitation(100000, 20, "1fronts", dir);
//        System.out.println("find: " + im.find());
//        System.out.println("exp:  400-800");
//
//        System.out.println("--");
//
//        im = new Imitation(100000, 25, "1fronts", dir);
//        System.out.println("find: " + im.find());
//        System.out.println("exp:  800");
//
//        System.out.println("--");
//
//        im = new Imitation(100000, 3, "1fronts", dir);
//        System.out.println("find: " + im.find());
//        System.out.println("exp:  5-15");
//
//        System.out.println("--");
//
//        im = new Imitation(100000, 30, "1fronts", dir);
//        System.out.println("find: " + im.find());
//        System.out.println("exp:  1000-3000");
//
//        System.out.println("--");
//
//        im = new Imitation(100000, 4, "1fronts", dir);
//        System.out.println("find: " + im.find());
//        System.out.println("exp:  20-60");
//

//
//
//        Imitation im = new Imitation(100000, 5, "cube", "");
//        System.out.println(im.find());
//
//
//        im = new Imitation(100000, 30, "1fronts", "");
//        System.out.println(im.find());
//
//        im = new Imitation(100000, 9, "1fronts", "");
//        System.out.println(im.find());

//        Imitation im = new Imitation(100000, 5, "cube", "");
//        System.out.println(im.find());
    }



}
