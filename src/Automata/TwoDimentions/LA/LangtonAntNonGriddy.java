package Automata.TwoDimentions.LA;

import Cell.States.CellState;
import Cell.States.LangtonCell;

import java.util.ArrayList;
import java.util.HashMap;

public class LangtonAntNonGriddy extends LangtonAnt {
    public LangtonAntNonGriddy(HashMap<Integer, CellState> cellz, int size_x, int size_y, ArrayList<LangtonCell> anties){
        super(cellz, size_x, size_y, anties);
    }

    @Override
    public void move() {
        for(LangtonCell ant : ants){
            switch (ant.antState){
                case EAST:
                    if(ant.whereItIs%1000 + 1 < width) {
                        ant.whereItIs += 1;
                    }
                    break;
                case WEST:
                    if(ant.whereItIs%1000 - 1 >= 0) {
                        ant.whereItIs -= 1;
                    }
                    break;
                case SOUTH:
                    if(ant.whereItIs/1000 + 1 < height) {
                        ant.whereItIs = (ant.whereItIs/1000 + 1)*1000+ant.whereItIs%1000;
                    }
                    break;
                case NORTH:
                    if(ant.whereItIs/1000 - 1 >= 0) {
                        ant.whereItIs = (ant.whereItIs/1000-1) * 1000 + ant.whereItIs%1000;
                    }
                    break;
            }
        }
    }
}
