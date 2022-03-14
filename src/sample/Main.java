package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.controlsfx.tools.Borders;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application{
    String userState;
    boolean dbState;
    String gotAccess;
    String operationState;

    public static void main(String[]args){
        launch(args);
    }

    public ArrayList<String> extractDbDetails(ArrayList<String> dbDetails) throws FileNotFoundException {
        File obj = new File("myfile.txt");
        Scanner fileReader = new Scanner(obj);
        while (fileReader.hasNextLine())
        {
            String data = fileReader.nextLine();
            dbDetails.add(data);
        }
        return dbDetails;
    }
    @Override
    public void start(Stage stage) throws Exception {
        String url = "jdbc:mysql://localhost/BILLINGDB";
        String user;
        String password;
        ArrayList<String> dbDetails = new ArrayList<String>();
        extractDbDetails(dbDetails);
        user=dbDetails.get(0);
        password=dbDetails.get(1);
        System.out.println(dbDetails.get(0)+dbDetails.get(1));

        TemporaryDatabaseOperations temporaryDatabaseOperationsObj = new TemporaryDatabaseOperations();
        Scene scene,scene2;

        Image moreIcon = new Image(getClass().getResourceAsStream("graphics/viewmore.png"));
        Image backIcon = new Image(getClass().getResourceAsStream("graphics/back.png"));

        ImageView moreIconView = new ImageView(moreIcon);
        ImageView backIconView = new ImageView(backIcon);

        moreIconView.setFitHeight(32);
        moreIconView.setFitWidth(32);
        backIconView.setFitHeight(32);
        backIconView.setFitWidth(32);

//============================================ SCENE1==========================================================
        GridPane loginLayout = new GridPane();
        GridPane loginLayout2 = new GridPane();
        GridPane loginButtonLayout = new GridPane();

        Button moreButton = new Button();
        moreButton.setId("BorderlessButton");
        moreButton.setGraphic(moreIconView);
        Label userNameLabel = new Label("User name");
        Label passwordLabel = new Label("Password");
        TextField userNameInput = new TextField();
        PasswordField passwordInput = new PasswordField();
        Button loginButton = new Button("Login");

        userNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        passwordLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        loginButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));




        loginLayout2.add(moreButton,0,0,1,1);
        loginButtonLayout.add(loginButton,0,0,1,1);

        loginLayout2.setAlignment(Pos.TOP_RIGHT);
        loginButtonLayout.setAlignment(Pos.CENTER);

        loginLayout.add(loginLayout2,0,1,1,1);
        loginLayout.add(userNameLabel,0,2,1,1);
        loginLayout.add(userNameInput,0,3,1,1);
        loginLayout.add(passwordLabel,0,4,1,1);
        loginLayout.add(passwordInput,0,5,1,1);
        loginLayout.add(loginButtonLayout,0,6,1,1);

        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setVgap(5);
//===================================== SCENE2 =====================================================
        GridPane databaseOperationLayout = new GridPane();
        GridPane backButtonLayout = new GridPane();
        GridPane userOperationLayout = new GridPane();
        GridPane itemOperationLayout = new GridPane();

        Button backButton = new Button();
        backButton.setId("BorderlessButton");
        backButton.setGraphic(backIconView);

        Button createDbButton = new Button("Create Database");
        createDbButton.setId("databaseOperation");
        Button addItemsButton = new Button("ADD ITEMS");
        addItemsButton.setId("databaseOperation");
        Button deleteItemsButton = new Button("DELETE ITEMS");
        deleteItemsButton.setId("databaseOperation");
        Button updateItemsButton = new Button("UPDATE ITEMS");
        updateItemsButton.setId("databaseOperation");
        Button deleteDbButton = new Button("Delete Database");
        deleteDbButton.setId("databaseOperation");

        createDbButton.setPrefWidth(150);
        addItemsButton.setPrefWidth(150);
        deleteItemsButton.setPrefWidth(150);
        updateItemsButton.setPrefWidth(150);
        deleteDbButton.setPrefWidth(150);

        Button addUserButton = new Button("Add User");
        addUserButton.setPrefWidth(150);
        addUserButton.setId("databaseOperation");
        Button removeUserButton = new Button("Remove User");
        removeUserButton.setPrefWidth(150);
        removeUserButton.setId("databaseOperation");
        Button changePasswordButton = new Button("Change Password");
        changePasswordButton.setPrefWidth(150);
        changePasswordButton.setId("databaseOperation");
        Button configDatabaseButton = new Button("Config Database");
        configDatabaseButton.setPrefWidth(150);
        configDatabaseButton.setId("databaseOperation");

        backButtonLayout.add(backButton,0,0,1,1);
        backButtonLayout.setAlignment(Pos.TOP_LEFT);

        userOperationLayout.add(addUserButton,0,0);
        userOperationLayout.add(removeUserButton,0,1);
        userOperationLayout.add(changePasswordButton,0,2);
        userOperationLayout.add(configDatabaseButton,0,3);
        userOperationLayout.setVgap(5);

        itemOperationLayout.add(createDbButton,0,0);
        itemOperationLayout.add(addItemsButton,0,1);
        itemOperationLayout.add(updateItemsButton,0,2);
        itemOperationLayout.add(deleteItemsButton,0,3);
        itemOperationLayout.add(deleteDbButton,0,4);
        itemOperationLayout.setVgap(5);

        Node wrapUserOperationLayout = Borders.wrap(userOperationLayout)
                .lineBorder()
                .title("User operations")
                .color(Color.rgb(81,152,222))
                .build()
                .build();

        Node wrapItemOperationLayout = Borders.wrap(itemOperationLayout)
                .lineBorder()
                .title("ItemOperation")
                .color(Color.rgb(81,152,222))
                .build()
                .build();

        databaseOperationLayout.add(backButtonLayout,0,0,1,1);
        databaseOperationLayout.add(wrapUserOperationLayout,0,1,1,1);
        databaseOperationLayout.add(wrapItemOperationLayout,0,2,1,1);
        databaseOperationLayout.setAlignment(Pos.CENTER);
        databaseOperationLayout.setVgap(5);
        databaseOperationLayout.setHgap(5);

        scene = new Scene(loginLayout,280,240);
        scene2 = new Scene(databaseOperationLayout,280,480);


        loginButton.setOnAction(e-> {
            String userName = userNameInput.getText();
            String userPassword = passwordInput.getText();
            userNameInput.clear();
            passwordInput.clear();
            if (userName.isEmpty()){
                AlertBox.displayAlertBox("Error","Please enter required fields");
            }else if (userPassword.isEmpty()){
                AlertBox.displayAlertBox("Error","Please enter required fields");
            }else{
                try {
                    dbState = CheckDatabaseExists.checkDatabase(user,password);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                if (!dbState){
                    AlertBox.displayAlertBox("Error","Please create a Database");
                }else{
                    String[] userState;
                    userState = VerifyUser.verifyUserBillingWindow(userName,userPassword,user,password);
                    if (userState[0].equals("true")){
                        try {
                            if (temporaryDatabaseOperationsObj.checkTemporaryDatabaseAvailable(user,password).equals("true")){
                                Connection databaseConnection = DriverManager.getConnection(url,user,password);
                                Statement databaseStatement = databaseConnection.createStatement();
                                ResultSet resultSet = databaseStatement.executeQuery("SELECT * FROM TemporaryGoods");
                                while (resultSet.next()){
                                    temporaryDatabaseOperationsObj.updateGoodsDatabase(resultSet.getString("ItemNumber"),resultSet.getInt("Quantity"),user,password);
                                }
                                temporaryDatabaseOperationsObj.resetTemporaryDatabase(user,password);
                            }

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        System.out.println(userState);
                        BillingWindow.billingWindow(userState[1],user,password);
                    }else if (userState[0].equals("false")){
                        AlertBox.displayAlertBox("Error","Username of Password is wrong");
                    }
                }


                }
            });

        moreButton.setOnAction(e->{
            stage.setTitle("Update");
            stage.setScene(scene2);
        });
        backButton.setOnAction(e->{
            stage.setTitle("Login");
            stage.setScene(scene);
        });

        addUserButton.setOnAction( e -> {
            gotAccess = AddUser.addUserWindow(user,password);
            if (gotAccess.equals("true")){
                AlertBox.displayAlertBox("Finished","User added successfully");
            }else if(gotAccess.equals("false")){
                AlertBox.displayAlertBox("Error","ADMIN password is wrong");
            }
        });

        removeUserButton.setOnAction(e -> {
            operationState = RemoveUser.removeUserWindow(user,password);
            if (operationState.equals("success")){
                AlertBox.displayAlertBox("Success","User deleted successfully");
            }else if (operationState.equals("failed")){
                AlertBox.displayAlertBox("Error","Username or Admin password is wrong");
            }
        });
        changePasswordButton.setOnAction(e-> {
            operationState = ChangePassword.changeUserWindow(user,password);
            if (operationState.equals("success")){
                AlertBox.displayAlertBox("Success","Password changed successfully");
            }else if(operationState.equals("wrongAdminPassword")){
                AlertBox.displayAlertBox("Error","Wrong Admin password");
            }else if (operationState.equals("newPasswordNotMatching")){
                AlertBox.displayAlertBox("Error","New password not matching");
            }
        });
        configDatabaseButton.setOnAction(e->{
            userState=VerifyUser.verifyUserUpdateOrDeleteDatabase("Login");
            if (userState.equals("true")){
                ConfigDatabase.configDatabaseWindow();
            }else if(userState.equals("false")){
                AlertBox.displayAlertBox("Error","Wrong ADMIN Password");
            }

        });

        addItemsButton.setOnAction(e-> {
            try {
                dbState = CheckDatabaseExists.checkDatabase(user,password);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if (!dbState){
                AlertBox.displayAlertBox("Error","Please create a Database");
            }else {
                userState = VerifyUser.verifyUserUpdateOrDeleteDatabase("Login");
                if (userState.equals("true")) {
                    try {
                        UpdateDatabase.addItems(user,password);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } else if (userState.equals("false")) {
                    AlertBox.displayAlertBox("Error", "Wrong Admin Password");
                }
            }
        });
        deleteItemsButton.setOnAction(e->{
            try {
                dbState = CheckDatabaseExists.checkDatabase(user,password);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if (!dbState){
                AlertBox.displayAlertBox("Error","Please create a Database");
            }else {
                userState = VerifyUser.verifyUserUpdateOrDeleteDatabase("Login");
                if (userState.equals("true")) {
                    try {
                        UpdateDatabase.deleteItems(user,password);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } else if (userState.equals("false")) {
                    AlertBox.displayAlertBox("Error", "Wrong Admin Password");
                }
            }
        });
        updateItemsButton.setOnAction(e->{
            try {
                dbState = CheckDatabaseExists.checkDatabase(user,password);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if (!dbState){
                AlertBox.displayAlertBox("Error","Please create a Database");
            }else {
                userState = VerifyUser.verifyUserUpdateOrDeleteDatabase("Login");
                if (userState.equals("true")) {
                    try {
                        UpdateDatabase.updateItems(user,password);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } else if (userState.equals("false")) {
                    AlertBox.displayAlertBox("Error", "Wrong Admin Password");
                }
            }
        });

        deleteDbButton.setOnAction(e-> {
            try {
                dbState = CheckDatabaseExists.checkDatabase(user,password);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if (!dbState){
                AlertBox.displayAlertBox("Error","Please create a Database");
            }else {
                userState=VerifyUser.verifyUserUpdateOrDeleteDatabase("Delete");
                if (userState.equals("true")){
                    try {
                        String title = "Finished";
                        DeleteDatabase.deleteDatabase(user,password);
                        AlertBox.displayAlertBox(title,"Database successfully deleted");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }else if(userState.equals("false")){
                    AlertBox.displayAlertBox("Error","Wrong ADMIN Password");
                }
            }

        });

        createDbButton.setOnAction(e-> {
            try {
                dbState = CheckDatabaseExists.checkDatabase(user,password);
                if (!dbState){
                    CreateDatabase.createDatabase(user,password);
                    AlertBox.displayAlertBox("Finished","Database successfully created!");
                }
                else {
                    AlertBox.displayAlertBox("Error","Database Exists");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        userNameInput.setOnKeyPressed(event->{
            if (event.getCode()== KeyCode.ENTER){
                passwordInput.requestFocus();
            }
        });

        passwordInput.setOnKeyPressed(event->{
            if (event.getCode()==KeyCode.ENTER){
                loginButton.requestFocus();
            }
        });
        userNameInput.requestFocus();

        scene.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());
        scene2.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();


        /*DateTime.time();
        BorderPane border = new BorderPane();

        GridPane  layout1 = new GridPane();
        GridPane layout2 = new GridPane();


        Button createDbButton = new Button("Create Database");
        Button updateDbButton = new Button("Update Database");
        Button deleteDbButton = new Button("Delete Database");
        Button statsButton = new Button("Stats");
        Button viewSummary = new Button("Summary");

        createDbButton.setStyle("-fx-label-padding:5,5,5,5;");
        updateDbButton.setStyle("-fx-label-padding:5,5,5,5;");
        deleteDbButton.setStyle("-fx-label-padding:5,5,5,5;");
        statsButton.setStyle("-fx-label-padding:5,5,5,5;");
        viewSummary.setStyle("-fx-label-padding:5,5,5,5;");

        //createDbButton.getStyle()

        Button loginButton = new Button("Login");
        loginButton.setPrefWidth(150);
        Button addUserButton = new Button("Add User");
        addUserButton.setPrefWidth(150);
        Button removeUserButton = new Button("Remove User");
        removeUserButton.setPrefWidth(150);
        Button changePasswordButton = new Button("Change Password");
        changePasswordButton.setPrefWidth(150);

        layout1.add(createDbButton,0,0,1,1);
        layout1.add(updateDbButton,1,0,1,1);
        layout1.add(deleteDbButton,2,0,1,1);
        layout1.add(statsButton,3,0,1,1);
        layout1.add(viewSummary,4,0,1,1);
        layout1.setHgap(73);

        layout2.add(loginButton,1,0,1,1);
        layout2.add(addUserButton,0,1,1,1);
        layout2.add(removeUserButton,2,1,1,1);
        layout2.add(changePasswordButton,1,2,1,1);
        layout2.setAlignment(Pos.CENTER);
        layout2.setHgap(50);
        layout2.setVgap(60);

        border.setTop(layout1);
        border.setCenter(layout2);

        addUserButton.setOnAction( e -> {
            gotAccess = AddUser.addUserWindow();
            if (gotAccess.equals("true")){
                AlertBox.displayAlertBox("Finished","User added successfully");
            }else if(gotAccess.equals("false")){
                AlertBox.displayAlertBox("Error","ADMIN password is wrong");
            }
        });
        removeUserButton.setOnAction(e -> {
            operationState = RemoveUser.removeUserWindow();
            if (operationState.equals("success")){
                AlertBox.displayAlertBox("Success","User deleted successfully");
            }else if (operationState.equals("failed")){
                AlertBox.displayAlertBox("Error","Username or Admin password is wrong");
            }
        });
        changePasswordButton.setOnAction(e-> {
            operationState = ChangePassword.changeUserWindow();
            if (operationState.equals("success")){
                AlertBox.displayAlertBox("Success","Password changed successfully");
            }else if(operationState.equals("wrongAdminPassword")){
                AlertBox.displayAlertBox("Error","Wrong Admin password");
            }else if (operationState.equals("newPasswordNotMatching")){
                AlertBox.displayAlertBox("Error","New password not matching");
            }
        });

        loginButton.setOnAction(e-> {
            try {
                dbState = CheckDatabaseExists.checkDatabase();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if (!dbState){
                AlertBox.displayAlertBox("Error","Please create a Database");
            }else{
                String[] userState;
                userState = VerifyUser.verifyUserBillingWindow();
                if (userState[0].equals("true")){
                    BillingWindow.billingWindow(userState[1]);
                }else if (userState[0].equals("false")){
                    AlertBox.displayAlertBox("Error","Username of Password is wrong");
                }
            }


        });
        updateDbButton.setOnAction(e-> {
            try {
                dbState = CheckDatabaseExists.checkDatabase();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if (!dbState){
                AlertBox.displayAlertBox("Error","Please create a Database");
            }else {
                userState = VerifyUser.verifyUserUpdateOrDeleteDatabase("Login");
                if (userState.equals("true")) {
                    try {
                        UpdateDatabase.updateDatabaseWindow();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } else if (userState.equals("false")) {
                    AlertBox.displayAlertBox("Error", "Wrong ADMIN Password");
                }
            }
        });

        deleteDbButton.setOnAction(e-> {
            try {
                dbState = CheckDatabaseExists.checkDatabase();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if (!dbState){
                AlertBox.displayAlertBox("Error","Please create a Database");
            }else {
                userState=VerifyUser.verifyUserUpdateOrDeleteDatabase("Delete");
                if (userState.equals("true")){
                    try {
                        String title = "Finished";
                        DeleteDatabase.deleteDatabase();
                        AlertBox.displayAlertBox(title,"Database successfully deleted");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }else if(userState.equals("false")){
                    AlertBox.displayAlertBox("Error","Wrong ADMIN Password");
                }
            }

        });

        createDbButton.setOnAction(e-> {
            try {
                dbState = CheckDatabaseExists.checkDatabase();
                if (!dbState){
                    CreateDatabase.createDatabase();
                    AlertBox.displayAlertBox("Finished","Database successfully created!");
                }
                else {
                    AlertBox.displayAlertBox("Error","Database Exists");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        statsButton.setOnAction(e->{
            try {
                dbState = CheckDatabaseExists.checkDatabase();
                if (!dbState){
                    AlertBox.displayAlertBox("Error","Please create a database");
                }else {
                    System.out.println("hello");
                }
            }catch (SQLException throwables){
                throwables.printStackTrace();
            }
        });



        Scene scene = new Scene(border,760,500);
        scene.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());
        //scene.getStylesheets().add("Main.css");
        createDbButton.setId("MainWindowButton");
        updateDbButton.setId("MainWindowButton");
        deleteDbButton.setId("MainWindowButton");
        viewSummary.setId("MainWindowButton");
        changePasswordButton.setId("MainWindowButton");
        removeUserButton.setId("MainWindowButton");
        addUserButton.setId("MainWindowButton");
        loginButton.setId("MainWindowButton");

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("POS SYSTEM");
        //scene.setOnKeyPressed(this);
        stage.show();*/
    }


}
