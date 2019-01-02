package Model.GUI_extras;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static javafx.scene.input.KeyCode.V;

public class AddFriendBox {

    public static String display(String title, String message) {
        Stage window = new Stage();

        //blocks other windows until action taken along with showAndWait
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        TextField friendNameInput = new TextField();
        Button addButton = new Button("Add Friend");
        addButton.setOnAction(e -> window.close());

        Button closeButton = new Button("Back");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, friendNameInput, addButton, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return friendNameInput.getText();

    }

}
