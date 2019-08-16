package Automata;

import Automata.TwoDimentions.LA.LangtonAnt;
import Cell.States.CellState;
import Neighborhood.CellNeighborhood;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Automaton {
    protected HashMap<Integer, CellState> cells;
    private CellNeighborhood neighborsStrategy;


    protected void insertMap(HashMap<Integer, ? extends CellState> map){
        cells = new HashMap<>(map);
    }

    protected void insertNeighborStrategy(CellNeighborhood neighbors){
        neighborsStrategy = neighbors;
    }

    public HashMap<Integer, CellState> getCells() {
        return cells;
    }

    protected abstract CellState getChanges(ArrayList<Integer> coords);

    public HashMap<Integer, CellState> nextState(){
        try {
            HashMap<Integer, CellState> stateHashMap = new HashMap<>();
            for (Map.Entry<Integer, CellState> entry : cells.entrySet()) {
                ArrayList<Integer> coordinates = new ArrayList<>(neighborsStrategy.cellNeighbors(entry.getKey()));
                CellState newy = getChanges(coordinates);
                if (newy != entry.getValue()) {
                    stateHashMap.put(entry.getKey(), newy);
                }
            }
            for (Map.Entry<Integer, CellState> entry : stateHashMap.entrySet()) {
                cells.put(entry.getKey(), entry.getValue());
            }
            if(this instanceof LangtonAnt){
                ((LangtonAnt) this).move();
            }
            return stateHashMap;                              //tu zwracam tylko zmienione komorki, bo nie ma sensu rysowac calej mapy, kiedy tylko czesc sie zmienila
        }catch (NullPointerException e){
            System.err.println("Class Automaton/nextState/1");
            e.printStackTrace();
            return getCells();
        }
    }
}
