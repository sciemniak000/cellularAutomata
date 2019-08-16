package Automata.OneDimension;

import Cell.States.BinaryState;
import Cell.States.CellState;

import java.util.ArrayList;
import java.util.HashMap;

//sieze to ilosc komorek

//mode to string zawierajacy binarna reprezentacje liczby naturalnej, na podstawie ktorej okreslilismy typ automatu

//cells to koordynaty sasiadow
public class Automaton1DimGriddy extends Automaton1Dim {
    public Automaton1DimGriddy(HashMap<Integer, ? extends CellState> cellls, String mode, int sieze){
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
            first = cells.get(coords.get(0) + size);
        } else {
            first = cells.get(coords.get(0));
        }
        CellState second = cells.get(coords.get(1));
        CellState third;

        if(coords.get(2) >= size) {
            third = cells.get(coords.get(2) - size);
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
