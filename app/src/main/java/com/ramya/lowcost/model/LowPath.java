package com.varsha.lowcost.model;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LowPath {
    public static int MAXIMUM_COST = 50;
    private List<Integer> rowsTraversed = new ArrayList();
    private int totalCost = 0;
    private int expectedLength = 0;

    LowPath(int expectedLength) {
        this.expectedLength = expectedLength;
    }

    LowPath(LowPath anotherPath) {
        this.totalCost = anotherPath.totalCost;
        this.expectedLength = anotherPath.expectedLength;
        Iterator var2 = anotherPath.rowsTraversed.iterator();

        while(var2.hasNext()) {
            int rowTraversed = ((Integer)var2.next()).intValue();
            this.rowsTraversed.add(Integer.valueOf(rowTraversed));
        }

    }

    public List<Integer> getRowsTraversed() {
        return this.rowsTraversed;
    }

    public int getTotalCost() {
        return this.totalCost;
    }

    public int getPathLength() {
        return this.rowsTraversed.size();
    }

    public void addRowWithCost(int row, int cost) {
        this.rowsTraversed.add(Integer.valueOf(row));
        this.totalCost += cost;
    }

    public boolean isComplete() {
        return this.rowsTraversed.size() == this.expectedLength;
    }

    public boolean isSuccessful() {
        return this.isComplete() && !this.isOverCost();
    }

    public boolean isOverCost() {
        return this.totalCost > MAXIMUM_COST;
    }
}
