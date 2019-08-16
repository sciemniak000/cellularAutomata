package Automata.TwoDimentions.WW;


import Automata.TwoDimentions.Automaton2Dim;

import Cell.States.CellState;

import Neighborhood.CellNeighborhood;

import java.util.HashMap;


public abstract class WireWorld extends Automaton2Dim {
    protected WireWorld(HashMap<Integer, ? extends CellState> cellz, int size_x, int size_y, CellNeighborhood moor_or_neuman) {
        insertMap(cellz);
        width = size_x;
        height = size_y;
        insertNeighborStrategy(moor_or_neuman);
    }
}
