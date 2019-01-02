package ui;

import javafx.application.Application;
import javafx.stage.Stage;
import ui.Menu;

public class GUI extends Application {

    Stage window;

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        Menu.display(window);
        window.show();


    }
}

