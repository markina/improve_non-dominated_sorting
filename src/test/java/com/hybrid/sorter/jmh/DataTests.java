package com.hybrid.sorter.jmh;

/**
 * @author mmarkina
 */
public class DataTests {
    final static double[][] inputSimpleTest = new double[][] {{2, 4}, {5, 3}, {3, 4}, {3, 6}, {1, 2}};

    final static int[] outputSimpleTest = new int[] {1, 1, 2, 3, 0};

    final static double[][] inputOtherSimpleTest = new double[][] {{2, 4}, {5, 3}, {3, 4}, {3, 6}, {1, 2}};

    final static int[] outputOtherSimpleTest = new int[] {1, 1, 2, 3, 0};

    final static double[][] inputTwoUnequal1DPointsOrderedDecreasing = new double[][] {{2}, {1}};

    final static int[] outputTwoUnequal1DPointsOrderedDecreasing = new int[] {1, 0};

    final static double[][] inputTwoUnequal1DPointsOrderedIncreasing = new double[][] {{2}, {3}};

    final static int[] outputTwoUnequal1DPointsOrderedIncreasing = new int[] {0, 1};

    final static double[][] inputTwoEqual1DPoints = new double[][] {{2}, {2}};

    final static int[] outputTwoEqual1DPoints = new int[] {0, 0};

    final static double[][] inputTwoIncomparable2DPoints = new double[][] {{2, 1}, {1, 2}};

    final static int[] outputTwoIncomparable2DPoints = new int[] {0, 0};

    final static double[][] inputTwo2DPointsDominatedIncreasingly = new double[][] {{1, 1}, {1, 2}};

    final static int[] outputTwo2DPointsDominatedIncreasingly = new int[] {0, 1};

    final static double[][] inputFive2DPointsDominatedDecreasingly = new double[][] {{0, 2}, {0, 2}, {0, 1}, {0, 2}, {0, 3}};

    final static int[] outputFive2DPointsDominatedDecreasingly = new int[] {1, 1, 0, 1, 2};

    final static double[][] inputTwo2DPointsDominatedDecreasingly = new double[][] {{1, 2}, {1, 1}};

    final static int[] outputTwo2DPointsDominatedDecreasingly = new int[] {1, 0};


}
