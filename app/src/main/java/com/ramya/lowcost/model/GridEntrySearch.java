package com.varsha.lowcost.model;

import java.util.ArrayList;
import java.util.Collections;

public class GridEntrySearch {

    private GridEntry grid;
    private LowPathComparator pathComparator;

    public GridEntrySearch(GridEntry grid) {
        if(grid == null) {
            throw new IllegalArgumentException("A grid is required");
        } else {
            this.grid = grid;
            this.pathComparator = new LowPathComparator();
        }
    }

    public LowPath getBestPathForGrid() {
        ArrayList allPaths = new ArrayList();

        for(int row = 1; row <= this.grid.getRowCount(); ++row) {
            RowSearch rowSearch = new RowSearch(row, this.grid, new LowPathCollector());
            allPaths.add(rowSearch.getBestPathForRow());
        }

        Collections.sort(allPaths, this.pathComparator);
        return (LowPath)allPaths.get(0);
    }
}
