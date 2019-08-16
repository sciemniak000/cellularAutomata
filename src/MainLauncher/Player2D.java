package MainLauncher;

import Automata.OneDimension.Automaton1Dim;
import Automata.TwoDimentions.Automaton2Dim;
import Automata.TwoDimentions.LA.LangtonAnt;
import Cell.States.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player2D {
    private static long time;
    private static boolean available;

    public static void display(Automaton2Dim automaton, String title, int size){        //launches the 2 dimensional automaton
        time = 1000;
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        Button presto = new Button("Szybciej!");
        presto.setOnAction(e -> {
            if(time > 100){
                time -= 100;
            } else {
                time = 50;
            }
        });

        Button largo = new Button("Wolniej!");
        largo.setOnAction(e -> {
            if(time == 50){
                time = 100;
            } else if(time < 2000){
                time += 100;
            }
        });

        VBox left = new VBox();
        left.setPadding(new Insets(20, 20, 20, 20));
        left.setSpacing(10);

        left.getChildren().addAll(presto, largo);
        left.setAlignment(Pos.CENTER);


        Group root = new Group();
        Canvas canvas = new Canvas(600, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(2);

        for(Map.Entry<Integer, CellState> coords : automaton.getCells().entrySet()){
            gc.setFill(Colour(coords.getValue()));
            gc.fillRect((coords.getKey()%1000)*size, (coords.getKey()/1000)*size, size, size);
            gc.strokeRect((coords.getKey()%1000)*size, (coords.getKey()/1000)*size, size, size);
        }
        if(automaton instanceof LangtonAnt){
            ArrayList<LangtonCell> anties = new ArrayList<>(((LangtonAnt) automaton).getAnts());
            for(LangtonCell ant : anties){
                LangtonDraw(ant, gc, size);
            }
        }

        BorderPane layout = new BorderPane();
        layout.setRight(left);
        layout.setCenter(root);

        window.setOnCloseRequest(event -> {
            available = false;
            window.close();
        });
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();

        try{
            Thread.sleep(time);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        available = true;

        Task herewego = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while(available){
                    Platform.runLater(() -> {
                        HashMap<Integer, CellState> todo = automaton.nextState();
                        for(Map.Entry<Integer, CellState> entry : todo.entrySet()){
                            gc.setFill(Colour(entry.getValue()));
                            gc.fillRect((entry.getKey()%1000)*size, (entry.getKey()/1000)*size, size, size);
                            gc.strokeRect((entry.getKey()%1000)*size, (entry.getKey()/1000)*size, size, size);
                        }
                        if(automaton instanceof LangtonAnt){
                            ArrayList<LangtonCell> anties = new ArrayList<>(((LangtonAnt) automaton).getAnts());
                            for(LangtonCell ant : anties){
                                LangtonDraw(ant, gc, size);
                            }
                        }
                    });
                    Thread.sleep(time);
                }
                return null;
            }
        };
        Thread thr = new Thread(herewego);
        thr.setDaemon(true);
        thr.start();
    }


    public static void display1D(Automaton1Dim automaton, String title, int size){

        HashMap<Integer, CellState> downcells = new HashMap<>(), helpful = new HashMap<>();
        time = 1000;
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        Button presto = new Button("Szybciej!");
        presto.setOnAction(e -> {
            if(time > 100){
                time -= 100;
            } else {
                time = 50;
            }
        });

        Button largo = new Button("Wolniej!");
        largo.setOnAction(e -> {
            if(time == 50){
                time = 100;
            } else if(time < 2000){
                time += 100;
            }
        });

        VBox left = new VBox();
        left.setPadding(new Insets(20, 20, 20, 20));
        left.setSpacing(10);

        left.getChildren().addAll(presto, largo);
        left.setAlignment(Pos.CENTER);


        Group root = new Group();
        Canvas canvas = new Canvas(600, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(2);


        for(Map.Entry<Integer, CellState> coords : automaton.getCells().entrySet()){
            gc.setFill(Colour(coords.getValue()));
            gc.fillRect((coords.getKey()%1000)*size, 0, size, size);
            gc.strokeRect((coords.getKey()%1000)*size, 0, size, size);
        }

        gc.setFill(Color.WHITE);
        for(int i = 2; i*size < 600; i++){
            for(int j = 0; j < automaton.getCells().size(); j++) {
                gc.fillRect(j*size, i*size, size, size);
                gc.strokeRect(j*size, i*size, size, size);
                downcells.put(i*1000+j, BinaryState.DEAD);
            }
        }


        BorderPane layout = new BorderPane();
        layout.setRight(left);
        layout.setCenter(root);

        window.setOnCloseRequest(event -> {
            available = false;
            window.close();
        });
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();

        try{
            Thread.sleep(time);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        available = true;

        Task herewego = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while(available){
                    Platform.runLater(() -> {
                        for(Map.Entry<Integer, CellState> entry : downcells.entrySet()){
                            if(entry.getKey()/1000 == 2){
                                helpful.put(entry.getKey(),automaton.getCells().get(entry.getKey()%1000));
                            } else {
                                helpful.put(entry.getKey(),downcells.get(entry.getKey()-1000));
                            }
                        }

                        HashMap<Integer, CellState> todo = automaton.nextState();
                        for(Map.Entry<Integer, CellState> entry : todo.entrySet()){
                            //if(entry.getValue() == BinaryState.ALIVE) System.out.println(entry.getKey() + "\n");

                            gc.setFill(Colour(entry.getValue()));
                            gc.fillRect((entry.getKey()%1000)*size, 0, size, size);
                            gc.strokeRect((entry.getKey()%1000)*size, 0, size, size);
                        }

                        for(Map.Entry<Integer,CellState> entry : helpful.entrySet()){
                            downcells.put(entry.getKey(),entry.getValue());
                            gc.setFill(Colour(entry.getValue()));
                            gc.fillRect((entry.getKey()%1000)*size, (entry.getKey()/1000)*size, size, size);
                            gc.strokeRect((entry.getKey()%1000)*size, (entry.getKey()/1000)*size, size, size);
                        }
                    });
                    Thread.sleep(time);
                }
                return null;
            }
        };
        Thread thr = new Thread(herewego);
        thr.setDaemon(true);
        thr.start();
    }


    public static Paint Colour(CellState state){
        if(state == BinaryState.ALIVE || state == WireElectronState.VOID){
            return Color.BLACK;
        } else if(state == BinaryState.DEAD || state == QuadState.DEAD){
            return Color.WHITE;
        } else if(state == QuadState.BLUE || state == WireElectronState.ELECTRON_HEAD){
            return Color.AQUA;
        } else if(state == QuadState.GREEN){
            return Color.LAWNGREEN;
        } else if(state == QuadState.RED){
            return Color.RED;
        } else if(state == QuadState.YELLOW || state == WireElectronState.WIRE){
            return Color.YELLOW;
        } else { //WireElectronState Electron Tail
            return Color.ORANGERED;
        }
    }


    public static void LangtonDraw(LangtonCell ang, GraphicsContext gc, int size){
        gc.setFill(Color.RED);
        switch (ang.antState){
            case EAST:
                gc.fillRect((ang.whereItIs%1000)*size + 0.8*size, (ang.whereItIs/1000)*size, 0.2*size, size);
                break;
            case WEST:
                gc.fillRect((ang.whereItIs%1000)*size, (ang.whereItIs/1000)*size, 0.2*size, size);
                break;
            case NORTH:
                gc.fillRect((ang.whereItIs%1000)*size, (ang.whereItIs/1000)*size, size, 0.2*size);
                break;
            case SOUTH:
                gc.fillRect((ang.whereItIs%1000)*size, (ang.whereItIs/1000)*size + 0.8*size, size, 0.2*size);
                break;
        }
    }
}
