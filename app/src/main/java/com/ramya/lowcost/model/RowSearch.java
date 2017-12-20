package com.varsha.lowcost.model;


import java.util.Iterator;
import java.util.List;


public class RowSearch {
    private int row;
    private GridEntry grid;
    private LowPathCollector pathCollector;

    public RowSearch(int startRow, GridEntry grid, LowPathCollector collector) {
        if(grid == null) {
            throw new IllegalArgumentException("A grid is required");
        } else if(collector == null) {
            throw new IllegalArgumentException("A visitor requires a collector");
        } else if(startRow > 0 && startRow <= grid.getRowCount()) {
            this.row = startRow;
            this.grid = grid;
            this.pathCollector = collector;
        } else {
            throw new IllegalArgumentException("Cannot visit a row outside of grid boundaries");
        }
    }

    public LowPath getBestPathForRow() {
        LowPath initialPath = new LowPath(this.grid.getColumnCount());
        this.visitPathsForRow(this.row, initialPath);
        return this.pathCollector.getBestPath();
    }

    private void visitPathsForRow(int row, LowPath path) {
        if(this.canVisitRowOnPath(row, path)) {
            this.visitRowOnPath(row, path);
        }

        List adjacentRows = this.grid.getRowsAdjacentTo(row);
        boolean currentPathAdded = false;
        Iterator var5 = adjacentRows.iterator();

        while(var5.hasNext()) {
            int adjacentRow = ((Integer)var5.next()).intValue();
            if(this.canVisitRowOnPath(adjacentRow, path)) {
                LowPath pathCopy = new LowPath(path);
                this.visitPathsForRow(adjacentRow, pathCopy);
            } else if(!currentPathAdded) {
                this.pathCollector.addPath(path);
                currentPathAdded = true;
            }
        }

    }

    private boolean canVisitRowOnPath(int row, LowPath path) {
        return !path.isComplete() && !this.nextVisitOnPathWouldExceedMaximumCost(row, path);
    }

    private void visitRowOnPath(int row, LowPath path) {
        int columnToVisit = path.getPathLength() + 1;
        path.addRowWithCost(row, this.grid.getValueForRowAndColumn(row, columnToVisit));
    }

    private boolean nextVisitOnPathWouldExceedMaximumCost(int row, LowPath path) {
        int nextColumn = path.getPathLength() + 1;
        return path.getTotalCost() + this.grid.getValueForRowAndColumn(row, nextColumn) > LowPath.MAXIMUM_COST;
    }
}
