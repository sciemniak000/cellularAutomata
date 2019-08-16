package Automata.TwoDimentions.GoL;

import Cell.States.BinaryState;
import Cell.States.CellState;
import Neighborhood.CellNeighborhood;

import java.util.ArrayList;
import java.util.HashMap;

public class GameOfLifeGriddy extends GameOfLife {
    public GameOfLifeGriddy(HashMap<Integer, ? extends CellState> cellz, String modee, int size_x, int size_y, CellNeighborhood moor_or_neuman){
        super(cellz, modee, size_x, size_y, moor_or_neuman);
    }
    @Override
    //zarowno moor jak i vonNeuman musza podawac badana komorke pod zerowym indeksem
    protected CellState getChanges(ArrayList<Integer> coords) {
        int x,y, counter = 0;
        for(int i = 1; i < coords.size(); i++){
            int co = coords.get(i);

            if(co < 0){
                if(co%1000 >= -100){
                    x = 1000*width + co%1000;
                    y = 1000*height + co/1000;
                } else {
                    y = 1000*height + co/1000 - 1;
                    x = 1000+co%1000;
                }
            } else {
                if(co%1000 >= 900){
                    y = co/1000 + 1;
                    x = 1000*width -1000 + co%1000;
                } else {
                    x = co % 1000;
                    y = co / 1000;
                }
            }

            if(!(x >= width || y >= height)){
                if(cells.get(co) == BinaryState.ALIVE){
                    ++counter;
                }
            } else {
                while(x >= width || y >= height) {
                    if (x >= width) x = x%width;
                    if (y >= height) y = y%height;
                }
                if(cells.get(1000*y+x) == BinaryState.ALIVE){
                    ++counter;
                }
            }
        }
        if(cells.get(coords.get(0)) == BinaryState.ALIVE){
            if(stay_alive.contains(counter)){
                return BinaryState.ALIVE;
            } else {
                return BinaryState.DEAD;
            }
        } else {
            if (make_alive.contains(counter)) {
                return BinaryState.ALIVE;
            } else {
                return BinaryState.DEAD;
            }
        }
    }
}
