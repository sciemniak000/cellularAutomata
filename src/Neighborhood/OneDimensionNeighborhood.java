package Neighborhood;

import java.util.ArrayList;

public class OneDimensionNeighborhood implements CellNeighborhood {
    @Override
    public ArrayList<Integer> cellNeighbors(Integer cell) {
        ArrayList<Integer> coords = new ArrayList<>();
        coords.add(cell-1);
        coords.add(cell);
        coords.add(cell+1);

        return coords;
    }
}