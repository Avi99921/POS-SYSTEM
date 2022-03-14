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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.awt.*;
import java.sql.*;

public class ChangePassword {
    public static String operationState;
    public static final String url = "jdbc:mysql://localhost/BILLINGDB";
    //public static final String user = "root";
    //public static final String password = "Avi990921pj";
    public static String changeUserWindow(String user,String password){
        Stage stage = new Stage();
        stage.setTitle("Change Password");
        GridPane changePasswordLayout = new GridPane();
        Scene scene = new Scene(changePasswordLayout,360,240);
        scene.getStylesheets().add(ChangePassword.class.getResource("Main.css").toExternalForm());

        Label currentPassword = new Label("Current Password");
        Label newPassword = new Label("New Password");
        Label confirmNewPassword = new Label("Confirm New Password");

        PasswordField currentPasswordEntry = new PasswordField();
        PasswordField newPasswordEntry = new PasswordField();
        PasswordField confirmNewPasswordEntry = new PasswordField();

        Button submitButton = new Button("Submit");

        currentPassword.setFocusTraversable(false);
        newPasswordEntry.setFocusTraversable(false);
        confirmNewPasswordEntry.setFocusTraversable(false);

        changePasswordLayout.add(currentPassword,0,0,1,1);
        changePasswordLayout.add(newPassword,0,1,1,1);
        changePasswordLayout.add(confirmNewPassword,0,2,1,1);
        changePasswordLayout.add(currentPasswordEntry,1,0,1,1);
        changePasswordLayout.add(newPasswordEntry,1,1,1,1);
        changePasswordLayout.add(confirmNewPasswordEntry,1,2,1,1);
        changePasswordLayout.add(submitButton,1,3,1,1);

        changePasswordLayout.setAlignment(Pos.CENTER);
        changePasswordLayout.setHgap(7);

        submitButton.setOnAction(e->{
            String currentAdminPassword = currentPasswordEntry.getText();
            String newAdminPassword = newPasswordEntry.getText();
            String confirmNewAdminPassword = confirmNewPasswordEntry.getText();
            Connection databaseConnection = null;
            Statement databaseStatement = null;

            try {
                if (newAdminPassword.equals(confirmNewAdminPassword)){
                    databaseConnection = DriverManager.getConnection(url,user,password);
                    databaseStatement = databaseConnection.createStatement();
                    String sql = "SELECT Password FROM User WHERE Username='admin'";
                    ResultSet resultSet = databaseStatement.executeQuery(sql);
                    while (resultSet.next()){
                        String dbAdminPassword = resultSet.getString("Password");
                        if(currentAdminPassword.equals(dbAdminPassword)){
                            sql = "UPDATE User SET Password='"+newAdminPassword+"' WHERE Username='admin'";
                            databaseStatement.executeUpdate(sql);
                            operationState = "success";
                        }else {
                            operationState = "wrongAdminPassword";
                        }
                        break;

                    }

                }else {
                    operationState = "newPasswordNotMatching";
                }
                stage.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
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