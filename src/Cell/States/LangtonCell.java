package Cell.States;

import java.util.ArrayList;

public class LangtonCell implements CellState {
    public LangtonCell(AntState actual, Integer position){
        states = new ArrayList<>();
        states.add(AntState.EAST);
        states.add(AntState.SOUTH);
        states.add(AntState.WEST);
        states.add(AntState.NORTH);

        antState = actual;
        whereItIs = position;
    }
    private ArrayList<AntState> states;
    public AntState antState;
    public Integer whereItIs;


    public void clockwiseChange(){
        int index = states.indexOf(antState) + 1 >= states.size() ? 0 : states.indexOf(antState) + 1;
        antState = states.get(index);
    }

    public void counterclockwiseChange(){
        int index = states.indexOf(antState) - 1 < 0 ? states.size() - 1 : states.indexOf(antState) - 1;
        antState = states.get(index);
    }
}
