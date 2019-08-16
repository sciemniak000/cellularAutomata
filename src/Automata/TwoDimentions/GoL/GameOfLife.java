package Automata.TwoDimentions.GoL;

import Automata.TwoDimentions.Automaton2Dim;
import Cell.States.CellState;
import Neighborhood.CellNeighborhood;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class GameOfLife extends Automaton2Dim {
    protected GameOfLife(HashMap<Integer, ? extends CellState> cellz, String modee, int size_x, int size_y, CellNeighborhood moor_or_neuman){
        insertMap(cellz);
        setMode(modee);
        width = size_x;
        height = size_y;
        insertNeighborStrategy(moor_or_neuman);
        stay_alive = new ArrayList<>();
        make_alive = new ArrayList<>();
        boolean first = true;
        String temp = "";
        for(int i = 0; i < mode.length(); i++){
            if(mode.charAt(i) == ','){
                int tempint = Integer.parseInt(temp);
                if(first){
                    stay_alive.add(tempint);
                } else {
                    make_alive.add(tempint);
                }
                temp = "";
            } else if(mode.charAt(i) == '/'){
                int tempint = Integer.parseInt(temp);
                stay_alive.add(tempint);
                temp = "";
                first = false;
            } else {
                temp = temp + mode.charAt(i);
            }
        }
        make_alive.add(Integer.parseInt(temp));
    }

    protected String mode;

    protected void setMode(String actualmode){
        mode = actualmode;
    }

    protected ArrayList<Integer> make_alive;
    protected ArrayList<Integer> stay_alive;
}
