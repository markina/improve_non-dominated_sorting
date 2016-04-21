/*
 * Copyright 2015 Maxim Buzdalov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 * A stand-alone implementation of the "faster" non-dominated sorting.
 *
 * The idea is outlined in the paper:
 * ==================================================================================================
 * Buzdalov M., Shalyto A. A Provably Asymptotically Fast Version of the Generalized Jensen Algorithm
 * for Non-dominated Sorting // Parallel Problem Solving from Nature XIII. - 2015. - P. 528-537.
 * - (Lecture Notes on Computer Science ; 8672)
 * ==================================================================================================
 *
 * Please cite the paper referenced above when you use this code in your research.
 * For those who cite, there is a BibTeX entry:
 * <code>
 * @incollection{
 *     author       = {Maxim Buzdalov and Anatoly Shalyto},
 *     title        = {A Provably Asymptotically Fast Version of the Generalized Jensen Algorithm
 *                     for Non-dominated Sorting},
 *     booktitle    = {Parallel Problem Solving from Nature XIII},
 *     series       = {Lecture Notes on Computer Science},
 *     number       = {8672},
 *     year         = {2005},
 *     pages        = {528-537},
 *     langid       = {english}
 * }
 * </code>
 *
 * @author Maxim Buzdalov
 */

public final class FasterNonDominatedSorting {


    /**
     * A factory method which returns a sorter
     * adapted for the given size (the number of points)
     * and dimension (the number of coordinates in each point).
     *
     * The method does not cache anything, do it on the caller's side.
     *
     * @param size the problem's size (the number of points).
     * @param dim the problem's dimension (the number of coordinates in each point).
     * @return the sorter adapted for the given size and dimension.
     */
    public static Sorter getSorterFast(int size, int dim) {
        if (dim < 0 || size < 0) {
            throw new IllegalArgumentException("Size or dimension is negative");
        }

        if (size == 0) {
            return new SorterEmpty(dim);
        }
        switch (dim) {
            case 0: return new Sorter0D(size);
            case 1: return new Sorter1D(size);
            case 2: return new Sorter2D(size);
            default: return new SorterXD(size, dim);
        }
    }

    public static Sorter getSorterBOS(int size, int dim) {
        if (dim < 0 || size < 0) {
            throw new IllegalArgumentException("Size or dimension is negative");
        }

        if (size == 0) {
            return new SorterEmpty(dim);
        }

        return new SorterBOS(size, dim);
    }

    public static Sorter getSorterFastWithAnalysis(int size, int dim, boolean withTiming, boolean withLogging, PrintWriter out) throws IllegalAccessException, FileNotFoundException {
        if(!withLogging && !withTiming) {
            return getSorterFast(size, dim);
        }
        if(withLogging && withTiming) {
            throw new IllegalAccessException("Timing + Logging are bad idea");
        }
        Sorter sorter = getSorterFast(size, dim);
        sorter.setParamAnalysis(withTiming, withLogging, out);
        return sorter;
    }

    public static Sorter getSorterBOSWithAnalysis(int size, int dim, boolean withTiming, boolean withLogging, PrintWriter out) throws IllegalAccessException, FileNotFoundException {
        if(!withLogging && !withTiming) {
            return getSorterFast(size, dim);
        }
        if(withLogging && withTiming) {
            throw new IllegalAccessException("Timing + Logging are bad idea");
        }
        Sorter sorter = getSorterBOS(size, dim);
        sorter.setParamAnalysis(withTiming, withLogging, out);
        return sorter;
    }




}
