package sample;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.sql.*;

public class MenuWindows {
    public static String discountPermissionValue="No";
    public static float updateDiscountPrice;
    public static float updateDiscountPolicy;
    public static String resetState;
    public static final String url = "jdbc:mysql://localhost/BILLINGDB";
    //public static final String user = "root";
    //public static final String password = "Avi990921pj";
    public static void showStockWindow(String user,String password)throws SQLException {
        Stock stockObj = new Stock();
        Connection dbConnection = DriverManager.getConnection(url,user,password);
        Statement databaseStatement = dbConnection.createStatement();

        Stage stage = new Stage();
        GridPane layout = new GridPane();

        TableView table = new TableView<Stock>();
        TableColumn itemNameColumn = new TableColumn<Stock,String>("Name");
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<Stock,String>("itemName"));
        itemNameColumn.setStyle("-fx-alignment:center;");

        TableColumn itemQuantityColumn = new TableColumn<Stock,Integer>("Quantity");
        itemQuantityColumn.setCellValueFactory(new PropertyValueFactory<Stock,Integer>("quantity"));
        itemQuantityColumn.setStyle("-fx-alignment:center;");

        table.getColumns().add(itemNameColumn);
        table.getColumns().add(itemQuantityColumn);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setEditable(false);
        layout.add(table,0,0);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout,450,380);


        ResultSet resultSet = databaseStatement.executeQuery("SELECT * FROM goods");
        while (resultSet.next()){
            stockObj.setItemName(resultSet.getString("ItemName").substring(0,1).toUpperCase()+
                    resultSet.getString("ItemName").substring(1).toLowerCase());
            stockObj.setQuantity(resultSet.getInt("Quantity"));
            table.getItems().add(new Stock(stockObj.getItemName(),stockObj.getQuantity()));
            //System.out.println(resultSet.getString("ItemName"));
        }
        scene.getStylesheets().add(MenuWindows.class.getResource("Main.css").toExternalForm());
        stage.setTitle("Stock");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                try {
                    databaseStatement.close();
                    dbConnection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        stage.showAndWait();



    }
    public static String settingWindow(String user,String password)throws SQLException{

        Connection dbConnection = DriverManager.getConnection(url,user,password);
        Statement databaseStatement = dbConnection.createStatement();

        Stage stage = new Stage();
        GridPane layout = new GridPane();
        Label resetData = new Label("Reset Data");
        Label resetGoods = new Label("Reset Goods");
        Label resetUser = new Label("Reset User");
        Label resetProfit = new Label("Reset Profit");

        Image goodsIcon = new Image(MenuWindows.class.getResourceAsStream("graphics/goods.png"));
        Image userIcon = new Image(MenuWindows.class.getResourceAsStream("graphics/user.png"));
        Image profitIcon = new Image(MenuWindows.class.getResourceAsStream("graphics/profit.png"));

        ImageView goodsIconView = new ImageView(goodsIcon);
        ImageView userIconView = new ImageView(userIcon);
        ImageView profitIconView = new ImageView(profitIcon);

        resetData.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD,30));
        resetData.setStyle("-fx-text-fill:#5198DE");
        resetData.setAlignment(Pos.CENTER);

        resetGoods.setFont(Font.font("Arial",FontWeight.BOLD,20));
        resetGoods.setGraphic(goodsIconView);

        resetUser.setFont(Font.font("Arial",FontWeight.BOLD,20));
        resetUser.setGraphic(userIconView);

        resetProfit.setFont(Font.font("Arial",FontWeight.BOLD,20));
        resetProfit.setGraphic(profitIconView);

        layout.add(resetData,0,0,1,1);
        layout.add(resetGoods,0,1,1,1);
        layout.add(resetUser,0,2,1,1);
        layout.add(resetProfit,0,3,1,1);

        layout.setAlignment(Pos.CENTER);
        layout.setVgap(15);

        resetGoods.setOnMouseClicked(e ->{
            try {
                databaseStatement.execute("DELETE FROM goods");
                resetState = "goods";
                stage.close();

            }catch (SQLException throwables){
                throwables.printStackTrace();
            }
        });

        resetUser.setOnMouseClicked(e ->{
           // String resetState;
            try {
                databaseStatement.execute("DELETE FROM user");
                databaseStatement.executeUpdate("INSERT INTO User (Username,Password)"
                        +" VALUES('admin','admin')");
                resetState = "user";
                stage.close();

            }catch (SQLException throwables){
                throwables.printStackTrace();
            }

        });

        resetProfit.setOnMouseClicked(e ->{
            try {
                databaseStatement.executeUpdate("DELETE FROM DailyProfit");
                databaseStatement.executeUpdate("DELETE FROM ProfitIndex");
                resetState = "profit";
                stage.close();
            }catch (SQLException throwables){
                throwables.printStackTrace();
            }
        });



        Scene scene = new Scene(layout,360,360);
        stage.setTitle("Reset");
        scene.getStylesheets().add(MenuWindows.class.getResource("Main.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                resetState = "null";
                try {
                    databaseStatement.close();
                    dbConnection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        return resetState;
    }
    public static void discountUpdateWindow(String user,String password)throws SQLException{

        Stage stage = new Stage();
        GridPane layout = new GridPane();

        Label itemNo = new Label("Item Number");
        Label discountPermission = new Label("Discount permission");
        Label discountPrice = new Label("Discount price");
        Label discountPolicy = new Label("Discount policy (Qty)");

        TextField itemNoEntry = new TextField();

        ChoiceBox <String> discountPermissionBox = new ChoiceBox<>();
        discountPermissionBox.getItems().add("Yes");
        discountPermissionBox.getItems().add("No");
        discountPermissionBox.setValue("Yes");

        TextField discountPriceEntry = new TextField();
        TextField discountPolicyEntry = new TextField();

        Button updateButton = new Button("Update");

        layout.add(itemNo,0,0);
        layout.add(discountPermission,1,0);
        layout.add(discountPrice,2,0);
        layout.add(discountPolicy,3,0);

        layout.add(itemNoEntry,0,1);
        layout.add(discountPermissionBox,1,1);
        layout.add(discountPriceEntry,2,1);
        layout.add(discountPolicyEntry,3,1);
        layout.add(updateButton,5,1);

        layout.setAlignment(Pos.CENTER);
        layout.setHgap(10);

        updateButton.setOnAction(e->{
            String discountPermissionValue = discountPermissionBox.getValue();
            String updateItemNo = itemNoEntry.getText().trim();
            if (!(discountPriceEntry.getText().trim().isEmpty())) {
                updateDiscountPrice = Float.parseFloat(discountPriceEntry.getText().trim());
            }
            if (!(discountPolicyEntry.getText().trim().isEmpty())){
                updateDiscountPolicy = Float.parseFloat(discountPolicyEntry.getText().trim());
            }
            try {
                Connection databaseConnection = DriverManager.getConnection(url,user,password);
                Statement databaseStatement = databaseConnection.createStatement();
                if (!(itemNoEntry.getText().trim().isEmpty())){
                    ResultSet resultSet = databaseStatement.executeQuery("SELECT * FROM goods WHERE ItemNumber='"+updateItemNo+"'");
                    if (!(resultSet.next())){
                        AlertBox.displayAlertBox("Error","Wrong item number");
                    }else{
                        if (!(discountPriceEntry.getText().trim().isEmpty())){
                            if (!(discountPolicyEntry.getText().trim().isEmpty())){
                                databaseStatement.executeUpdate("UPDATE goods SET DiscountPermission='"+discountPermissionValue+"',"+
                                        "DiscountPrice='"+updateDiscountPrice+"',"+
                                        "DiscountPolicy='"+updateDiscountPolicy+"'"+
                                        "WHERE ItemNumber='"+updateItemNo+"'");
                            }else {
                                databaseStatement.executeUpdate("UPDATE goods SET DiscountPermission='"+discountPermissionValue+"',"+
                                        "DiscountPrice='"+updateDiscountPrice+"'"+
                                        "WHERE ItemNumber='"+updateItemNo+"'");
                            }
                        }else if (!(discountPolicyEntry.getText().trim().isEmpty())){
                            databaseStatement.executeUpdate("UPDATE goods SET DiscountPermission='"+discountPermissionValue+"',"+
                                    "DiscountPolicy='"+updateDiscountPolicy+"'"+
                                    "WHERE ItemNumber='"+updateItemNo+"'");
                        }else {
                            databaseStatement.executeUpdate("UPDATE goods SET DiscountPermission='"+discountPermissionValue+"'"+
                                    "WHERE ItemNumber='"+updateItemNo+"'");
                        }
                    }
                }else {
                    AlertBox.displayAlertBox("Error","Please enter item number");
                };
            }catch (SQLException throwables){
                throwables.printStackTrace();
            };
            itemNoEntry.clear();
            discountPolicyEntry.clear();
            discountPriceEntry.clear();
            discountPermissionBox.setValue("Yes");
        });

        Scene scene = new Scene(layout,760,240);
        scene.getStylesheets().add(MenuWindows.class.getResource("Main.css").toExternalForm());
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Update Discount");
        stage.setScene(scene);
        stage.showAndWait();

        //discountPermissionBox.getSelectionModel().getSelectedItem();
        //String discountPermissionValue = discountPermissionBox.getValue();

    //});

    }
    public static String discountWindow(){

        Stage stage = new Stage();
        GridPane layout = new GridPane();

        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton yes = new RadioButton("Yes");
        yes.setUserData("Yes");
        yes.setToggleGroup(toggleGroup);

        RadioButton no = new RadioButton("No");
        no.setUserData("No");
        no.setToggleGroup(toggleGroup);

        Button setButton = new Button("Set");

        layout.add(yes,0,0,1,1);
        layout.add(no,1,0,1,1);
        layout.add(setButton,2,0,1,1);
        layout.setAlignment(Pos.CENTER);
        layout.setHgap(10);

        setButton.setOnAction(e->{
            discountPermissionValue = toggleGroup.getSelectedToggle().getUserData().toString();
            //System.out.println(discountPermissionValue);
            stage.close();
        });

        Scene scene = new Scene(layout,360,240);
        scene.getStylesheets().add(MenuWindows.class.getResource("Main.css").toExternalForm());
        stage.setTitle("Discount");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                discountPermissionValue = "No";
            }
        });
        stage.setScene(scene);
        stage.showAndWait();
        return discountPermissionValue;
    }
}
