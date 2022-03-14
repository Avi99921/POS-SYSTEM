package sample;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class AskReport {
    public static String requestedReport = "";
    public static String askDailyOrMonthly(){
        Stage stage = new Stage();
        GridPane root = new GridPane();
        Button dayButton = new Button("Daily");
        dayButton.setFont(Font.font("Arial", FontWeight.BOLD,20));
        Button monthButton = new Button("Monthly");
        monthButton.setFont(Font.font("Arial", FontWeight.BOLD,20));



        dayButton.setOnMouseClicked(event->{
            requestedReport = "Day";
            stage.close();
        });
        monthButton.setOnMouseClicked(event->{
            requestedReport = "Month";
            stage.close();
        });
        root.add(dayButton,0,0);
        root.add(monthButton,0,1);
        root.setVgap(10);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root,360,360);
        scene.getStylesheets().add(AskReport.class.getResource("Main.css").toExternalForm());
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                requestedReport = "null";
            }
        });
        stage.showAndWait();
        return requestedReport;
    }


}
