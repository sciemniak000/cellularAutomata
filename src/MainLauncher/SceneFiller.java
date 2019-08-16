package MainLauncher;

import Automata.OneDimension.Automaton1DimGriddy;
import Automata.OneDimension.Automaton1DimNonGriddy;
import Automata.TwoDimentions.GoL.GameOfLifeGriddy;
import Automata.TwoDimentions.GoL.GameOfLifeNonGriddy;
import Automata.TwoDimentions.LA.LangtonAntGriddy;
import Automata.TwoDimentions.LA.LangtonAntNonGriddy;
import Automata.TwoDimentions.QL.QuadLifeGriddy;
import Automata.TwoDimentions.QL.QuadLifeNonGriddy;
import Automata.TwoDimentions.WW.WireWorldGriddy;
import Automata.TwoDimentions.WW.WireWorldNonGriddy;
import Cell.States.CellState;
import Cell.States.LangtonCell;
import Neighborhood.CellNeighborhood;
import Neighborhood.MoorNeighborhood;
import Neighborhood.VonNeumanNeighborhood;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;


public class SceneFiller {
    /*
    * #0
    * Menu pierwsze
    * otwierane
    * bezposrednio
    * po
    * wlaczeniu
    * programu
     */

    public static Scene mainMenu(double width, double height, Stage stage){
        VBox layout = new VBox();
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setSpacing(height/6);

        Label label = new Label();
        label.setTextAlignment(TextAlignment.CENTER);
        label.setText("This program allows to watch cellular automata");

        Button start = new Button();
        start.setText("Start");
        start.setOnAction(e -> Mainer.setScene(stage, 1, width, height)
        );

        Button credits = new Button("Credits");
        credits.setOnAction(e -> Mainer.setScene(stage, 8, width, height));

        Button quit = new Button("Quit");
        quit.setOnAction(e -> {
            stage.close();
        });

        layout.getChildren().addAll(label, start, credits, quit);
        layout.setAlignment(Pos.CENTER);
        return new Scene(layout, width, height);
    }





    /*
    * #1
    * To jest menu, ktore uruchamiamy wciskajac przycisk start
    * to pierwszy etap wyboru, ktory automat chcemy uruchomic
     */

    public static Scene startMenu(double width, double height, Stage stage){
        VBox layout = new VBox();
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setSpacing(height/5);

        Label label = new Label();
        label.setTextAlignment(TextAlignment.CENTER);
        label.setText("Choose what automaton you want to see");

        Button dim1 = new Button("One dimension");
        dim1.setOnAction(e -> Mainer.setScene(stage, 2, width, height)
        );

        Button dim2 = new Button("Two dimensions");
        dim2.setOnAction(e -> Mainer.setScene(stage, 3, width, height));

        Button back = new Button("Back");
        back.setOnAction(e  -> Mainer.setScene(stage, 0, width, height));

        layout.getChildren().addAll(label, dim1, dim2, back);
        layout.setAlignment(Pos.CENTER);
        return new Scene(layout, width, height);
    }







    /*
    * #2
    * To jest menu ustawien dla automatu jednowymiarowego
    *
    * kolejne pola sluza do wyboru wlasciwosci automatu
     */

    public static Scene dimOneMenu(double width, double height, Stage stage){
        VBox layout = new VBox();
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setSpacing(10);

        Label label = new Label();
        label.setTextAlignment(TextAlignment.CENTER);
        label.setText("Settings\n\n" +
                "First define the mode by choosing an integer" +
                "between 0 and 255\n\n" +
                "Then choose if you want the borders " +
                "to lead to one another " +
                "they will be treated as 0 otherwise\n\n" +
                "Finally choose the size of the grid");

        TextArea rule = new TextArea();
        rule.setText("Choose the type of automaton (0-255)");

        CheckBox grid = new CheckBox("Infinite grid (recommended)");
        grid.setSelected(true);

        ChoiceBox<String> size = new ChoiceBox<>();
        size.getItems().addAll("60x1 (size of single cell: 10","30x1 (size of single cell: 20","20x1 (size of single cell: 30","15x1 (size of single cell: 40");
        size.getSelectionModel().select(0);

        Button go = new Button("Go!");
        go.setOnAction(e -> {
            boolean tr = true;
            int nump = 0;
            try{
                nump = Integer.parseInt(rule.getText());
            } catch (Exception e1){
                nump = -1;
            }
            if(nump < 0 || nump > 255){
                System.out.println("Integer is not ok");
                tr = false;
            }
            if(tr){
                int sizen = (size.getSelectionModel().getSelectedIndex() + 1) * 10;
                String bode = getMode(Integer.parseInt(rule.getText()));
                if(grid.isSelected()){
                    Player2D.display1D(new Automaton1DimGriddy(new PiervotBox().display1d(sizen,"Initial conditions"),bode,600/sizen),"Cellular automaton 1D " + rule.getText(),sizen);
                } else {
                    Player2D.display1D(new Automaton1DimNonGriddy(new PiervotBox().display1d(sizen,"Initial conditions"),bode,600/sizen),"Cellular automaton 1D " + rule.getText(),sizen);
                }
            }
        });

        Button back = new Button("Back");
        back.setOnAction(e  -> Mainer.setScene(stage, 1, width, height));

        layout.getChildren().addAll(label, rule, grid, size, go, back);
        layout.setAlignment(Pos.CENTER);
        return new Scene(layout, width, height);
    }






    /*
    * #3
    * To jest menu wyboru automatu dwuwymiarowego
    * odprowadza ono do ustawien wybranego automatu
     */

    public static Scene dimtwoMenu(double width, double height, Stage stage){
        VBox layout = new VBox();
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setSpacing(height/7);

        Label label = new Label();
        label.setTextAlignment(TextAlignment.CENTER);
        label.setText("Choose what 2dimensional automaton you want to see");

        Button gol = new Button("Game of life");
        gol.setOnAction(e -> Mainer.setScene(stage, 4, width, height)
        );

        Button la = new Button("Langton's ant");
        la.setOnAction(e -> Mainer.setScene(stage, 5, width, height)
        );

        Button ww = new Button("Wireworld");
        ww.setOnAction(e -> Mainer.setScene(stage, 6, width, height)
        );

        Button ql = new Button("Quad life");
        ql.setOnAction(e -> Mainer.setScene(stage, 7, width, height)
        );

        Button back = new Button("Back");
        back.setOnAction(e  -> Mainer.setScene(stage, 1, width, height));

        layout.getChildren().addAll(label, gol, ql, la, ww, back);
        layout.setAlignment(Pos.CENTER);
        return new Scene(layout, width, height);
    }






    /*
    * #4
    * Ustawienia do Game of Life
     */

    public static Scene GameOfLife(double width, double height, Stage stage){
        VBox layout = new VBox();
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setSpacing(10);

        Label label = new Label();
        label.setTextAlignment(TextAlignment.CENTER);
        label.setText("Settings\n\n" +
                "first choose the mode using this format: " +
                "(numbers of neighbours for which the cell stays alive " +
                "separated with comma)/(numbers of neighbors " +
                "for which the dead cell starts to live separated with comma)\n\n" +
                "Choose whether the borders should lead to one another " +
                "they will be treated as 0 otherwise\n\n" +
                "Choose the neighborhood and its size\n\n" +
                "Finally choose the size of the grid");

        TextArea rule = new TextArea();
        rule.setText("2,3/3");

        CheckBox grid = new CheckBox("Infinite grid (recommended)");
        grid.setSelected(true);

        ChoiceBox<String> neighborhood = new ChoiceBox<>();
        neighborhood.getItems().addAll("Moore neighborhood", "von Neumann neighborhood");
        neighborhood.getSelectionModel().select(0);

        TextArea r = new TextArea();
        r.setText("Neighborhood size");


        ChoiceBox<String> size = new ChoiceBox<>();
        size.getItems().addAll("60x60, rozmiar 10", "30x30, rozmiar 20", "20x20, rozmiar 30", "15x15, rozmiar 40");
        size.getSelectionModel().select(0);

        Button go = new Button("Go!");
        go.setOnAction(event -> {
            boolean can_go = true;  //indicates whether the automaton can be launched

            int nei_size = 0;   //represents the size of neighbourhood - how far it reaches

            try {               //this try block checks whether the size of neighbourhood was properly written, otherwise it sets it to -1
                nei_size = Integer.parseInt(r.getText());
            }catch (Exception e){
                nei_size = -1;
            }

            if(nei_size < 1 || nei_size > 100){      //checks if the neighbourhood has the proper size (1-100), sets can_go to false otherwise
                can_go = false;
            }

            char ck,prev;               //helpful to check whether the mode has the proper structure (num,num,...,num/num,num,...,num)
            String ok = "0123456789,/";     //contains signs acceptable in mode string
            String gotten = rule.getText();     //contains the text in rule that is mode string
            boolean cango = false;          //helpful when checking if every char from mode string is acceptable


            ck = gotten.charAt(0);          //those lines are to check whether the first sign isn't ',' or '/' which are acceptable but not at the beginning
            if (ck == '/' || ck == ',') {
                can_go = false;             //disables possibility of launching the automaton
            }


            ck = '\0';          //this is to prevent the next lines of getting confused, just there is no previous char to first char of the mode string
            for(int i = 0; i < gotten.length(); i++) {      //iterates through all chars in mode string

                prev = ck;                      //remembers previous char to get sure that there is no ",," or "//" or ",/" situation
                ck = gotten.charAt(i);          //takes the next sign from mode string

                cango = false;                      //sets to false, if is set to true in loop, then the sign is ok
                for (int j = 0; j < 12; j++) {          //iterates through the string of acceptable signs and checks whether the actual sign of mode string is acceptable
                    if (ok.charAt(j) == ck) {
                        cango = true;
                    }
                }

                if (!cango) can_go = false;         //if cango is still false, the sign is not acceptable which blocks possibility of launching the automaton

                else {                              //can_go is true, then we can move forward

                    if ((prev == '/' || prev == ',') && (ck == '/' || ck == ',')) {     //checks whether there are to non digits in a row, if so, blocks the automaton
                        can_go = false;
                    }
                }                                       //jumps to the next iteration
            }

            if(ck == ',' || ck == '/'){         //after the loop we get sure that the last character is a digit, if not, we block the automaton
                can_go = false;
            }

            if(can_go){                     //if nothing blocks the automaton, we move on

                CellNeighborhood ne;            //this is to store the type of neighbourhood that is going to be passed to automaton

                if(neighborhood.getSelectionModel().getSelectedIndex() == 0){           //by the indices of options in neighbourhood choicebox we set the correct type of ne
                    ne = new MoorNeighborhood(nei_size);
                } else {
                    ne = new VonNeumanNeighborhood(nei_size);
                }

                int sizen = (size.getSelectionModel().getSelectedIndex() + 1) * 10;    //sets the size of the side of one cell on the screen
                                                                                        //the indices are in the order that allows to use this trick with counting that will simply return 10,20,30 or 40 which are all allowed sizes

                if(grid.isSelected()){                          //checks whether to use grid looping and launches the automaton
                    Player2D.display(new GameOfLifeGriddy(new PiervotBox().display2d(sizen,8,"Initial conditions"),rule.getText(),600/sizen,600/sizen,ne),"Game of life " + rule.getText(),sizen);
                } else {
                    Player2D.display(new GameOfLifeNonGriddy(new PiervotBox().display2d(sizen,8,"Initial conditions"),rule.getText(),600/sizen,600/sizen,ne),"Game of life " + rule.getText(),sizen);
                }
            }
        });


        Button back = new Button("Back");
        back.setOnAction(e  -> Mainer.setScene(stage, 3, width, height));

        layout.getChildren().addAll(label, rule, grid, neighborhood, r, size, go, back);
        layout.setAlignment(Pos.CENTER);
        return new Scene(layout, width, height);
    }






    /*
    * #5
    * Ustawienia do mrowki Langtona
     */

    public static Scene LangtonAnt(double width, double height, Stage stage){
        VBox layout = new VBox();
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setSpacing(10);

        Label label = new Label();
        label.setTextAlignment(TextAlignment.CENTER);
        label.setText("Settings\n\n" +
                "Choose whether the grid is to be looped\n" +
                "Choose the size");

        CheckBox grid = new CheckBox("Infinite grid (recommended)");
        grid.setSelected(true);

        ChoiceBox<String> size = new ChoiceBox<>();
        size.getItems().addAll("60x60, rozmiar 10", "30x30, rozmiar 20", "20x20, rozmiar 30", "15x15, rozmiar 40");
        size.getSelectionModel().select(0);


        Button go = new Button("Go!");
        go.setOnAction(e ->{
            int sizen = (size.getSelectionModel().getSelectedIndex() + 1) * 10;

            Pair<HashMap<Integer,CellState>, ArrayList<LangtonCell>> pair = new PiervotBox().displayofants(sizen, "Set initial conditions");
            if(grid.isSelected()){
                Player2D.display(new LangtonAntGriddy(pair.getKey(), 600/sizen, 600/sizen,pair.getValue()),"Langton's Ant", sizen);
            } else {
                Player2D.display(new LangtonAntNonGriddy(pair.getKey(), 600/sizen, 600/sizen,pair.getValue()),"Langton's Ant", sizen);
            }
        });

        Button back = new Button("Back");
        back.setOnAction(e  -> Mainer.setScene(stage, 3, width, height));

        layout.getChildren().addAll(label, grid, size, go, back);
        layout.setAlignment(Pos.CENTER);
        return new Scene(layout, width, height);
    }







    /*
    * #6
    * Ustawienia do Wireworld
     */

    public static Scene Wireworld(double width, double height, Stage stage){
        VBox layout = new VBox();
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setSpacing(10);

        Label label = new Label();
        label.setTextAlignment(TextAlignment.CENTER);
        label.setText("Settings\n\n" +
                "Choose if the grid is to be looped\n" +
                "Choose the neighborhood and it's size (recommended size is 1)\n" +
                "Choose the size of the grid");

        CheckBox grid = new CheckBox("Infinite grid");
        grid.setSelected(true);

        ChoiceBox<String> neighborhood = new ChoiceBox<>();
        neighborhood.getItems().addAll("Moore Neighborhood", "von Neumann Neighborhood");
        neighborhood.getSelectionModel().select(0);

        TextArea r = new TextArea();
        r.setText("Neighborhood size (1 recommended)");


        ChoiceBox<String> size = new ChoiceBox<>();
        size.getItems().addAll("60x60, rozmiar 10", "30x30, rozmiar 20", "20x20, rozmiar 30", "15x15, rozmiar 40");
        size.getSelectionModel().select(0);


        Button go = new Button("Go!");
        go.setOnAction(e -> {
            int sieze = 0;          //to store the size of the neighborhood

            try {                   //checks whether the string from the text field neighborsize is a string, if not, sets sieze as -1
                sieze = Integer.parseInt(r.getText());
            } catch (Exception e1) {
                sieze = -1;
            }

            boolean can_go = true;          //bool deciding if the automaton can be launched

            if (sieze < 1 || sieze > 100) {       //blocks the automaton launch if the size of the neighborhood is inappropriate
                can_go = false;
            }

            if (can_go) {             //if all is ok, we can move to launching procedure

                CellNeighborhood ne;        //stores the neighborhood (and its type) to be given to automaton

                if(neighborhood.getSelectionModel().getSelectedIndex() == 0){       //initializes ne with the neighborhood chosen in neighborhood choice box
                    ne = new MoorNeighborhood(sieze);
                } else {
                    ne = new VonNeumanNeighborhood(sieze);
                }

                int cell_size = (size.getSelectionModel().getSelectedIndex()+1)*10;    //stores the size of the side of a single cell, due to the order the options are kept in choice box size, the size can be got with this mathematical trick

                if(grid.isSelected()) {          //launches the automaton with looped or not looped grid according to the grid check box
                    Player2D.display(new WireWorldGriddy(new PiervotBox().display2d(cell_size,6,"Initial conditions"),600/cell_size, 600/cell_size, ne), "Wireworld", cell_size);
                } else {
                    Player2D.display(new WireWorldNonGriddy(new PiervotBox().display2d(cell_size,6,"Initial conditions"),600/cell_size, 600/cell_size, ne), "Wireworld", cell_size);
                }
            }
        });

        Button back = new Button("Back");
        back.setOnAction(e  -> Mainer.setScene(stage, 3, width, height));

        layout.getChildren().addAll(label, grid, neighborhood, r, size, go, back);
        layout.setAlignment(Pos.CENTER);
        return new Scene(layout, width, height);
    }





    /*
    * #7
    * Ustawienia do Quad Life
     */

    public static Scene QuadLife(double width, double height, Stage stage){
        VBox layout = new VBox();
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setSpacing(10);

        Label label = new Label();
        label.setTextAlignment(TextAlignment.CENTER);
        label.setText("Settings\n\n" +
                "Choose whether the grid is to be looped\n" +
                "Choose the neighborhood and it's size\n" +
                "Choose the size of the grid");

        CheckBox grid = new CheckBox("Infinite grid");
        grid.setSelected(true);

        ChoiceBox<String> neighborhood = new ChoiceBox<>();
        neighborhood.getItems().addAll("Moore Neighborhood", "von Neumann Neighborhood");
        neighborhood.getSelectionModel().select(0);

        TextArea r = new TextArea();
        r.setText("Neighborhood size (1 recommended)");


        ChoiceBox<String> size = new ChoiceBox<>();
        size.getItems().addAll("60x60, rozmiar 10", "30x30, rozmiar 20", "20x20, rozmiar 30", "15x15, rozmiar 40");
        size.getSelectionModel().select(0);

        Button go = new Button("Go!");
        go.setOnAction(e -> {
            int sieze = 0;          //to store the size of the neighborhood

            try {                   //checks whether the string from the text field neighborsize is a string, if not, sets sieze as -1
                sieze = Integer.parseInt(r.getText());
            } catch (Exception e1){
                sieze = -1;
            }

            boolean can_go = true;          //bool deciding if the automaton can be launched

            if(sieze < 1 || sieze > 100){       //blocks the automaton launch if the size of the neighborhood is inappropriate
                can_go = false;
            }

            if(can_go){             //if all is ok, we can move to launching procedure

                CellNeighborhood ne;        //stores the neighborhood (and its type) to be given to automaton

                if(neighborhood.getSelectionModel().getSelectedIndex() == 0){       //initializes ne with the neighborhood chosen in neighborhood choice box
                    ne = new MoorNeighborhood(sieze);
                } else {
                    ne = new VonNeumanNeighborhood(sieze);
                }

                int cell_size = (size.getSelectionModel().getSelectedIndex()+1)*10;    //stores the size of the side of a single cell, due to the order the options are kept in choice box size, the size can be got with this mathematical trick

                if(grid.isSelected()){          //launches the automaton with looped or not looped grid according to the grid check box
                    Player2D.display(new QuadLifeGriddy(new PiervotBox().display2d(cell_size,7,"Quad life initial conditions"),600/cell_size,600/cell_size,ne),"Quad life", cell_size);
                } else {
                    Player2D.display(new QuadLifeNonGriddy(new PiervotBox().display2d(cell_size,7,"Quad life initial conditions"),600/cell_size,600/cell_size,ne),"Quad life", cell_size);
                }
            }
        });

        Button back = new Button("Back");
        back.setOnAction(e  -> Mainer.setScene(stage, 3, width, height));

        layout.getChildren().addAll(label, grid, neighborhood, r, size, go, back);
        layout.setAlignment(Pos.CENTER);
        return new Scene(layout, width, height);
    }






    /*
    #8
    Informacje o tworcach
     */

    public static Scene Credits(double width, double height, Stage stage){
        VBox layout = new VBox();
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setSpacing(height/3);

        Label label = new Label();
        label.setText("This program is a final project " +
                "of object programming\n" +
                "of Przemek Indyka and Wojciech Wieckowski,\n" +
                "students of\n" +
                "AGH University of Science and Technology " +
                "in Cracow, Poland");

        Button back = new Button("Back");
        back.setOnAction(e  -> Mainer.setScene(stage, 0, width, height));

        layout.getChildren().addAll(label, back);
        layout.setAlignment(Pos.CENTER);
        return new Scene(layout, width, height);
    }

    private static String getMode(int num){     //used actually only to get the binary to the 1-dimensional automaton
        if(num < 0 || num > 255){
            return "Fail";
        }
        String rule = "";
        while (rule.length() != 8){
            if(num % 2 == 1){
                rule = "1".concat(rule);
                num = (num -1)/2;
            } else {
                rule = "0".concat(rule);
                num /= 2;
            }
        }
        return rule;
    }
}
