package Automata.TwoDimentions.QL;

import Cell.States.CellState;
import Cell.States.QuadState;
import Neighborhood.CellNeighborhood;

import java.util.ArrayList;
import java.util.HashMap;

public class QuadLifeNonGriddy extends QuadLife {
    public QuadLifeNonGriddy(HashMap<Integer, ? extends CellState> cellz, int size_x, int size_y, CellNeighborhood moor_or_neuman){
        super(cellz, size_x, size_y, moor_or_neuman);
    }

    @Override
    protected CellState getChanges(ArrayList<Integer> coords) {
        int x, y, red = 0, yellow = 0, blue = 0, green = 0, counter = 0;

        for(int i = 1; i < coords.size(); i++) {
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

            if (!(x >= width || y >= height)) {
                if (cells.get(coords.get(i)) != QuadState.DEAD) {
                    switch ((QuadState) cells.get(coords.get(i))) {
                        case RED:
                            red++;
                            break;
                        case BLUE:
                            blue++;
                            break;
                        case GREEN:
                            green++;
                            break;
                        case YELLOW:
                            yellow++;
                            break;
                    }
                    counter++;
                }
            }
        }
        if(cells.get(coords.get(0)) != QuadState.DEAD){
            if(counter == 2 || counter == 3){
                return cells.get(coords.get(0));
            } else {
                return QuadState.DEAD;
            }
        } else {
            if(counter == 3){
                return whichColortoMakealive(red,green,yellow, blue);
            } else {
                return QuadState.DEAD;
            }
        }
    }
}
