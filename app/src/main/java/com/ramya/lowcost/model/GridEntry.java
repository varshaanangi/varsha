package com.varsha.lowcost.model;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GridEntry {
    int[][] values;

    public GridEntry(int[][] values) {
        if(values.length >= 1 && values.length <= 10) {
            if(values[0].length >= 5 && values[0].length <= 100) {
                this.values = values;
            } else {
                throw new IllegalArgumentException("Between five and one hundred columns of values are expected");
            }
        } else {
            throw new IllegalArgumentException("Between one and ten rows of values are expected");
        }
    }

    public int getValueForRowAndColumn(int row, int column) {
        return this.values[row - 1][column - 1];
    }

    public int getRowCount() {
        return this.values.length;
    }

    public int getColumnCount() {
        return this.values[0].length;
    }

    public List<Integer> getRowsAdjacentTo(int rowNumber) {
        HashSet adjacentRows = new HashSet();
        if(this.isValidRowNumber(rowNumber)) {
            adjacentRows.add(Integer.valueOf(rowNumber));
            adjacentRows.add(Integer.valueOf(this.getRowAbove(rowNumber)));
            adjacentRows.add(Integer.valueOf(this.getRowBelow(rowNumber)));
        }

        return new ArrayList(adjacentRows);
    }

    public String asDelimitedString(String delimiter) {
        StringBuilder builder = new StringBuilder();

        for(int row = 0; row < this.values.length; ++row) {
            for(int column = 0; column < this.values[row].length; ++column) {
                builder.append(this.values[row][column]);
                if(column < this.values[row].length - 1) {
                    builder.append(delimiter);
                }
            }

            if(row < this.values.length - 1) {
                builder.append("\n");
            }
        }

        return builder.toString();
    }

    private boolean isValidRowNumber(int rowNumber) {
        return rowNumber > 0 && rowNumber <= this.values.length;
    }

    private int getRowAbove(int rowNumber) {
        int potentialRowNumber = rowNumber - 1;
        return potentialRowNumber < 1?this.values.length:potentialRowNumber;
    }

    private int getRowBelow(int rowNumber) {
        int potentialRowNumber = rowNumber + 1;
        return potentialRowNumber > this.values.length?1:potentialRowNumber;
    }
}
