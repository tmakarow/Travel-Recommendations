package Model.GUI_extras;

import Model.Recommendation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddRecBox {
    private static Recommendation rec;
    public static Recommendation display(String title) {
        Stage window = new Stage();

        //blocks other windows until action taken along with showAndWait
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label1 = new Label("Type");
        Label label2 = new Label("Comment");
        Label label3 = new Label("Rating (1-5)");
        Label label4 = new Label("City");
        Label label5 = new Label("Your name");

        TextField typeInputT = new TextField();
        String typeInput = typeInputT.getText();
        TextField commentInputT = new TextField();
        String commentInput = commentInputT.toString();
        TextField ratingInputT = new TextField();
        String ratingInput = ratingInputT.getText();
        TextField cityInputT = new TextField();
        String cityInput = cityInputT.getText();
        TextField nameInputT = new TextField();
        String nameInput = nameInputT.getText();
        Button addButton = new Button("Add Recommendation");

        HBox hbox1 = new HBox(label1, typeInputT);
        HBox hbox2 = new HBox(label2, commentInputT);
        HBox hbox3 = new HBox(label3, ratingInputT);
        HBox hbox4 = new HBox(label4, cityInputT);
        HBox hbox5 = new HBox(label5, nameInputT);
        hbox1.setAlignment(Pos.CENTER);
        addButton.setOnAction(e -> {
            rec = new Recommendation(nameInput, typeInput, commentInput, ratingInput, cityInput);
            window.close();
        });

        Button closeButton = new Button("Back");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(hbox1, hbox2, hbox3, hbox4, hbox5, addButton, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return rec;

    }

}
