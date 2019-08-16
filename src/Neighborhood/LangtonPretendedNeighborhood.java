package Neighborhood;

import java.util.ArrayList;

public class LangtonPretendedNeighborhood implements CellNeighborhood {
    @Override
    public ArrayList<Integer> cellNeighbors(Integer cell) {
        ArrayList<Integer> result = new ArrayList<>();
        result.add(cell);
        return result;
    }
}
