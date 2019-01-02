package Model.GUI_extras;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ui.Menu;

import java.awt.*;

public class FriendListBox {
    static ListView<String> listView;


    public static void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Friends who have visited "+ ui.Menu.currUser.currLocation);


        listView = new ListView<>();
        listView.getItems().addAll(Menu.friends);
        //listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(listView);

        Scene scene = new Scene(layout, 500, 250);
        window.setScene(scene);
        window.showAndWait();
    }

}
