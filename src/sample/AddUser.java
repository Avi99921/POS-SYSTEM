package sample;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.sql.*;

public class AddUser {
    public static final String url = "jdbc:mysql://localhost/BILLINGDB";
    //public static final String user = "root";
    //public static final String password = "Avi990921pj";
    public static String gotAccess;
    public static String addUserWindow(String user,String password){
        Stage stage = new Stage();
        stage.setTitle("Add User");
        GridPane addUserLayout = new GridPane();
        Scene scene = new Scene(addUserLayout,360,240);
        scene.getStylesheets().add(AddUser.class.getResource("Main.css").toExternalForm());

        Label userName = new Label("User Name");
        Label userPassword = new Label("User Password");
        Label adminPassword = new Label("Admin Password");

        TextField userNameEntry = new TextField();
        TextField userPasswordEntry = new TextField();
        PasswordField adminPasswordEntry = new PasswordField();

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e->{
            String adminDbPassword;
            Connection databaseConnection = null;
            Statement databaseStatement = null;
            String uName = userNameEntry.getText();
            String uPassword = userPasswordEntry.getText();

            try {
                databaseConnection = DriverManager.getConnection(url,user,password);
                databaseStatement = databaseConnection.createStatement();
                String sqlStatement = "SELECT Password FROM User WHERE Username='admin'";
                ResultSet resultSet = databaseStatement.executeQuery(sqlStatement);
                while (resultSet.next()){
                    adminDbPassword = resultSet.getString("Password");
                    if(adminPasswordEntry.getText().equals(adminDbPassword)){
                        gotAccess = "true";
                        stage.close();
                    }else{
                        gotAccess = "false";
                        stage.close();
                    }
                }
                if (gotAccess.equals("true")){
                    sqlStatement = "INSERT INTO User(Username,Password) VALUES('"+uName+"','"+uPassword+"')";
                    databaseStatement.executeUpdate(sqlStatement);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });


        addUserLayout.add(userName,0,0,1,1);
        addUserLayout.add(userPassword,0,1,1,1);
        addUserLayout.add(adminPassword,0,2,1,1);
        addUserLayout.add(userNameEntry,1,0,1,1);
        addUserLayout.add(userPasswordEntry,1,1,1,1);
        addUserLayout.add(adminPasswordEntry,1,2,1,1);
        addUserLayout.add(submitButton,1,3,1,1);

        addUserLayout.setAlignment(Pos.CENTER);
        addUserLayout.setHgap(7);

        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                gotAccess = "null";
                stage.close();
            }
        });
        stage.showAndWait();
        return gotAccess;


    }

}
