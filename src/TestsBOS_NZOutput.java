import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.function.BiConsumer;

/**
 * Tests for non-dominated sorting algorithms and implementations.
 *
 * @author Maxim Buzdalov
 */
public class TestsBOS_NZOutput {
    Sorter sorterBOS = new BOSNonDominatedSorting().getSorter(20000, 100);

    static int[] findFrontIndices(double[][] input, FactoryNonDominatedSorting sorterFactory, int[] output) {
        int size = input.length;
        int dim = size == 0 ? 0 : input[0].length;
        sorterFactory.getSorter(size, dim).sort(input, output);
        return output;
    }

    static int[] findFrontIndices(double[][] input, Sorter srt, int [] output) {
        int size = input.length;
        srt.sort(input, output);
        return output;
    }

    static void checkEqual(int[] expected, int[] found) {
        if(found == null) {
            throw new AssertionError("Found a null array");
        }
        if(expected.length != found.length) {
            throw new AssertionError("Arrays have unequal length: expected " + expected.length + " found " + found.length);
        }
        for(int i = 0; i < expected.length; ++i) {
            if(expected[i] != found[i]) {
                throw new AssertionError("Arrays differ at position " + i + ": expected " + expected[i] + " found " + found[i]);
            }
        }
    }

    private static void groupCheck(String title, double[][] input, int[] expected, int[] startOutput) {
        int[] expected2 = new int[expected.length * 2];
        System.arraycopy(expected, 0, expected2, 0, expected.length);
        System.arraycopy(expected, 0, expected2, expected.length, expected.length);

        int[] output2 = new int[startOutput.length * 2];
        System.arraycopy(startOutput, 0, output2, 0, startOutput.length);
        System.arraycopy(startOutput, 0, output2, startOutput.length, startOutput.length);

        int[] output = new int[startOutput.length];
        System.arraycopy(startOutput, 0, output, 0, startOutput.length);

        double[][] input2 = new double[input.length * 2][];
        System.arraycopy(input, 0, input2, 0, input.length);
        System.arraycopy(input, 0, input2, input.length, input.length);

        FactoryNonDominatedSorting bosFactory = new BOSNonDominatedSorting();
        FactoryNonDominatedSorting hybridFactory = new HybridNonDominatedSorting();

//        try {
//            checkEqual(expected, findFrontIndices(input, hybridFactory, output));
//            System.out.println("Hybrid: Raw test '" + title + "' passed");
//        } catch(AssertionError er) {
//            throw new AssertionError("Hybrid: Error in raw test '" + title + "': " + er.getMessage());
//        }
//
//        System.arraycopy(startOutput, 0, output, 0, startOutput.length);

        try {
            checkEqual(expected, findFrontIndices(input, bosFactory, output));
            System.out.println("BOS: Raw test '" + title + "' passed");
        } catch(AssertionError er) {
            throw new AssertionError("BOS: Error in raw test '" + title + "': " + er.getMessage());
        }

        System.arraycopy(startOutput, 0, output, 0, startOutput.length);

        try {
            checkEqual(expected, findFrontIndices(input, bosFactory, output));
            System.out.println("BOS: Raw test '" + title + "' passed");
        } catch(AssertionError er) {
            throw new AssertionError("BOS: Error in raw test '" + title + "': " + er.getMessage());
        }

        System.arraycopy(startOutput, 0, output, 0, startOutput.length);

//        try {
//            checkEqual(expected2, findFrontIndices(input2, hybridFactory, output2));
//            System.out.println("Hybrid: Duplicate test '" + title + "' passed");
//        } catch(AssertionError er) {
//            throw new AssertionError("Hybrid: Error in duplicate test '" + title + "': " + er.getMessage());
//        }
//
//        System.arraycopy(startOutput, 0, output2, 0, startOutput.length);
//        System.arraycopy(startOutput, 0, output2, startOutput.length, startOutput.length);

        try {
            checkEqual(expected2, findFrontIndices(input2, bosFactory, output2));
            System.out.println("BOS: Duplicate test '" + title + "' passed");
        } catch(AssertionError er) {
            throw new AssertionError("BOS: Error in duplicate test '" + title + "': " + er.getMessage());
        }

        System.arraycopy(startOutput, 0, output2, 0, startOutput.length);
        System.arraycopy(startOutput, 0, output2, startOutput.length, startOutput.length);
    }

    private static double[][] arrayFill(int dim1, int dim2, double value) {
        double[][] rv = new double[dim1][dim2];
        for(double[] ar : rv) {
            Arrays.fill(ar, value);
        }
        return rv;
    }

    private static int[] arrayFill(int dim, int value) {
        int[] rv = new int[dim];
        Arrays.fill(rv, value);
        return rv;
    }

    private static double[][] getForWorstCase3(int size) {

        double[][] res = new double[size][3];
        for(int i = 0; i < size; i++) {
            res[i][0] = i;
            res[i][1] = i;
            res[i][2] = size - i - 1;
        }
        return res;
    }

    private static double[][] concat(double[][] a, double[][] b) {
        double[][] rv = new double[a.length + b.length][];
        System.arraycopy(a, 0, rv, 0, a.length);
        System.arraycopy(b, 0, rv, a.length, b.length);
        return rv;
    }

    private static int[] concat(int[] a, int[] b) {
        int[] rv = new int[a.length + b.length];
        System.arraycopy(a, 0, rv, 0, a.length);
        System.arraycopy(b, 0, rv, a.length, b.length);
        return rv;
    }

    public static void main(String[] args) {

        groupCheck("a tricky random test #1 inherited from old hg-based NGP", new double[][]{
                {-758, -515, -226}, {-786, -98, -268}, {-876, -264, -655}, {-43, -572, -418}, {-158, -517, -647},
                {-636, -321, -369}, {-19, -547, -935}, {-571, -866, -524}, {-819, -917, -692}, {-555, -487, -980}
        },
                new int[]{1, 1, 0, 3, 1, 1, 0, 1, 0, 0},
                new int[]{0, 0, 0, 3, 0, 0, 0, 0, 0, 0});

//
//        groupCheck("a tricky random test #2 inherited from old hg-based NGP", new double[][]{
//                {1, 4, 3, 3, 1, 1}, {3, 5, 4, 6, 7, 1}, {1, 2, 2, 3, 8, 0}, {0, 2, 4, 8, 3, 5}, {0, 2, 7, 4, 2, 9},
//                {1, 3, 9, 4, 0, 1}, {7, 6, 7, 1, 7, 4}, {6, 0, 9, 3, 7, 1}, {7, 0, 7, 8, 3, 0}, {4, 7, 6, 4, 0, 3},
//                {9, 2, 7, 0, 8, 8}, {5, 5, 3, 3, 6, 1}, {0, 7, 4, 1, 9, 2}, {1, 2, 0, 3, 9, 6}, {2, 8, 2, 6, 9, 3},
//                {1, 5, 0, 7, 4, 9}, {4, 8, 4, 8, 2, 9}, {7, 7, 5, 7, 3, 2}, {2, 6, 3, 6, 7, 9}, {8, 3, 3, 7, 0, 4},
//                {4, 1, 5, 1, 1, 5}, {9, 7, 4, 0, 9, 4}, {6, 5, 3, 5, 7, 9}, {1, 2, 7, 7, 0, 1}, {4, 9, 8, 4, 3, 8},
//                {5, 7, 5, 7, 4, 6}, {1, 2, 1, 0, 6, 8}, {8, 8, 3, 8, 4, 9}, {8, 5, 7, 8, 2, 3}, {2, 1, 1, 0, 6, 0},
//                {9, 5, 8, 9, 6, 6}, {1, 5, 5, 2, 3, 4}, {8, 4, 9, 5, 4, 7}, {1, 9, 2, 5, 3, 5}, {0, 5, 9, 4, 3, 2},
//                {0, 7, 4, 5, 8, 0}, {0, 3, 6, 2, 3, 7}, {7, 3, 1, 3, 7, 3}, {5, 2, 4, 0, 7, 0}, {4, 6, 2, 2, 8, 4},
//                {2, 7, 1, 0, 6, 8}, {0, 5, 8, 0, 7, 6}, {5, 0, 4, 1, 7, 9}, {6, 9, 1, 4, 2, 6}, {5, 4, 3, 3, 4, 5},
//                {6, 5, 0, 1, 1, 6}, {9, 5, 1, 0, 0, 5}, {8, 2, 4, 3, 7, 5}, {3, 4, 5, 7, 9, 1}, {9, 1, 4, 9, 0, 5},
//                {6, 3, 9, 0, 6, 0}, {7, 2, 4, 3, 6, 1}, {8, 4, 4, 4, 3, 9}, {3, 7, 1, 1, 3, 8}, {9, 4, 6, 3, 4, 0},
//                {3, 4, 9, 0, 1, 5}, {4, 7, 9, 5, 9, 8}, {2, 2, 2, 8, 0, 9}, {8, 2, 4, 7, 7, 2}, {6, 4, 4, 0, 0, 4},
//                {9, 3, 0, 2, 0, 6}, {1, 6, 2, 6, 1, 5}, {9, 8, 3, 3, 9, 3}, {0, 3, 2, 7, 3, 6}, {8, 3, 9, 5, 0, 9},
//                {6, 5, 9, 2, 3, 5}, {5, 6, 8, 2, 0, 5}, {7, 7, 1, 5, 4, 3}, {2, 9, 8, 5, 2, 7}, {4, 3, 8, 7, 1, 0},
//                {2, 1, 7, 0, 4, 5}, {4, 3, 5, 2, 5, 4}, {4, 1, 6, 6, 5, 1}, {1, 6, 7, 3, 9, 9}, {8, 4, 6, 6, 8, 6},
//                {2, 5, 1, 6, 0, 7}, {6, 5, 9, 7, 6, 2}, {0, 2, 0, 8, 0, 1}, {0, 7, 9, 2, 8, 7}, {2, 5, 4, 1, 2, 2},
//                {1, 0, 0, 5, 7, 0}, {4, 8, 6, 2, 7, 1}, {4, 6, 2, 5, 3, 5}, {4, 5, 6, 0, 0, 9}, {2, 7, 1, 6, 5, 7},
//                {1, 5, 5, 0, 5, 4}, {0, 4, 8, 7, 0, 0}, {3, 0, 7, 1, 1, 7}, {7, 2, 6, 6, 3, 3}, {7, 7, 4, 2, 0, 8},
//                {2, 7, 6, 9, 8, 2}, {7, 5, 5, 7, 7, 0}, {4, 4, 9, 1, 5, 0}, {1, 4, 8, 7, 9, 9}, {0, 2, 0, 7, 8, 4},
//                {5, 8, 1, 9, 7, 4}, {6, 2, 6, 1, 9, 2}, {2, 5, 7, 8, 2, 1}, {2, 0, 7, 9, 3, 3}, {7, 3, 6, 6, 4, 5},
//                {0, 3, 3, 2, 5, 7}, {9, 7, 2, 7, 2, 6}, {0, 3, 8, 9, 4, 9}, {5, 2, 4, 7, 1, 0}, {6, 2, 1, 1, 0, 2},
//                {9, 7, 0, 2, 0, 1}, {1, 3, 0, 6, 5, 3}, {0, 9, 6, 3, 6, 8}, {1, 1, 8, 7, 9, 4}, {3, 2, 1, 2, 7, 6},
//                {2, 0, 8, 1, 7, 7}, {7, 4, 3, 8, 1, 3}, {1, 2, 5, 5, 2, 9}, {9, 4, 0, 8, 0, 6}, {5, 2, 2, 0, 9, 1},
//                {1, 5, 8, 9, 4, 3}, {0, 8, 2, 3, 7, 6}, {1, 4, 8, 0, 9, 3}, {3, 0, 3, 2, 0, 2}, {1, 3, 3, 9, 5, 3},
//                {5, 5, 3, 7, 2, 0}, {6, 3, 5, 3, 8, 7}, {6, 9, 7, 3, 1, 8}, {3, 6, 6, 2, 7, 0}, {9, 9, 1, 6, 3, 0},
//                {1, 6, 5, 5, 0, 2}, {2, 4, 5, 4, 8, 7}, {5, 1, 1, 2, 1, 9}, {6, 2, 1, 1, 1, 9}, {1, 2, 1, 2, 4, 8},
//                {4, 6, 5, 1, 5, 1}, {2, 5, 8, 3, 2, 8}, {2, 4, 5, 2, 2, 5}, {8, 6, 5, 0, 8, 8}, {8, 9, 5, 2, 5, 9},
//                {2, 5, 1, 1, 4, 7}, {8, 9, 6, 9, 5, 5}, {6, 9, 3, 8, 4, 3}, {4, 8, 7, 9, 3, 9}, {6, 8, 7, 5, 8, 6},
//                {2, 1, 9, 1, 4, 2}, {5, 4, 5, 2, 1, 2}, {5, 0, 7, 8, 9, 2}, {4, 6, 3, 5, 7, 1}, {9, 3, 2, 0, 6, 1},
//                {8, 2, 8, 8, 7, 5}, {3, 8, 6, 6, 0, 1}, {3, 3, 1, 5, 4, 2}, {9, 4, 9, 0, 4, 3}, {6, 9, 5, 7, 8, 7},
//                {3, 5, 3, 4, 1, 6}, {2, 4, 3, 2, 6, 3}, {7, 2, 7, 5, 4, 0}, {5, 3, 0, 0, 7, 9}, {0, 7, 8, 8, 5, 5},
//                {8, 8, 3, 8, 0, 5}, {4, 8, 2, 6, 5, 0}, {1, 0, 7, 8, 2, 9}, {0, 7, 1, 6, 2, 1}, {2, 5, 3, 0, 8, 6},
//                {2, 7, 4, 7, 6, 3}, {0, 9, 5, 8, 9, 5}, {5, 6, 6, 7, 4, 0}, {8, 4, 8, 3, 2, 7}, {5, 3, 8, 6, 0, 0},
//                {5, 0, 1, 5, 2, 9}, {3, 6, 4, 4, 1, 0}, {3, 7, 8, 2, 1, 8}, {8, 9, 1, 5, 4, 3}, {1, 2, 6, 8, 1, 8},
//                {3, 8, 2, 4, 8, 3}, {8, 5, 3, 6, 3, 8}, {2, 6, 6, 1, 5, 9}, {5, 9, 0, 5, 1, 9}, {6, 4, 2, 1, 4, 9},
//                {3, 8, 6, 4, 4, 8}, {9, 0, 4, 0, 8, 0}, {8, 3, 8, 7, 2, 2}, {4, 7, 4, 2, 9, 6}, {4, 4, 4, 2, 4, 7},
//                {3, 0, 1, 2, 8, 9}, {5, 1, 3, 2, 2, 1}, {0, 9, 0, 0, 2, 5}, {1, 0, 3, 8, 9, 6}, {5, 6, 8, 7, 6, 2},
//                {1, 4, 1, 5, 7, 7}, {2, 2, 1, 8, 0, 4}, {4, 9, 0, 2, 5, 1}, {8, 4, 4, 7, 9, 9}, {8, 2, 2, 0, 9, 1},
//                {4, 1, 0, 2, 9, 8}, {4, 6, 0, 5, 3, 5}, {8, 6, 0, 8, 7, 3}, {6, 6, 5, 5, 9, 4}, {1, 5, 0, 6, 2, 2},
//                {4, 1, 7, 7, 8, 7}, {7, 4, 4, 6, 5, 4}, {6, 7, 6, 2, 2, 7}, {4, 1, 3, 5, 0, 0}, {1, 5, 9, 5, 6, 7}
//        }, new int[]{
//                0, 1, 0, 1, 0, 0, 2, 0, 0, 1, 2, 1, 0, 0, 1, 1, 3, 2, 2, 1, 0, 3, 3, 0, 2, 2, 0, 3, 2, 0, 3, 0, 3, 1, 0, 0, 0,
//                1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 2, 1, 1, 1, 1, 2, 0, 0, 0, 3, 2, 2, 0, 0, 0, 3, 0, 1, 2, 1, 1, 1, 1, 0, 1, 1, 1,
//                3, 0, 2, 0, 1, 0, 0, 2, 1, 0, 1, 0, 0, 0, 1, 1, 1, 2, 0, 2, 0, 2, 2, 1, 0, 2, 0, 1, 2, 1, 0, 0, 0, 1, 1, 1, 0,
//                1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 2, 2, 1, 0, 0, 2, 0, 1, 0, 0, 1, 0, 2, 3, 0, 3, 2, 4, 3, 0, 1, 1, 1, 1, 3, 0, 0,
//                0, 3, 1, 1, 1, 0, 2, 2, 0, 0, 0, 1, 2, 2, 2, 2, 1, 0, 0, 1, 2, 1, 1, 2, 1, 0, 2, 2, 0, 2, 2, 1, 0, 0, 0, 1, 3,
//                1, 1, 0, 3, 2, 0, 0, 1, 2, 0, 2, 1, 2, 0, 1
//        });
//
//        groupCheck("a tricky random test #3 inherited from old hg-based NGP", new double[][]{
//                {923, 565, 773}, {444, 591, 756}, {272, 974, 743}, {962, 736, 763}, {957, 562, 957}, {995, 334, 950},
//                {925, 43, 100}, {550, 727, 269}, {360, 67, 428}, {728, 395, 315}, {88, 558, 283}, {8, 550, 45},
//                {184, 308, 822}, {666, 955, 90}, {551, 370, 277}, {338, 142, 269}, {770, 757, 11}, {46, 882, 761},
//                {2, 385, 623}, {730, 188, 566}, {712, 927, 754}, {37, 506, 103}, {360, 109, 715}, {478, 229, 95},
//                {285, 466, 526}, {987, 374, 510}, {528, 644, 338}, {943, 32, 927}, {505, 613, 708}, {996, 10, 70},
//                {505, 750, 181}, {375, 842, 799}, {843, 46, 984}, {994, 221, 710}, {553, 627, 448}, {222, 401, 828},
//                {668, 259, 821}, {955, 924, 943}, {361, 402, 956}, {308, 201, 698}, {53, 508, 482}, {114, 908, 13},
//                {68, 415, 283}, {786, 393, 958}, {72, 296, 86}, {271, 985, 889}, {925, 439, 260}, {5, 731, 719},
//                {746, 194, 762}, {171, 504, 219}, {853, 328, 76}, {134, 504, 168}, {499, 870, 44}, {90, 558, 870},
//                {568, 23, 938}, {687, 912, 122}, {84, 727, 814}, {562, 409, 296}, {964, 204, 904}, {642, 374, 47},
//                {423, 950, 211}, {602, 178, 259}, {825, 451, 93}, {62, 862, 371}, {880, 359, 80}, {720, 102, 385},
//                {731, 510, 973}, {188, 799, 896}, {215, 416, 478}, {557, 796, 302}, {201, 584, 751}, {785, 217, 107},
//                {412, 972, 201}, {838, 230, 904}, {294, 399, 300}, {312, 325, 101}, {448, 664, 434}, {367, 479, 29},
//                {388, 252, 82}, {960, 579, 951}, {424, 871, 520}, {652, 379, 288}, {794, 696, 790}, {655, 443, 615},
//                {458, 786, 600}, {218, 447, 453}, {876, 346, 845}, {65, 539, 554}, {511, 205, 530}, {676, 382, 542},
//                {984, 781, 697}, {867, 718, 37}, {767, 174, 794}, {636, 315, 744}, {796, 657, 6}, {902, 952, 327},
//                {404, 131, 73}, {35, 80, 480}, {961, 555, 169}, {545, 356, 947}, {312, 108, 98}, {364, 26, 189},
//                {648, 865, 687}, {856, 132, 756}, {720, 433, 703}, {568, 252, 401}, {761, 315, 393}, {380, 847, 372},
//                {325, 889, 815}, {142, 664, 427}, {167, 942, 243}, {743, 227, 968}, {448, 530, 945}, {179, 952, 35},
//                {166, 348, 577}, {296, 328, 41}, {266, 39, 921}, {65, 704, 471}, {674, 962, 248}, {271, 507, 885},
//                {929, 594, 918}, {839, 563, 787}, {856, 764, 807}, {701, 974, 447}, {821, 833, 498}, {63, 73, 537},
//                {484, 870, 732}, {244, 355, 878}, {198, 901, 463}, {295, 965, 944}, {616, 511, 624}, {439, 6, 779},
//                {772, 916, 261}, {933, 107, 802}, {90, 233, 618}, {871, 254, 978}, {966, 510, 534}, {84, 404, 284},
//                {187, 166, 534}, {996, 978, 737}, {641, 70, 225}, {982, 241, 144}, {722, 663, 843}, {878, 478, 334},
//                {223, 365, 530}, {733, 859, 880}, {373, 945, 970}, {590, 115, 728}, {144, 66, 460}, {228, 374, 431},
//                {86, 570, 559}, {118, 900, 487}, {959, 808, 318}, {213, 171, 148}, {912, 924, 841}, {283, 505, 183},
//                {644, 962, 850}, {351, 569, 362}, {495, 220, 291}, {412, 476, 621}, {128, 819, 599}, {600, 859, 972},
//                {971, 509, 135}, {548, 784, 723}, {568, 523, 645}, {91, 690, 824}, {332, 835, 154}, {31, 931, 984},
//                {193, 216, 324}, {614, 857, 828}, {677, 532, 104}, {283, 938, 998}, {913, 44, 884}, {925, 357, 711},
//                {1, 115, 790}, {167, 506, 438}, {17, 966, 822}, {749, 224, 612}, {778, 841, 284}, {91, 849, 313},
//                {6, 759, 269}, {102, 612, 219}, {386, 280, 662}, {746, 33, 819}, {77, 997, 679}, {635, 681, 244},
//                {427, 250, 160}, {531, 651, 98}, {562, 710, 101}, {798, 335, 447}, {408, 320, 519}, {169, 31, 527},
//                {326, 512, 468}, {954, 236, 313}, {424, 858, 106}, {699, 774, 854}, {686, 174, 740}, {530, 436, 683},
//                {936, 748, 396}, {190, 958, 875}, {741, 385, 381}, {673, 782, 822}, {934, 922, 931}, {989, 323, 352},
//                {601, 583, 763}, {982, 393, 252}, {697, 472, 920}, {841, 345, 779}, {953, 816, 601}, {657, 523, 83},
//                {114, 955, 452}, {756, 132, 863}, {713, 25, 296}, {676, 950, 870}, {148, 598, 664}, {414, 783, 413},
//                {309, 796, 764}, {565, 75, 465}, {702, 809, 835}, {528, 80, 188}, {618, 200, 390}, {210, 678, 836},
//                {467, 743, 612}, {832, 615, 800}, {853, 272, 226}, {704, 334, 235}, {593, 968, 628}, {807, 852, 329},
//                {235, 385, 287}, {239, 509, 23}, {44, 129, 821}, {912, 444, 277}, {623, 342, 603}, {715, 593, 517},
//                {840, 123, 418}, {182, 898, 986}, {469, 418, 603}, {654, 192, 386}, {884, 681, 61}, {790, 392, 922},
//                {404, 135, 279}, {859, 416, 905}, {449, 80, 221}, {541, 666, 928}, {662, 181, 488}, {537, 418, 252},
//                {359, 777, 835}, {535, 138, 553}, {94, 209, 457}, {999, 587, 244}, {869, 453, 283}, {206, 597, 48},
//                {543, 133, 177}, {702, 658, 638}, {636, 122, 653}, {852, 17, 381}, {336, 175, 877}, {752, 707, 981},
//                {82, 454, 489}, {894, 478, 376}, {507, 103, 329}, {373, 416, 624}, {835, 556, 247}, {169, 345, 836},
//                {843, 28, 108}, {406, 765, 118}, {881, 123, 937}, {706, 417, 202}, {425, 354, 688}, {560, 158, 687},
//                {832, 953, 304}, {899, 290, 990}, {85, 103, 420}, {569, 315, 177}, {835, 294, 11}, {827, 335, 575},
//                {655, 39, 474}, {49, 265, 625}, {327, 277, 954}, {672, 830, 860}, {225, 792, 578}, {572, 407, 740},
//                {307, 243, 454}, {697, 725, 836}, {511, 200, 476}, {615, 584, 735}, {552, 248, 952}, {402, 297, 425},
//                {508, 457, 618}, {812, 921, 60}, {481, 5, 510}, {891, 177, 7}, {882, 197, 224}, {544, 849, 242},
//                {633, 593, 375}, {676, 808, 628}, {964, 821, 755}, {887, 461, 205}, {778, 31, 941}, {69, 774, 75}
//        }, new int[]{
//                7, 5, 5, 8, 9, 8, 0, 3, 0, 4, 1, 0, 2, 3, 2, 1, 0, 2, 0, 4, 8, 0, 1, 1, 3, 6, 3, 1, 5, 0, 2, 6, 3, 5, 4, 3, 5, 12,
//                4, 2, 1, 0, 0, 7, 0, 6, 4, 1, 6, 2, 1, 1, 1, 3, 1, 4, 3, 3, 7, 1, 3, 2, 2, 1, 2, 2, 8, 5, 2, 4, 4, 1, 3, 7, 2, 1,
//                4, 0, 0, 9, 5, 3, 8, 6, 5, 2, 8, 2, 4, 5, 8, 1, 6, 5, 0, 6, 0, 0, 4, 5, 0, 0, 7, 4, 6, 3, 4, 4, 6, 2, 2, 6, 5, 1,
//                2, 0, 1, 1, 4, 4, 9, 7, 9, 5, 6, 0, 6, 3, 3, 6, 6, 0, 5, 3, 1, 8, 7, 1, 1, 9, 1, 2, 8, 5, 2, 10, 7, 3, 0, 1, 3, 3,
//                5, 0, 10, 2, 9, 3, 2, 4, 4, 7, 3, 6, 6, 4, 2, 2, 0, 8, 3, 6, 2, 7, 0, 2, 2, 5, 4, 2, 0, 1, 2, 1, 3, 3, 1, 2, 3, 5,
//                3, 0, 3, 3, 2, 9, 5, 5, 7, 5, 4, 8, 11, 4, 7, 4, 7, 7, 8, 2, 3, 4, 0, 9, 4, 4, 5, 1, 9, 0, 3, 5, 5, 8, 2, 3, 6, 5,
//                1, 0, 1, 4, 5, 5, 3, 5, 4, 3, 2, 7, 1, 8, 1, 6, 3, 2, 5, 3, 1, 5, 4, 1, 1, 7, 3, 0, 2, 9, 1, 6, 2, 3, 4, 2, 0, 2,
//                4, 3, 4, 4, 6, 9, 0, 2, 0, 6, 1, 1, 3, 8, 4, 5, 1, 8, 3, 7, 5, 2, 5, 2, 0, 0, 2, 3, 4, 7, 9, 4, 2, 1
//        });
//
//
//        groupCheck("worst case from paper BOS", getForWorstCase3(10000), new int[10000]);

        System.out.println();
        System.out.println("Tests passed");
    }

    private static class Hypercube {
        Random rng = new Random(366239);

        void callOn(int dim, int size, BiConsumer<double[][], int[]> whatToCall) {
            double[][] cube = genShuffledHypercube(dim, size);
            int[] sums = new int[cube.length];
            for(int i = 0; i < cube.length; ++i) {
                double sum = 0;
                for(int j = 0; j < dim; ++j) {
                    sum += cube[i][j];
                }
                sums[i] = (int) Math.round(sum);
            }
            whatToCall.accept(cube, sums);
        }


        private double[][] genShuffledHypercube(int dim, int size) {
            double[][] cube = genHypercube(dim, size);
            Collections.shuffle(Arrays.asList(cube), rng);
            return cube;
        }

        private double[][] genHypercube(int dim, int size) {
            if(dim == 1) {
                double[][] rv = new double[size][1];
                for(int i = 0; i < size; ++i) {
                    rv[i][0] = i;
                }
                return rv;
            } else {
                double[][] prev = genHypercube(dim - 1, size);
                double[][] rv = new double[prev.length * size][dim];
                for(int i = 0; i < size; ++i) {
                    for(int j = 0; j < prev.length; ++j) {
                        int idx = j + prev.length * i;
                        rv[idx][dim - 1] = i;
                        System.arraycopy(prev[j], 0, rv[idx], 0, dim - 1);
                    }
                }
                return rv;
            }
        }
    }

    private static final Hypercube hypercube = new Hypercube();
}
