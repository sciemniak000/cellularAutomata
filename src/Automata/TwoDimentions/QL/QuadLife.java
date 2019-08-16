package Automata.TwoDimentions.QL;

import Automata.TwoDimentions.Automaton2Dim;
import Cell.States.CellState;
import Cell.States.QuadState;
import Neighborhood.CellNeighborhood;

import java.util.HashMap;

public abstract class QuadLife extends Automaton2Dim {
    protected QuadLife(HashMap<Integer, ? extends CellState> cellz, int size_x, int size_y, CellNeighborhood moor_or_neuman) {
        insertMap(cellz);
        width = size_x;
        height = size_y;
        insertNeighborStrategy(moor_or_neuman);
    }

    protected CellState whichColortoMakealive(int red, int green, int yellow, int blue){
        if(red == 2 || red == 3){
            return QuadState.RED;
        } else if(green == 2 || green == 3){
            return QuadState.GREEN;
        } else if(blue == 2 || blue == 3){
            return QuadState.BLUE;
        } else if(yellow == 2 || yellow == 3){
            return QuadState.YELLOW;
        } else if(red == 0){
            return QuadState.RED;
        } else if(green == 0){
            return QuadState.GREEN;
        } else if(blue == 0){
            return QuadState.BLUE;
        } else if(yellow == 0){
            return QuadState.YELLOW;
        } else {
            return QuadState.DEAD;
        }
    }
}
