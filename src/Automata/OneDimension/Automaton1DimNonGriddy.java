package Automata.OneDimension;

import Cell.States.BinaryState;
import Cell.States.CellState;

import java.util.ArrayList;
import java.util.HashMap;

public class Automaton1DimNonGriddy extends Automaton1Dim {
    public Automaton1DimNonGriddy(HashMap<Integer, ? extends CellState> cellls, String mode, int sieze) {
        super(cellls, mode, sieze);
    }

    @Override
    protected CellState getChanges(ArrayList<Integer> coords) {
        if(coords.size() != 3){
            return null;
        }
        if(mode.length() != 8){
            return null;
        }

        CellState first;
        if(coords.get(0) < 0){
            first = BinaryState.DEAD;
        } else {
            first = cells.get(coords.get(0));
        }
        CellState second = cells.get(coords.get(1));
        CellState third;

        if(coords.get(2) >= size) {
            third = BinaryState.DEAD;
        } else {
            third = cells.get(coords.get(2));
        }


        int index = 0;

        if(first == BinaryState.DEAD){
            index += 4;
        }
        if(second == BinaryState.DEAD){
            index += 2;
        }
        if(third == BinaryState.DEAD){
            ++index;
        }

        return possibilities.get(index);
    }
}
