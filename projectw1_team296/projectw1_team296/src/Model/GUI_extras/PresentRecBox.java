package Model.GUI_extras;

import Model.Recommendation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ui.Menu;

import java.util.ArrayList;

public class PresentRecBox {
    private static TableView<Recommendation> table;

    public static void display() {
        Stage window = new Stage();

        //Type column
        TableColumn<Recommendation, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setMinWidth(200);
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        //Comment column
        TableColumn<Recommendation, Double> commentColumn = new TableColumn<>("Comment");
        commentColumn.setMinWidth(100);
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));

        //Rating column
        TableColumn<Recommendation, String> ratingColumn = new TableColumn<>("Rating");
        ratingColumn.setMinWidth(100);
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));

        //City column
        TableColumn<Recommendation, String> cityColumn = new TableColumn<>("City");
        cityColumn.setMinWidth(100);
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));

        table = new TableView<>();
        table.setItems(getRecs(Menu.recs));
        table.getColumns().addAll(typeColumn, commentColumn, ratingColumn, cityColumn);

        Button closeButton = new Button("Exit");
        closeButton.setOnAction(e -> window.close());

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(table, closeButton);

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Recommendations");
        window.setMinWidth(250);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.showAndWait();
    }

    private static ObservableList<Recommendation> getRecs(ArrayList<Recommendation> recommendations) {
        ObservableList<Recommendation> recs = FXCollections.observableArrayList();
        recs.addAll(recommendations);
        return recs;
    }


}
