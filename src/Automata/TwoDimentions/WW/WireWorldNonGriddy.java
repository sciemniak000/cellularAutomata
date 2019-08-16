package Automata.TwoDimentions.WW;

import Cell.States.CellState;
import Cell.States.WireElectronState;
import Neighborhood.CellNeighborhood;

import java.util.ArrayList;
import java.util.HashMap;

public class WireWorldNonGriddy extends WireWorld {
    public WireWorldNonGriddy(HashMap<Integer, ? extends CellState> cellz, int size_x, int size_y, CellNeighborhood moor_or_neuman){
        super(cellz, size_x, size_y, moor_or_neuman);
    }


    @Override
    protected CellState getChanges(ArrayList<Integer> coords) {
        if(cells.get(coords.get(0)) == WireElectronState.VOID){
            return WireElectronState.VOID;
        } else if(cells.get(coords.get(0)) == WireElectronState.ELECTRON_HEAD){
            return WireElectronState.ELECTRON_TAIL;
        } else if(cells.get(coords.get(0)) == WireElectronState.ELECTRON_TAIL){
            return WireElectronState.WIRE;
        } else {
            int x,y,counter = 0;
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
                    if(cells.get(coords.get(i)) == WireElectronState.ELECTRON_HEAD){
                        counter++;
                    }
                }
            }
            if(counter == 1 || counter == 2){
                return WireElectronState.ELECTRON_HEAD;
            } else {
                return WireElectronState.WIRE;
            }
        }
    }
}
