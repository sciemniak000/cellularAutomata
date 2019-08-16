package MainLauncher;

import Cell.States.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


//mode 6 = wireworld, mode 7 = quad life, mode 8 = game of life
public class PiervotBox {

    public HashMap<Integer, CellState> display2d(int size, int mode, String title) {
        HashMap<Integer, CellState> esrult = new HashMap<>();
        for (int i = 0; i < 600/size; ++i) {
            for (int j = 0; j < 600/size; j++) {
                if (mode == 6) {
                    esrult.put(1000*j+i, WireElectronState.VOID);
                } else if (mode == 7) {
                    esrult.put(1000*j+i, QuadState.DEAD);
                } else {
                    esrult.put(1000*j+i, BinaryState.DEAD);
                }
            }
        }
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        Group root = new Group();
        Canvas canvas = new Canvas(600, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(2);

        gc.setFill(Player2D.Colour(esrult.get(0)));
        for (Map.Entry<Integer, CellState> entry : esrult.entrySet()) {
            gc.fillRect((entry.getKey()%1000)* size, (entry.getKey()/1000) * size, size, size);
            gc.strokeRect((entry.getKey()%1000) * size, (entry.getKey()/1000) * size, size, size);
        }

        EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
            int help_x = 0, help_y = 0, help_2_x = 0, help_2_y = 0, x, y;

            @Override
            public void handle(MouseEvent mouseEvent) {

                if(mouseEvent.getX() >= 0 && mouseEvent.getX() < 600 && mouseEvent.getY() >=0 && mouseEvent.getY() < 600) {
                    if(mouseEvent.getEventType().getName().equals("MOUSE_PRESSED")){
                        help_x = (int) (mouseEvent.getX() / size);
                        help_y = (int) (mouseEvent.getY() / size);
                        gc.setFill(Player2D.Colour(eturnStates(esrult.get(help_x + help_y * 1000))));
                        gc.fillRect(help_x*size,help_y*size, size, size);
                        gc.strokeRect(help_x*size,help_y*size, size, size);
                    } else if(mouseEvent.getEventType().getName().equals("MOUSE_RELEASED")){
                        help_2_x = (int) (mouseEvent.getX()/size);
                        help_2_y = (int) (mouseEvent.getY()/size);
                        for(x = Math.min(help_x,help_2_x); x <= Math.max(help_x,help_2_x); x++){
                            for(y = Math.min(help_y,help_2_y); y <= Math.max(help_y,help_2_y); y++){
                                esrult.put(y*1000+x,eturnStates(esrult.get(y*1000+x)));
                            }
                        }
                    } else if(mouseEvent.getEventType().getName().equals("MOUSE_DRAGGED")){
                        for(Map.Entry<Integer,CellState> entry : esrult.entrySet()){
                            gc.setFill(Player2D.Colour(entry.getValue()));
                            gc.fillRect((entry.getKey()%1000)*size,(entry.getKey()/1000)*size,size,size);
                            gc.strokeRect((entry.getKey()%1000)*size,(entry.getKey()/1000)*size,size,size);
                        }
                        help_2_x = (int) (mouseEvent.getX()/size);
                        help_2_y = (int) (mouseEvent.getY()/size);
                        for(x = Math.min(help_x,help_2_x); x <= Math.max(help_x,help_2_x); x++){
                            for(y = Math.min(help_y,help_2_y); y <= Math.max(help_y,help_2_y); y++){
                                gc.setFill(Player2D.Colour(eturnStates(esrult.get(y*1000+x))));
                                gc.fillRect(x*size,y*size,size,size);
                                gc.strokeRect(x*size,y*size,size,size);
                            }
                        }
                    }
                }
            }

        };

        root.setOnMousePressed(mouseHandler);
        root.setOnMouseReleased(mouseHandler);
        root.setOnMouseDragged(mouseHandler);


        StackPane right = new StackPane();
        Button go = new Button("Go!");
        go.setOnAction(event -> {
            window.close();
        });
        right.getChildren().addAll(go);
        right.setPadding(new Insets(10,20,10,20));

        BorderPane layout = new BorderPane();
        layout.setCenter(root);
        layout.setRight(right);

        Scene scene = new Scene(layout);
        window.setScene(scene);

        window.showAndWait();

        return esrult;
    }

    public HashMap<Integer, CellState> display1d(int sizen, String title) {

        int size = 2*sizen;
        HashMap<Integer, CellState> esrult = new HashMap<>();
        for (int i = 0; i < 600/sizen; ++i) {
                esrult.put(i, BinaryState.DEAD);
        }
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        Group root = new Group();
        Canvas canvas = new Canvas(1200, size);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(2);

        gc.setFill(Player2D.Colour(esrult.get(0)));
        for (Map.Entry<Integer, CellState> entry : esrult.entrySet()) {
            gc.fillRect(entry.getKey()* size, 0, size, size);
            gc.strokeRect(entry.getKey()* size, 0, size, size);
            }

        EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
            int help_x = 0, help_2_x = 0, x;

            @Override
            public void handle(MouseEvent mouseEvent) {

                if(mouseEvent.getX() >= 0 && mouseEvent.getX() < 1200 && mouseEvent.getY() >=0 && mouseEvent.getY() < size) {
                    if(mouseEvent.getEventType().getName().equals("MOUSE_PRESSED")){
                        help_x = (int) (mouseEvent.getX() / size);
                        gc.setFill(Player2D.Colour(eturnStates(esrult.get(help_x))));
                        gc.fillRect(help_x*size,0, size, size);
                        gc.strokeRect(help_x*size,0, size, size);
                    } else if(mouseEvent.getEventType().getName().equals("MOUSE_RELEASED")){
                        help_2_x = (int) (mouseEvent.getX()/size);
                        for(x = Math.min(help_x,help_2_x); x <= Math.max(help_x,help_2_x); x++){
                            esrult.put(x,eturnStates(esrult.get(x)));
                        }
                    } else if(mouseEvent.getEventType().getName().equals("MOUSE_DRAGGED")){
                        for(Map.Entry<Integer,CellState> entry : esrult.entrySet()){
                            gc.setFill(Player2D.Colour(entry.getValue()));
                            gc.fillRect(entry.getKey()*size,0,size,size);
                            gc.strokeRect(entry.getKey()*size,0,size,size);
                        }
                        help_2_x = (int) (mouseEvent.getX()/size);
                        for(x = Math.min(help_x,help_2_x); x <= Math.max(help_x,help_2_x); x++){
                            gc.setFill(Player2D.Colour(eturnStates(esrult.get(x))));
                            gc.fillRect(x*size,0,size,size);
                            gc.strokeRect(x*size,0,size,size);
                        }
                    }
                }
            }

        };

        root.setOnMousePressed(mouseHandler);
        root.setOnMouseReleased(mouseHandler);
        root.setOnMouseDragged(mouseHandler);


        StackPane down = new StackPane();
        Button go = new Button("Go!");
        go.setOnAction(event -> {
            window.close();
        });
        down.getChildren().addAll(go);
        down.setPadding(new Insets(10,20,10,20));

        BorderPane layout = new BorderPane();
        layout.setCenter(root);
        layout.setBottom(down);

        Scene scene = new Scene(layout);
        window.setScene(scene);

        window.showAndWait();

        return esrult;
    }

    public Pair<HashMap<Integer, CellState>,ArrayList<LangtonCell>> displayofants(int size, String title) {
        ChangeListener<String> tochoicebo = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.equals("EAST")) changeway(1);
                else if(newValue.equals("SOUTH")) changeway(2);
                else if(newValue.equals("WEST")) changeway(3);
                else if(newValue.equals("NORTH")) changeway(4);
            }
        };
        HashMap<Integer, CellState> esrult = new HashMap<>();
        for (int i = 0; i < 600/size; ++i) {
            for (int j = 0; j < 600/size; j++) {
                esrult.put(1000*j+i, BinaryState.DEAD);
            }
        }
        ToggleButton ifants = new ToggleButton("Ants");
        ChoiceBox<String> dire = new ChoiceBox<>();
        dire.getItems().addAll("EAST", "WEST", "NORTH", "SOUTH");
        dire.setValue("EAST");

        dire.getSelectionModel().selectedItemProperty().addListener(tochoicebo);

        ArrayList<LangtonCell> ants = new ArrayList<>();
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        Group root = new Group();
        Canvas canvas = new Canvas(600, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(2);

        gc.setFill(Player2D.Colour(esrult.get(0)));
        for (Map.Entry<Integer, CellState> entry : esrult.entrySet()) {
            gc.fillRect((entry.getKey()%1000)* size, (entry.getKey()/1000) * size, size, size);
            gc.strokeRect((entry.getKey()%1000) * size, (entry.getKey()/1000) * size, size, size);
        }

        EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
            int help_x = 0, help_y = 0, help_2_x = 0, help_2_y = 0, x, y;

            @Override
            public void handle(MouseEvent mouseEvent) {

                if(mouseEvent.getX() >= 0 && mouseEvent.getX() < 600 && mouseEvent.getY() >=0 && mouseEvent.getY() < 600) {
                    if(!ifants.isSelected()) {
                        if (mouseEvent.getEventType().getName().equals("MOUSE_PRESSED")) {
                            help_x = (int) (mouseEvent.getX() / size);
                            help_y = (int) (mouseEvent.getY() / size);
                            gc.setFill(Player2D.Colour(eturnStates(esrult.get(help_x + help_y * 1000))));
                            gc.fillRect(help_x * size, help_y * size, size, size);
                            gc.strokeRect(help_x * size, help_y * size, size, size);
                        } else if (mouseEvent.getEventType().getName().equals("MOUSE_RELEASED")) {
                            help_2_x = (int) (mouseEvent.getX() / size);
                            help_2_y = (int) (mouseEvent.getY() / size);
                            for (x = Math.min(help_x, help_2_x); x <= Math.max(help_x, help_2_x); x++) {
                                for (y = Math.min(help_y, help_2_y); y <= Math.max(help_y, help_2_y); y++) {
                                    esrult.put(y * 1000 + x, eturnStates(esrult.get(y * 1000 + x)));
                                }
                            }
                        } else if (mouseEvent.getEventType().getName().equals("MOUSE_DRAGGED")) {
                            for (Map.Entry<Integer, CellState> entry : esrult.entrySet()) {
                                gc.setFill(Player2D.Colour(entry.getValue()));
                                gc.fillRect((entry.getKey() % 1000) * size, (entry.getKey() / 1000) * size, size, size);
                                gc.strokeRect((entry.getKey() % 1000) * size, (entry.getKey() / 1000) * size, size, size);
                            }
                            help_2_x = (int) (mouseEvent.getX() / size);
                            help_2_y = (int) (mouseEvent.getY() / size);
                            for (x = Math.min(help_x, help_2_x); x <= Math.max(help_x, help_2_x); x++) {
                                for (y = Math.min(help_y, help_2_y); y <= Math.max(help_y, help_2_y); y++) {
                                    gc.setFill(Player2D.Colour(eturnStates(esrult.get(y * 1000 + x))));
                                    gc.fillRect(x * size, y * size, size, size);
                                    gc.strokeRect(x * size, y * size, size, size);
                                }
                            }
                        }
                    } else {
                        if(mouseEvent.getEventType().getName().equals("MOUSE_CLICKED")){
                            LangtonCell temp = new LangtonCell(way,(int)(mouseEvent.getX())/size+(int)(mouseEvent.getY())/size*1000);
                            if(!ants.contains(temp))
                            ants.add(temp);
                        }
                    }
                    for(LangtonCell lang : ants){
                        Player2D.LangtonDraw(lang,gc,size);
                    }
                }
            }

        };

        root.setOnMousePressed(mouseHandler);
        root.setOnMouseReleased(mouseHandler);
        root.setOnMouseDragged(mouseHandler);
        root.setOnMouseClicked(mouseHandler);


        VBox right = new VBox();
        Button go = new Button("Go!");
        go.setOnAction(event -> {
            window.close();
        });

        right.getChildren().addAll(go, ifants,dire);
        right.setPadding(new Insets(10,20,10,20));

        BorderPane layout = new BorderPane();
        layout.setCenter(root);
        layout.setRight(right);

        Scene scene = new Scene(layout);
        window.setScene(scene);

        window.showAndWait();

        return new Pair<>(esrult,ants);
    }

    private CellState eturnStates(CellState c) {
        if(c == BinaryState.ALIVE) return BinaryState.DEAD;
        else if (c == BinaryState.DEAD) return BinaryState.ALIVE;
        else if(c == QuadState.DEAD) return QuadState.RED;
        else if(c == QuadState.RED) return QuadState.YELLOW;
        else if(c == QuadState.YELLOW) return QuadState.GREEN;
        else if(c == QuadState.GREEN) return QuadState.BLUE;
        else if(c == QuadState.BLUE) return QuadState.DEAD;
        else if(c == WireElectronState.VOID) return WireElectronState.WIRE;
        else if(c == WireElectronState.WIRE) return WireElectronState.ELECTRON_HEAD;
        else if(c == WireElectronState.ELECTRON_HEAD) return WireElectronState.ELECTRON_TAIL;
        else return WireElectronState.VOID;
    }

    private AntState way = AntState.EAST;
    private void changeway(int i){              //changes the state of ant (direction) by number given to it as an argument
        switch (i){
            case 1:
                way = AntState.EAST;
                break;
            case 2:
                way = AntState.SOUTH;
                break;
            case 3:
                way = AntState.WEST;
                break;
            case 4:
                way = AntState.NORTH;
                break;
        }
    }
}
