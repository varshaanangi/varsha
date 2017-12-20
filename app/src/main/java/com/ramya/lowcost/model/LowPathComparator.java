package com.varsha.lowcost.model;

import java.util.Comparator;

public class LowPathComparator implements Comparator<LowPath> {
    private static int SORT_LEFT_FIRST = -1;
    private static int SORT_RIGHT_FIRST = 1;
    private static int SORT_EQUAL = 0;

    public LowPathComparator() {
    }

    public int compare(LowPath leftPath, LowPath rightPath) {
        int comparedLength = this.compareLengths(leftPath, rightPath);
        return comparedLength != 0?comparedLength:this.compareCosts(leftPath, rightPath);
    }

    private int compareLengths(LowPath leftPath, LowPath rightPath) {
        int leftLength = this.getLengthFromPath(leftPath);
        int rightLength = this.getLengthFromPath(rightPath);
        return leftLength > rightLength?SORT_LEFT_FIRST:(leftLength == rightLength?SORT_EQUAL:SORT_RIGHT_FIRST);
    }

    private int compareCosts(LowPath leftPath, LowPath rightPath) {
        int leftCost = this.getCostFromPath(leftPath);
        int rightCost = this.getCostFromPath(rightPath);
        return leftCost < rightCost?SORT_LEFT_FIRST:(leftCost == rightCost?SORT_EQUAL:SORT_RIGHT_FIRST);
    }

    private int getLengthFromPath(LowPath path) {
        return path != null?path.getPathLength():-2147483648;
    }

    private int getCostFromPath(LowPath path) {
        return path != null?path.getTotalCost():2147483647;
    }
}
