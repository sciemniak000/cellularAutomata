package Automata.TwoDimentions.LA;


import Automata.TwoDimentions.Automaton2Dim;
import Cell.States.BinaryState;
import Cell.States.CellState;
import Cell.States.LangtonCell;
import Neighborhood.LangtonPretendedNeighborhood;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class LangtonAnt extends Automaton2Dim {
    protected LangtonAnt(HashMap<Integer, ? extends CellState> cellz, int size_x, int size_y, ArrayList<LangtonCell> anties){
        insertMap(cellz);
        ants = new ArrayList<>(anties);
        width = size_x;
        height = size_y;
        insertNeighborStrategy(new LangtonPretendedNeighborhood());
    }

    protected CellState getChanges(ArrayList<Integer> coords) {
        Integer where = coords.get(0);
        boolean changeState = false;
        for(LangtonCell ant: ants){
            if(ant.whereItIs.equals(where)){
                changeState = true;
                if (cells.get(where) == BinaryState.ALIVE){
                    ant.counterclockwiseChange();
                } else {
                    ant.clockwiseChange();
                }
            }
        }
        if(!changeState){
            return cells.get(where);
        } else {
            if(cells.get(where) == BinaryState.ALIVE){
                return BinaryState.DEAD;
            }
            else return BinaryState.ALIVE;
        }
    }

    public ArrayList<LangtonCell> getAnts() {
        return ants;
    }

    public abstract void move();

    protected ArrayList<LangtonCell> ants;
}
