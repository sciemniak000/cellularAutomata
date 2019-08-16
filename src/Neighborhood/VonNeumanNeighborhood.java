package Neighborhood;

import java.util.ArrayList;

public class VonNeumanNeighborhood implements CellNeighborhood{
    public VonNeumanNeighborhood(int prom){
        r = prom;
    }

    private final int r;
    @Override
    public ArrayList<Integer> cellNeighbors(Integer cell) {
        int x = cell%1000;
        int y = cell/1000;

        ArrayList<Integer> result = new ArrayList<>();
        result.add(cell);
        boolean bigger = true;
        int actual = 0;
        for(int i = x - r; i <= x + r; i++){
            for(int j = y - actual; j <= y + actual; j++){
                if(i != x || j != y){
                    result.add(1000*j+i);
                }
            }
            if(bigger){
                actual++;
            } else {
                actual--;
            }
            if(actual == r){
                bigger = false;
            }
        }
        return result;
    }
}
