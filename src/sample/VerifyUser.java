package sample;
import javafx.application.Platform;
import javafx.event.EventHandler;
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
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;

public class VerifyUser {
    public static final String url = "jdbc:mysql://localhost/BILLINGDB";
    //public static final String user = "root";
    //public static final String password = "Avi990921pj";
    public static String userState;
    public static String[] verifyUserBillingWindow(String userName,String userPassword,String user,String password){
        String[] userArray = new String[0];
        Connection databaseConnection = null;
        Statement databaseStatement = null;
        String uName = userName;
        String uPassword = userPassword;

        try {
            String dbUserName;
            String dbPassword;
            String sqlStatementGetUser = "SELECT Username FROM User";
            databaseConnection = DriverManager.getConnection(url,user,password);
            databaseStatement = databaseConnection.createStatement();
            ResultSet resultSet = databaseStatement.executeQuery(sqlStatementGetUser);
            while (resultSet.next()){
                dbUserName = resultSet.getString("Username");
                if (dbUserName.equals(uName)){
                    String sqlStatementGetUserPassword = "SELECT Password FROM User WHERE Username = "+ "'"+dbUserName+"'";
                    resultSet = databaseStatement.executeQuery(sqlStatementGetUserPassword);
                    while (resultSet.next()){
                        dbPassword = resultSet.getString("Password");
                        if (dbPassword.equals(uPassword)){
                            userState = "true";

                        }else {
                            userState = "false";
                        }
                        //stage.close();
                    }
                }else{
                    userState = "false";
                    //stage.close();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        /*Stage stage = new Stage();
        stage.setTitle("Login");
        GridPane verifyUserLayout = new GridPane();
        Scene scene = new Scene(verifyUserLayout,360,240);
        scene.getStylesheets().add(VerifyUser.class.getResource("Main.css").toExternalForm());

        Label userName = new Label("User Name");
        Label userPassword = new Label("Password");

        TextField userNameEntry = new TextField();
        PasswordField userPasswordEntry = new PasswordField();

        Button loginButton = new Button("Submit");
        loginButton.setOnAction(e-> {
            Connection databaseConnection = null;
            Statement databaseStatement = null;
            String uName = userNameEntry.getText();
            String uPassword = userPasswordEntry.getText();

            try {
                String dbUserName;
                String dbPassword;
                String sqlStatementGetUser = "SELECT Username FROM User";
                databaseConnection = DriverManager.getConnection(url,user,password);
                databaseStatement = databaseConnection.createStatement();
                ResultSet resultSet = databaseStatement.executeQuery(sqlStatementGetUser);
                while (resultSet.next()){
                    dbUserName = resultSet.getString("Username");
                    if (dbUserName.equals(uName)){
                        String sqlStatementGetUserPassword = "SELECT Password FROM User WHERE Username = "+ "'"+dbUserName+"'";
                        resultSet = databaseStatement.executeQuery(sqlStatementGetUserPassword);
                        while (resultSet.next()){
                            dbPassword = resultSet.getString("Password");
                            if (dbPassword.equals(uPassword)){
                                userState = "true";

                            }else {
                                userState = "false";
                            }
                            stage.close();
                        }
                    }else{
                        userState = "false";
                        stage.close();
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        verifyUserLayout.add(userName,0,0,1,1);
        verifyUserLayout.add(userPassword,0,2,1,1);
        verifyUserLayout.add(userNameEntry,1,0,1,1);
        verifyUserLayout.add(userPasswordEntry,1,2,1,1);
        verifyUserLayout.add(loginButton,1,3,1,1);

        verifyUserLayout.setAlignment(Pos.CENTER);
        verifyUserLayout.setHgap(5);
        verifyUserLayout.setVgap(5);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                userState = "null";
            }
        });
        stage.showAndWait();*/
        userArray = new String[]{userState,userName};
        return userArray;

    }
    public static String verifyUserUpdateOrDeleteDatabase(String buttonName){
        Stage stage = new Stage();
        stage.setTitle("Login");
        GridPane updateOrDeleteDatabaseLoginLayout = new GridPane();
        Scene scene = new Scene(updateOrDeleteDatabaseLoginLayout,360,240);
        scene.getStylesheets().add(VerifyUser.class.getResource("Main.css").toExternalForm());

        Label adminPassword = new Label("Admin Password");
        PasswordField adminPasswordEntry = new PasswordField();

        Button submitButton = new Button(buttonName);

        updateOrDeleteDatabaseLoginLayout.add(adminPassword,0,0,1,1);
        updateOrDeleteDatabaseLoginLayout.add(adminPasswordEntry,1,0,1,1);
        updateOrDeleteDatabaseLoginLayout.add(submitButton,1,1,1,1);

        updateOrDeleteDatabaseLoginLayout.setHgap(5);
        updateOrDeleteDatabaseLoginLayout.setVgap(5);

        updateOrDeleteDatabaseLoginLayout.setAlignment(Pos.CENTER);

        submitButton.setOnAction(e->{
            String adminPass = adminPasswordEntry.getText();
            if (adminPass.equals("admin")){
                userState = "true";
            }else {
                adminPasswordEntry.clear();
                userState = "false";
            }
            stage.close();


        });

        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                userState = "null";
                //Platform.exit();
                stage.close();
            }
        });
        stage.showAndWait();
        return userState;


        //stage.close();
    }


}
