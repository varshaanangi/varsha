package com.varsha.lowcost.model;


public class LowPathCollector {
    private LowPath bestPath;
    private LowPathComparator comparator = new LowPathComparator();

    public LowPathCollector() {
    }

    public LowPath getBestPath() {
        return this.bestPath;
    }

    public void addPath(LowPath newPath) {
        if(this.comparator.compare(newPath, this.bestPath) < 0) {
            this.bestPath = newPath;
        }

    }
}
