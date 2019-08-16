package MainLauncher;

import javafx.application.Application;
import javafx.stage.Stage;

//due to representation of coordinates the neighbourhood ray cannot be longer than 100 and the size of the grid cannot be bigger than 799

public class Mainer extends Application {
    public static void main(String argv[]){
        launch(argv);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Cellular Automata");

        setScene(primaryStage, 0, 800, 550);   //function responsible for creating the scene
        primaryStage.show();
    }

    public static void setScene(Stage s, int whichOne, double width, double height){
        switch (whichOne){
            case 0:
                s.setScene(SceneFiller.mainMenu(width, height, s));
                break;
            case 1:
                s.setScene(SceneFiller.startMenu(width, height, s));
                break;
            case 2:
                s.setScene(SceneFiller.dimOneMenu(width, height, s));
                break;
            case 3:
                s.setScene(SceneFiller.dimtwoMenu(width,height,s));
                break;
            case 4:
                s.setScene(SceneFiller.GameOfLife(width, height, s));
                break;
            case 5:
                s.setScene(SceneFiller.LangtonAnt(width, height, s));
                break;
            case 6:
                s.setScene(SceneFiller.Wireworld(width, height, s));
                break;
            case 7:
                s.setScene(SceneFiller.QuadLife(width, height, s));
                break;
            case 8:
                s.setScene(SceneFiller.Credits(width, height, s));
                break;
            default:
                s.setScene(SceneFiller.mainMenu(width, height, s));
        }
    }
}
