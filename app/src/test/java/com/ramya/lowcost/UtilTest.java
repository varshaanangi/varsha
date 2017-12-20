package com.varsha.lowcost;

import com.varsha.lowcost.Utils.UtilHelper;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class UtilTest {

    @Test
    public void createsEmptyGridArrayWithNullInput() {
        assertThat(UtilHelper.convertFromStringToGridArray(null), equalTo(new int[0][0]));
    }

    @Test
    public void createsEmptyGridArrayWithAnyNonNumericInput() {
        assertThat(UtilHelper.convertFromStringToGridArray("1 2 3 a 5"), equalTo(new int[0][0]));
    }

    @Test
    public void createsOneLineGridArrayWithOneLineOfStringData() {
        assertThat(UtilHelper.convertFromStringToGridArray("1  2   3  4 5"), equalTo(new int[][]{ { 1, 2, 3, 4, 5 } }));
    }

    @Test
    public void createsMultiLineGridArrayWithMultipleLinesOfStringData() {
        assertThat(UtilHelper.convertFromStringToGridArray("1  2   3  4 5\n6 7 8  9\t10"),
                equalTo(new int[][]{ { 1, 2, 3, 4, 5 }, { 6, 7, 8, 9, 10 } }));
    }

    @Test
    public void lengthOfFirstLineDeterminesRowLengthAndExtraNumbersInLaterLinesAreDiscarded() {
        assertThat(UtilHelper.convertFromStringToGridArray("1 2 3\n6 7 8 9 10"),
                equalTo(new int[][]{ { 1, 2, 3 }, { 6, 7, 8 } }));
    }

    @Test
    public void lengthOfFirstLineDeterminesRowLengthAndMissingNumbersInLaterLinesAreZero() {
        assertThat(UtilHelper.convertFromStringToGridArray("1 2 3 4 5\n6 7 8   "),
                equalTo(new int[][]{ { 1, 2, 3, 4, 5 }, { 6, 7, 8, 0, 0 } }));
    }
}