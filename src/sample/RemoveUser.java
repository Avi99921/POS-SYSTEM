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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RemoveUser {
    public static String userState;
    public static String adminState;
    public static String operationState;
    public static final String url = "jdbc:mysql://localhost/BILLINGDB";
    //public static final String user = "root";
    //public static final String password = "Avi990921pj";
    public static String removeUserWindow(String user,String password){
        Stage stage = new Stage();
        stage.setTitle("Remove User");
        GridPane removeUserLayout = new GridPane();
        Scene scene = new Scene(removeUserLayout,360,240);
        scene.getStylesheets().add(RemoveUser.class.getResource("Main.css").toExternalForm());

        Label userName = new Label("User Name");
        Label adminPassword = new Label("Admin Password");

        TextField userNameEntry = new TextField();
        PasswordField adminPasswordEntry = new PasswordField();

        Button submitButton = new Button("Submit");


        removeUserLayout.add(userName,0,0,1,1);
        removeUserLayout.add(adminPassword,0,2,1,1);
        removeUserLayout.add(userNameEntry,1,0,1,1);
        removeUserLayout.add(adminPasswordEntry,1,2,1,1);
        removeUserLayout.add(submitButton,1,3,1,1);

        removeUserLayout.setAlignment(Pos.CENTER);
        removeUserLayout.setHgap(7);

        submitButton.setOnAction(e->{
            String username = userNameEntry.getText();
            String passwordAdmin = adminPasswordEntry.getText();
            Connection databaseConnection = null;
            Statement databaseStatement = null;

            try {
                databaseConnection = DriverManager.getConnection(url,user,password);
                databaseStatement = databaseConnection.createStatement();
                String sql = "SELECT Username FROM User";
                ResultSet resultSet = databaseStatement.executeQuery(sql);
                if (username.equals("admin")){
                    userState = "false";
                    stage.close();
                }
                while (resultSet.next()){
                    String dbUsername = resultSet.getString("Username");
                    if(dbUsername.equals(username)){
                        userState = "true";
                        break;
                    }else{
                        userState = "false";
                    }
                }
                if (userState.equals("true")){
                    sql = "SELECT Password FROM user WHERE Username='admin'";
                    resultSet = databaseStatement.executeQuery(sql);
                    while (resultSet.next()){
                        if (passwordAdmin.equals(resultSet.getString("Password"))){
                            adminState = "true";
                            break;
                        }else{
                            adminState = "false";
                        }
                    }
                }else if(userState.equals("false")){
                    operationState = "failed";
                    stage.close();
                }
                if (adminState.equals("true")){
                    sql = "DELETE FROM User WHERE Username='"+username+"'";
                    databaseStatement.executeUpdate(sql);
                    operationState = "success";
                    stage.close();
                }else if(adminState.equals("false")){
                    operationState = "failed";
                    stage.close();
                }


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                operationState = "null";
                stage.close();
            }
        });
        stage.showAndWait();
        return operationState;
    }
}