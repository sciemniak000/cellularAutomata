package Automata.OneDimension;

import Automata.Automaton;
import Cell.States.BinaryState;
import Cell.States.CellState;
import Neighborhood.OneDimensionNeighborhood;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Automaton1Dim extends Automaton{
    protected Automaton1Dim(HashMap<Integer, ? extends CellState> cellls, String mode, int sieze){
        insertMap(cellls);
        insertNeighborStrategy(new OneDimensionNeighborhood());
        setMode(mode);
        size = sieze;
        possibilities = new ArrayList<>();
        for(int i = 0; i < mode.length(); i++){
            if(mode.charAt(i) == '1'){
                possibilities.add(BinaryState.ALIVE);
            } else {
                possibilities.add(BinaryState.DEAD);
            }
        }
    }
    protected String mode;
    protected int size;
    protected ArrayList<CellState> possibilities;

    protected void setMode(String actualmode){
        mode = actualmode;
    }
}
