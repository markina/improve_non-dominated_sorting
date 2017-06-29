package com.hybrid.sorter.jmh;

import com.hybrid.sorter.BOSNonDominatedSorting;
import com.hybrid.sorter.FactoryNonDominatedSorting;
import com.hybrid.sorter.FasterNonDominatedSorting;
import com.hybrid.sorter.HybridNonDominatedSorting;


/**
 * @author mmarkina
 */
public class UtilsTests {
    static int[] findFrontIndices(double[][] input, FactoryNonDominatedSorting sorterFactory) {
        int size = input.length;
        int dim = size == 0 ? 0 : input[0].length;
        int[] rv = new int[size];
        sorterFactory.getSorter(size, dim).sort(input, rv);
        return rv;
    }

    public static void groupCheck(String title, double[][] input, int[] output) {
        groupCheckOnce(title, input, output);
        groupCheckDuplicate(title, input, output);
    }

    private static void groupCheckOnce(String title, double[][] input, int[] output) {

        FactoryNonDominatedSorting fastFactory = new FasterNonDominatedSorting();
        FactoryNonDominatedSorting bosFactory = new BOSNonDominatedSorting();
        FactoryNonDominatedSorting hybridFactory = new HybridNonDominatedSorting();

        try {
            checkEqual(output, findFrontIndices(input, fastFactory));
            System.out.println("FAST: Raw test '" + title + "' passed");
        } catch (AssertionError er) {
            throw new AssertionError("FAST: Error in raw test '" + title + "': " + er.getMessage());
        }

        try {
            checkEqual(output, findFrontIndices(input, bosFactory));
            System.out.println("BOS: Raw test '" + title + "' passed");
        } catch (AssertionError er) {
            throw new AssertionError("BOS: Error in raw test '" + title + "': " + er.getMessage());
        }

        try {
            checkEqual(output, findFrontIndices(input, hybridFactory));
            System.out.println("HYBRID: Raw test '" + title + "' passed");
        } catch (AssertionError er) {
            throw new AssertionError("HYBRID: Error in raw test '" + title + "': " + er.getMessage());
        }

    }

    private static void groupCheckDuplicate(String title, double[][] input, int[] output) {
        FactoryNonDominatedSorting fastFactory = new FasterNonDominatedSorting();
        FactoryNonDominatedSorting bosFactory = new BOSNonDominatedSorting();
        FactoryNonDominatedSorting hybridFactory = new HybridNonDominatedSorting();

        int[] output2 = new int[output.length * 2];
        System.arraycopy(output, 0, output2, 0, output.length);
        System.arraycopy(output, 0, output2, output.length, output.length);

        double[][] input2 = new double[input.length * 2][];
        System.arraycopy(input, 0, input2, 0, input.length);
        System.arraycopy(input, 0, input2, input.length, input.length);

        try {
            checkEqual(output2, findFrontIndices(input2, fastFactory));
            System.out.println("FAST: Duplicate test '" + title + "' passed");
        } catch (AssertionError er) {
            throw new AssertionError("FAST: Error in duplicate test '" + title + "': " + er.getMessage());
        }

        try {
            checkEqual(output2, findFrontIndices(input2, bosFactory));
            System.out.println("BOS: Duplicate test '" + title + "' passed");
        } catch (AssertionError er) {
            throw new AssertionError("BOS: Error in duplicate test '" + title + "': " + er.getMessage());
        }

        try {
            checkEqual(output2, findFrontIndices(input2, hybridFactory));
            System.out.println("HYBRID: Duplicate test '" + title + "' passed");
        } catch (AssertionError er) {
            throw new AssertionError("HYBRID: Error in duplicate test '" + title + "': " + er.getMessage());
        }
    }

    public static void checkEqual(int[] expected, int[] found) {
        if (found == null) {
            throw new AssertionError("Found a null array");
        }
        if (expected.length != found.length) {
            throw new AssertionError("Arrays have unequal length: expected " + expected.length + " found " + found.length);
        }
        for (int i = 0; i < expected.length; ++i) {
            if (expected[i] != found[i]) {
                throw new AssertionError("Arrays differ at position " + i + ": expected " + expected[i] + " found " + found[i]);
            }
        }
    }
}
