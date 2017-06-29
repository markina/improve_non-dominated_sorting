package com.hybrid.sorter.jmh;

import org.junit.Test;

import static com.hybrid.sorter.jmh.DataTests.*;
import static com.hybrid.sorter.jmh.UtilsTests.groupCheck;


/**
 * @author mmarkina
 */
public class CorrectTests {

    @Test
    public void simpleTest() {
        groupCheck("simple test", inputSimpleTest, outputSimpleTest);
    }

    @Test
    public void otherSimpleTest() {
        groupCheck("OtherSimpleTest", inputOtherSimpleTest, outputOtherSimpleTest);
    }

    @Test
    public void twoUnequal1DPointsOrderedDecreasing() {
        groupCheck("TwoUnequal1DPointsOrderedDecreasing", inputTwoUnequal1DPointsOrderedDecreasing, outputTwoUnequal1DPointsOrderedDecreasing);
    }

    @Test
    public void twoUnequal1DPointsOrderedIncreasing() {
        groupCheck("TwoUnequal1DPointsOrderedIncreasing", inputTwoUnequal1DPointsOrderedIncreasing, outputTwoUnequal1DPointsOrderedIncreasing);
    }

    @Test
    public void twoEqual1DPoints() {
        groupCheck("TwoEqual1DPoints", inputTwoEqual1DPoints, outputTwoEqual1DPoints);
    }

    @Test
    public void twoIncomparable2DPoints() {
        groupCheck("TwoIncomparable2DPoints", inputTwoIncomparable2DPoints, outputTwoIncomparable2DPoints);
    }

    @Test
    public void two2DPointsDominatedIncreasingly() {
        groupCheck("Two2DPointsDominatedIncreasingly", inputTwo2DPointsDominatedIncreasingly, outputTwo2DPointsDominatedIncreasingly);
    }

    @Test
    public void five2DPointsDominatedDecreasingly() {
        groupCheck("Five2DPointsDominatedDecreasingly", inputFive2DPointsDominatedDecreasingly, outputFive2DPointsDominatedDecreasingly);
    }

    @Test
    public void two2DPointsDominatedDecreasingly() {
        groupCheck("Two2DPointsDominatedDecreasingly", inputTwo2DPointsDominatedDecreasingly, outputTwo2DPointsDominatedDecreasingly);
    }

}
