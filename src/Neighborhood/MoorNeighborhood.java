package Neighborhood;

import java.util.ArrayList;

public class MoorNeighborhood implements CellNeighborhood{
    public MoorNeighborhood(int prom){
        r = prom;
    }
    private final int r;
    @Override
    public ArrayList<Integer> cellNeighbors(Integer cell) {
        int x = cell%1000, y = cell/1000;
        ArrayList<Integer> neighbors = new ArrayList<>();
        neighbors.add(cell);
        for(int i = x - r; i <= x + r; i++){
            for(int j = y - r; j <= y + r; j++){
                if(i != x || j != y){
                    neighbors.add(1000*j+i);
                }
            }
        }
        return neighbors;
    }
}
