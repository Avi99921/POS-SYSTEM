package sample;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
    public static void displayAlertBox(String title,String message){
        Stage stage = new Stage();
        StackPane layout = new StackPane();
        stage.setTitle(title);
        Label messageLabel = new Label(message);
        layout.getChildren().add(messageLabel);
        Scene errorScene = new Scene(layout,240,120);
        errorScene.getStylesheets().add(AlertBox.class.getResource("Main.css").toExternalForm());
        stage.setScene(errorScene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
