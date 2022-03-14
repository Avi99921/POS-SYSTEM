package sample;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;
import java.util.Currency;

public class BillingWindow {
    public static String discountPermissionvalue="Yes";
    public static String resetState = "null";
    public static final String url = "jdbc:mysql://localhost/BILLINGDB";
    //public static final String user = "root";
    //public static final String password = "Avi990921pj";

    public static void billingWindow(String loggedUser,String user,String password) {

        Currency intToCurrency;
        Total totalObj = new Total();
        Table tableObj = new Table();
        Profit profitObj = new Profit();
        CategoryQuantity categoryQuantityObj = new CategoryQuantity();
        Stage stage = new Stage();

        GridPane widgetContainer = new GridPane();
        GridPane layout1 = new GridPane();
        GridPane layout2 = new GridPane();
        GridPane layout3 = new GridPane();
        GridPane layout5 = new GridPane();
        GridPane layout5_1 = new GridPane();

        Label spaceLabelLayout3 = new Label("");

        Label invoiceLayout1 = new Label("DG Trading Co");
        invoiceLayout1.setId("invoiceLayout1");
        //Label userNameLayout1 = new Label("User");
        //Label dateLayout1 = new Label("Date");
        Label invoiceNoLayout1 = new Label("Invoice no");
        Label userNameTextLayout1 = new Label();
        Label dateEntryLayout1 = new Label();
        TextField invoiceNoEntryLayout1 = new TextField();

        Label itemNoLayout2 = new Label("Item No");
        Label quantityLayout2 = new Label("Quantity");
        Label unitPriceLayout2 = new Label("Unit Price");


        TextField itemNoEntryLayout2 = new TextField();
        TextField quantityEntryLayout2 = new TextField();
        TextField unitPriceEntryLayout2 = new TextField();
        Button addButton = new Button("ADD");

        TableView table = new TableView<Table>();
        TableColumn itemNumberColumn = new TableColumn<Table,String>("Item Code");
        itemNumberColumn.setCellValueFactory(new PropertyValueFactory<Table,String>("itemNumber"));
        itemNumberColumn.setStyle("-fx-alignment:center;");

        TableColumn itemNameColumn = new TableColumn<Table, String>("Name");
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<Table, String>("name"));
        itemNameColumn.setStyle("-fx-alignment:center;");

        TableColumn itemCategoryColumn = new TableColumn<Table,String>("Category");
        itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<Table,String>("category"));
        itemCategoryColumn.setStyle("-fx-alignment:center;");

        TableColumn unitPriceColumn = new TableColumn<Table, Float>("Unit Price");
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<Table, Integer>("unitPrice"));
        unitPriceColumn.setStyle("-fx-alignment:center-right;");

        TableColumn discountColumn = new TableColumn<Table,Float>("Discount");
        discountColumn.setCellValueFactory(new PropertyValueFactory<Table,Float>("discount"));
        discountColumn.setStyle("-fx-alignment:center-right;");

        TableColumn quantityColumn = new TableColumn<Table, Integer>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Table, Integer>("quantity"));
        quantityColumn.setStyle("-fx-alignment:center-right;");

        TableColumn totalColumn = new TableColumn<Table, Float>("Amount");
        totalColumn.setCellValueFactory(new PropertyValueFactory<Table, Integer>("amount"));
        totalColumn.setStyle("-fx-alignment:center-right;");



        Label subTotalLayout3 = new Label("Sub Total (Rs)");
        subTotalLayout3.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD,20));
        Label subTotalEntry = new Label();
        subTotalEntry.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD,30));
        subTotalEntry.setPrefWidth(200);
        subTotalEntry.setPrefHeight(50);
        subTotalEntry.setAlignment(Pos.CENTER_RIGHT);
        //subTotalEntry.setDisable(true);

        Label cashLayout3 = new Label("Cash (Rs)");
        cashLayout3.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD,20));
        TextField cashEntry = new TextField();
        cashEntry.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD,30));
        cashEntry.setAlignment(Pos.CENTER_RIGHT);
        cashEntry.setPrefWidth(200);
        cashEntry.setPrefHeight(50);

        Label balanceLayout3 = new Label("Balance (Rs)");
        balanceLayout3.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD,20));

        TextField balanceEntry = new TextField();
        balanceEntry.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD,30));
        balanceEntry.setAlignment(Pos.CENTER_RIGHT);
        balanceEntry.setPrefWidth(200);
        balanceEntry.setPrefHeight(50);

        subTotalEntry.setId("TotalBalance");
        cashEntry.setId("Cash");
        balanceEntry.setId("TotalBalance");


        Button checkoutLayout3 = new Button("Checkout");
        checkoutLayout3.setFont(Font.font("Arial",20));

        Button resetWindowLayout4 = new Button("Reset");
        resetWindowLayout4.setFont(Font.font("Arial",20));


        table.getColumns().add(itemNumberColumn);
        table.getColumns().add(itemNameColumn);
        table.getColumns().add(itemCategoryColumn);
        table.getColumns().add(unitPriceColumn);
        table.getColumns().add(discountColumn);
        table.getColumns().add(quantityColumn);
        table.getColumns().add(totalColumn);
        itemNumberColumn.setMaxWidth(80);
        itemNameColumn.setPrefWidth(150);
        itemCategoryColumn.setPrefWidth(150);
        unitPriceColumn.setPrefWidth(150);
        discountColumn.setPrefWidth(150);
        quantityColumn.setPrefWidth(150);
        totalColumn.setPrefWidth(150);

        //TableView.TableViewSelectionModel selectionModel = table.getSelectionModel();
        //TableView.TableViewSelectionModel<Table>selectionModel = table.getSelectionModel();
        //selectionModel.setSelectionMode(SelectionMode.SINGLE);



        userNameTextLayout1.setText(loggedUser.toUpperCase());
        userNameTextLayout1.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 20));
        //userNameTextLayout1.setDisable(true);

        dateEntryLayout1.setText(String.valueOf(LocalDate.now()));
        dateEntryLayout1.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        //dateEntryLayout1.setDisable(true);

        invoiceLayout1.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 50));
        invoiceLayout1.setAlignment(Pos.BASELINE_CENTER);


        Image homeIcon = new Image(BillingWindow.class.getResourceAsStream("graphics/home.png"));
        Image addIcon = new Image(BillingWindow.class.getResourceAsStream("graphics/add.png"));
        Image stockIcon = new Image(BillingWindow.class.getResourceAsStream("graphics/stock.png"));
        Image employeeIcon = new Image(BillingWindow.class.getResourceAsStream("graphics/employee.png"));
        Image statsIcon = new Image(BillingWindow.class.getResourceAsStream("graphics/stats.png"));
        Image reportIcon = new Image(BillingWindow.class.getResourceAsStream("graphics/report.png"));
        Image backupIcon = new Image(BillingWindow.class.getResourceAsStream("graphics/backup.png"));
        Image discountIcon = new Image(BillingWindow.class.getResourceAsStream("graphics/discount.png"));
        Image discountOptionIcon = new Image(BillingWindow.class.getResourceAsStream("graphics/discountOption.png"));
        Image settingsIcon = new Image(BillingWindow.class.getResourceAsStream("graphics/settings.png"));
        Image logoutIcon = new Image(BillingWindow.class.getResourceAsStream("graphics/logout.png"));

        ImageView homeIconView = new ImageView(homeIcon);
        ImageView addIconView = new ImageView(addIcon);
        ImageView stockIconView = new ImageView(stockIcon);
        ImageView employeeIconView = new ImageView(employeeIcon);
        ImageView statsIconView = new ImageView(statsIcon);
        ImageView reportIconView = new ImageView(reportIcon);
        ImageView backupIconView = new ImageView(backupIcon);
        ImageView discountImageView = new ImageView(discountIcon);
        ImageView discountOptionImageView = new ImageView(discountOptionIcon);
        ImageView settingsIconView = new ImageView(settingsIcon);
        ImageView logoutIconView = new ImageView(logoutIcon);

        homeIconView.setFitHeight(48);
        homeIconView.setFitWidth(48);
        settingsIconView.setFitHeight(48);
        settingsIconView.setFitWidth(48);
        logoutIconView.setFitHeight(48);
        logoutIconView.setFitWidth(48);



        Label homeLabel = new Label("Home");
        homeLabel.setGraphic(homeIconView);
        homeLabel.setFont(Font.font("Arial",FontWeight.BOLD,20));
        homeLabel.setPrefWidth(180);
        homeLabel.setId("menulabel");

        Label addItemsLabel = new Label("Insert");
        addItemsLabel.setGraphic(addIconView);
        addItemsLabel.setFont(Font.font("Arial",FontWeight.BOLD,20));
        addItemsLabel.setPrefWidth(180);
        addItemsLabel.setId("menulabel");

        Label stockLabel = new Label("Stock");
        stockLabel.setGraphic(stockIconView);
        stockLabel.setFont(Font.font("Arial",FontWeight.BOLD,20));
        stockLabel.setPrefWidth(180);
        stockLabel.setId("menulabel");

        Label employeeLabel = new Label("Employee");
        employeeLabel.setGraphic(employeeIconView);
        employeeLabel.setFont(Font.font("Arial",FontWeight.BOLD,20));
        employeeLabel.setPrefWidth(180);
        employeeLabel.setId("menulabel");

        Label statsLabel = new Label("Stats");
        statsLabel.setGraphic(statsIconView);
        statsLabel.setFont(Font.font("Arial",FontWeight.BOLD,20));
        statsLabel.setPrefWidth(180);
        statsLabel.setId("menulabel");

        Label reportLabel = new Label("Report");
        reportLabel.setGraphic(reportIconView);
        reportLabel.setFont(Font.font("Arial",FontWeight.BOLD,20));
        reportLabel.setPrefWidth(180);
        reportLabel.setId("menulabel");

        Label backupLabel = new Label("Backup");
        backupLabel.setGraphic(backupIconView);
        backupLabel.setFont(Font.font("Arial",FontWeight.BOLD,20));
        backupLabel.setPrefWidth(180);
        backupLabel.setId("menulabel");

        Label discountLabel = new Label("Discount");
        discountLabel.setGraphic(discountImageView);
        discountLabel.setFont(Font.font("Arial",FontWeight.BOLD,20));
        discountLabel.setPrefWidth(180);
        discountLabel.setId("menulabel");

        Label discountOptionLabel = new Label("Discount option");
        discountOptionLabel.setGraphic(discountOptionImageView);
        discountOptionLabel.setFont(Font.font("Arial",FontWeight.BOLD,15));
        discountOptionLabel.setPrefWidth(180);
        discountOptionLabel.setId("menulabel");

        Label settingsLabel = new Label("Settings");
        settingsLabel.setGraphic(settingsIconView);
        settingsLabel.setFont(Font.font("Arial",FontWeight.BOLD,20));
        settingsLabel.setPrefWidth(180);
        settingsLabel.setId("menulabel");

        Label logoutLabel = new Label("Logout");
        logoutLabel.setGraphic(logoutIconView);
        logoutLabel.setFont(Font.font("Arial",FontWeight.BOLD,20));
        logoutLabel.setPrefWidth(180);
        logoutLabel.setId("menulabel");

        layout1.add(invoiceLayout1, 0, 0, 10, 1);
        layout1.add(userNameTextLayout1, 10, 0, 1, 1);
        layout1.add(dateEntryLayout1, 11, 0, 1, 1);
        layout1.add(invoiceNoLayout1, 12, 0, 1, 1);
        layout1.add(invoiceNoEntryLayout1, 13, 0, 1, 1);
        layout1.setHgap(50);

        layout2.add(itemNoLayout2, 0, 0, 1, 1);
        layout2.add(itemNoEntryLayout2, 1, 0, 1, 1);
        layout2.add(quantityLayout2, 2, 0, 1, 1);
        layout2.add(quantityEntryLayout2, 3, 0, 1, 1);
        layout2.add(unitPriceLayout2, 4, 0, 1, 1);
        layout2.add(unitPriceEntryLayout2, 5, 0, 1, 1);
        layout2.add(addButton, 6, 0, 1, 1);
        layout2.setHgap(20);

        layout3.add(table, 0, 0, 1, 8);

        layout3.add(subTotalLayout3,1,0);
        layout3.add(subTotalEntry,1,1);
        layout3.add(cashLayout3,1,2);
        layout3.add(cashEntry,1,3);
        layout3.add(balanceLayout3,1,4);
        layout3.add(balanceEntry,1,5);
        layout3.add(checkoutLayout3,1,6);
        layout3.add(resetWindowLayout4,1,7);

        layout5_1.add(logoutLabel,0,0);
        layout5_1.setAlignment(Pos.BOTTOM_CENTER);

       // layout5.add(homeLabel,0,0,1,1);
        layout5.add(addItemsLabel,0,1,1,1);
        layout5.add(stockLabel,0,2,1,1);
        layout5.add(employeeLabel,0,3,1,1);
        layout5.add(statsLabel,0,4,1,1);
        layout5.add(reportLabel,0,5,1,1);
        layout5.add(backupLabel,0,6,1,1);
        layout5.add(discountLabel,0,7,1,1);
        layout5.add(discountOptionLabel,0,8,1,1);
        layout5.add(settingsLabel,0,9,1,1);
        layout5.add(layout5_1,0,10,1,5);

        layout5.setAlignment(Pos.CENTER);
        layout5.setVgap(20);
        //layout4.add(endTransactionLayout4,0,2,1,1);
        //layout4.add(resetWindowLayout4,1,2,1,1);
       // layout4.setHgap(0);
        //layout4.setVgap(20);
        widgetContainer.add(layout5,0,0,1,7);
        widgetContainer.add(layout1, 1, 0);
        widgetContainer.add(layout2, 1, 1);

        widgetContainer.add(layout3, 1,2);
        //widgetContainer.add(layout4,1,2);
        layout3.setVgap(10);
        widgetContainer.setVgap(30);


        //layout1.setAlignment(Pos.TOP_RIGHT);
        Scene scene = new Scene(widgetContainer);

        addButton.setId("MainWindowButton");

        Rectangle2D screenBound = Screen.getPrimary().getBounds();
        double stageWidth = screenBound.getMaxX();
        double stageHeight = screenBound.getMaxY();

       // System.out.println(screenBound);
       // System.out.println(stageHeight);

//===============================================ADD ITEMS SCENE=====================================================
        //Scene scene1 = new Scene(layout5,stageWidth,stageHeight);

        addItemsLabel.setOnMouseClicked(e->{
            String userState;
            if (userNameTextLayout1.getText().toLowerCase().equals("admin")){
                try {
                    UpdateDatabase.addItems(user,password);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }else {
                userState = VerifyUser.verifyUserUpdateOrDeleteDatabase("Login");
                if (userState.equals("true")){
                    try {
                        UpdateDatabase.addItems(user,password);
                    }catch (SQLException throwables){
                        throwables.printStackTrace();
                    }
                }else if (userState.equals("false")){
                    AlertBox.displayAlertBox("Error","Wrong Admin Password");
                }
            }

        });
        homeLabel.setOnMouseClicked(e->{
            stage.setScene(scene);
        });

        stockLabel.setOnMouseClicked(e->{
            try {
                MenuWindows.showStockWindow(user,password);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        System.out.println(discountPermissionvalue);
        discountLabel.setOnMouseClicked(e ->{
            discountPermissionvalue = MenuWindows.discountWindow();
           // MenuWindows.discountWindow();
            //System.out.println(discountPermissionvalue);
        });
        discountOptionLabel.setOnMouseClicked(e->{
            try {
                MenuWindows.discountUpdateWindow(user,password);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        reportLabel.setOnMouseClicked(e->{
            String requestReport = AskReport.askDailyOrMonthly();
            if (requestReport.equals("Day")) {
                try {
                    ShowReport.showDailyReport(user,password);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }else if (requestReport.equals("Month")){
                try {
                    ShowMonthlyReport.showMonthlyReport(user,password);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });


        settingsLabel.setOnMouseClicked(e->{
            String userState;
            userState = VerifyUser.verifyUserUpdateOrDeleteDatabase("Login");
            if (userState.equals("true")){
                try {
                    resetState = MenuWindows.settingWindow(user,password);
                }catch (SQLException throwables){
                    throwables.printStackTrace();
                }
            }else if (userState.equals("false")){
                AlertBox.displayAlertBox("Error","Wrong Admin password");
            }
            if (resetState.equals("user")){
                userNameTextLayout1.setText("ADMIN");
            }else if (resetState.equals("goods")){
                cashEntry.clear();
                //balanceEntry.setText("");
                table.getItems().clear();
                float resetProfit = profitObj.getProfit();
                if (resetProfit==0){
                    profitObj.setProfit(0);
                }else {
                    profitObj.setProfit(-(resetProfit));
                }
                if (subTotalEntry.getText().trim().isEmpty()) {
                    totalObj.setTotal(0);
                }else{
                    totalObj.setTotal(-(Float.parseFloat(subTotalEntry.getText())));
                    }
                //totalObj.setTotal(-(Float.parseFloat(subTotalEntry.getText())));
                subTotalEntry.setText("");
            }
        });


        addButton.setOnAction(e -> {
            System.out.println(discountPermissionvalue);
            if (itemNoEntryLayout2.getText().trim().isEmpty()) {
                AlertBox.displayAlertBox("Error", "Please fill required fields");
            } else if (quantityEntryLayout2.getText().trim().isEmpty()) {
                AlertBox.displayAlertBox("Error", "Please fill required fields");
            } else {
                int quantity = Integer.parseInt(quantityEntryLayout2.getText());
                String category = "";
                float unitPrice=0;
                float amount;
                float buyingPrice = 0;
                float discount = 0;
                float newUnitPrice = 0;
                float databaseUnitPrice =0;
                int databaseQuantity = 0;
                String itemNumber = itemNoEntryLayout2.getText().trim();
                Connection databaseConnection = null;
                Statement databaseStatement = null;

                try {
                    databaseConnection = DriverManager.getConnection(url, user, password);
                    databaseStatement = databaseConnection.createStatement();
//CHECK ITEM EXISTS SQL==========================================================================================================
                    String sql = "SELECT ItemName FROM goods WHERE ItemNumber='" + itemNoEntryLayout2.getText().trim() + "'";
                    ResultSet resultSet = databaseStatement.executeQuery(sql);
                    if (!resultSet.next()) {
                        AlertBox.displayAlertBox("Error", "Wrong item number");
                    } else {
                        String itemName = resultSet.getString("ItemName").substring(0, 1).toUpperCase() +
                                resultSet.getString("ItemName").substring(1).toLowerCase();
                        resultSet = databaseStatement.executeQuery("SELECT BuyingPrice FROM goods WHERE ItemNumber='"+
                                itemNoEntryLayout2.getText().trim()+"'");
                        while (resultSet.next()){
                            buyingPrice = Float.parseFloat(resultSet.getString("BuyingPrice")) ;
                        }
                        if (unitPriceEntryLayout2.getText().trim().isEmpty()) {
//GET UnitPrice FROM DATABASE when unitprice entry field is EMPTY=============================================================
                            resultSet = databaseStatement.executeQuery("SELECT * FROM goods WHERE ItemNumber='" +
                                    itemNoEntryLayout2.getText().trim() + "'");
                            while (resultSet.next()) {
                                category = resultSet.getString("Category");
                                unitPrice = resultSet.getFloat("UnitPrice");
                                databaseQuantity = resultSet.getInt("Quantity");
                                databaseQuantity = databaseQuantity-quantity;
                                if (discountPermissionvalue.trim().equals("Yes")){
                                    String itemDiscountPermission = resultSet.getString("DiscountPermission");
                                    //System.out.println(itemDiscountPermission+"-----------------");
                                    if (itemDiscountPermission.equals("Yes")){
                                        int discountQuantity = (int) resultSet.getFloat("DiscountPolicy");
                                        if (quantity>=discountQuantity){
                                            discount = resultSet.getFloat("DiscountPrice");
                                            newUnitPrice = unitPrice-discount;
                                        }else {
                                            discount=0;
                                            newUnitPrice = unitPrice;
                                        }
                                    }else {
                                        discount = 0;
                                        newUnitPrice = unitPrice;
                                    }

                                }else {
                                    discount=  0;
                                    newUnitPrice = unitPrice;
                                }

                            }
                            resultSet = databaseStatement.executeQuery("SELECT * FROM TemporaryGoods WHERE ItemNumber='"+itemNumber+"'");
                            if (!(resultSet.next())){
                                databaseStatement.executeUpdate("INSERT INTO TemporaryGoods(ItemNumber,Quantity) VALUES("
                                +itemNumber+","+quantity+")");
                            }else {
                                int temporaryQuantity=0;
                                resultSet = databaseStatement.executeQuery("SELECT Quantity FROM TemporaryGoods WHERE ItemNumber='"+itemNumber+"'");
                                while (resultSet.next()){
                                    temporaryQuantity = resultSet.getInt("Quantity");
                                }
                                int newQuantity = temporaryQuantity+quantity;
                                databaseStatement.executeUpdate("UPDATE TemporaryGoods SET Quantity='"+newQuantity
                                        +"' WHERE ItemNumber='"+itemNumber+"'");
                            }
                            System.out.println("Unit price is"+unitPrice);
                            databaseStatement.executeUpdate("UPDATE goods SET Quantity='"+databaseQuantity+"' WHERE ItemNumber='"+
                                    itemNoEntryLayout2.getText().trim()+"'");
                            amount = quantity * newUnitPrice;
                            totalObj.setTotal(amount);
                            profitObj.setProfit((newUnitPrice-buyingPrice)*quantity);
                            String tableAmount = String.format("%.2f",amount);
                            table.getItems().add(new Table(itemNumber,itemName,category, unitPrice, quantity, tableAmount,discount));
                        } else {
                            resultSet = databaseStatement.executeQuery("SELECT * FROM goods WHERE ItemNumber='" +
                                    itemNoEntryLayout2.getText().trim() + "'");
                            while (resultSet.next()){
                                category = resultSet.getString("Category");
                                databaseUnitPrice = resultSet.getFloat("UnitPrice");
                            }
                            unitPrice = Float.parseFloat(unitPriceEntryLayout2.getText().trim());
                            if (databaseUnitPrice>unitPrice){
                                discount = databaseUnitPrice-unitPrice;
                                amount = quantity * unitPrice;
                                profitObj.setProfit((unitPrice-buyingPrice)*quantity);
                                unitPrice = databaseUnitPrice;
                            }else {
                                discount=0;
                                amount = quantity * unitPrice;
                                profitObj.setProfit((unitPrice-buyingPrice)*quantity);
                            }

                            totalObj.setTotal(amount);

                            String tableAmount = String.format("%.2f",amount);
                            table.getItems().add(new Table(itemNumber,itemName,category, unitPrice, quantity, tableAmount,discount));
                        }
                        //Food Item","Cleaning Item","Personal Care","Beverages","Other"

                        if (category.equals("Food Item")){
                            categoryQuantityObj.setFoodItems(quantity);
                        }else if (category.equals("Cleaning Item")){
                            categoryQuantityObj.setCleaningItem(quantity);
                        }else if (category.equals("Personal Care")){
                            categoryQuantityObj.setPersonalCare(quantity);
                        }else if (category.equals("Beverages")) {
                            categoryQuantityObj.setBeverages(quantity);
                        }else if (category.equals("Other")){
                            categoryQuantityObj.setOther(quantity);
                        }
                        System.out.println("Food item---"+categoryQuantityObj.getFoodItems());
                        System.out.println("Cleaning Item---"+categoryQuantityObj.getCleaningItem());
                        System.out.println("Personal care---"+categoryQuantityObj.getPersonalCare());
                        System.out.println("Beverages---"+categoryQuantityObj.getBeverages());
                        System.out.println("Other---"+categoryQuantityObj.getOther());
                        System.out.println("Profit is---"+profitObj.getProfit());
                        //subTotalEntry.setDisable(false);
                        //subTotalEntry.clear();
                        String totalAmount = String.format("%.2f",totalObj.getTotal()) ;
                        subTotalEntry.setText(totalAmount);

                        itemNoEntryLayout2.clear();
                        unitPriceEntryLayout2.clear();
                        quantityEntryLayout2.clear();

                        //subTotalEntry.setDisable(true);
                        //Table data = (Table) table.getSelectionModel().getSelectedItem();

                       // System.out.println(totalObj.getTotal());
                        //System.out.println(itemName);
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


            }
            itemNoEntryLayout2.requestFocus();

        });

        table.setOnKeyPressed(event->{
            if (event.getCode() == KeyCode.DELETE) {
                Table data = (Table) table.getSelectionModel().getSelectedItem();
                Float buyingPrice = null;
                String tableItemNumber = "";
                String deleteCondition="";
                String tableCategory = "";
                int tableQuantity = 0;
                int databaseQuantity = 0;
                int deleteQuantity=0;
                String itemName = data.getName();

                Connection databaseConnection = null;
                Statement databaseStatement = null;
                try {
                    databaseConnection = DriverManager.getConnection(url,user,password);
                    databaseStatement = databaseConnection.createStatement();

                    ResultSet resultSet = databaseStatement.executeQuery("SELECT * FROM goods WHERE ItemNumber='"+data.getItemNumber()+"'");
                    while (resultSet.next()){
                        buyingPrice = resultSet.getFloat("BuyingPrice");
                        databaseQuantity = resultSet.getInt("Quantity")+data.getQuantity();
                    }
                    tableCategory = data.getCategory();
                    tableItemNumber = data.getItemNumber();
                    tableQuantity = data.getQuantity();
                    totalObj.setTotal(-1*(Float.parseFloat(data.getAmount())));
                    profitObj.setProfit(-1*(((Float.parseFloat(data.getAmount())/data.getQuantity())-buyingPrice)*data.getQuantity()));
                    System.out.println(profitObj.getProfit());
                    //System.out.println(totalObj.getTotal());
                    //subTotalEntry.clear();
                    databaseStatement.executeUpdate("UPDATE goods SET Quantity='"+databaseQuantity+"' WHERE ItemNumber='"+data.getItemNumber()+"'");
                    subTotalEntry.setText(Float.toString(totalObj.getTotal()));
                    table.getItems().removeAll(table.getSelectionModel().getSelectedItem());

                    resultSet = databaseStatement.executeQuery("SELECT * FROM TemporaryGoods WHERE ItemNumber='"+tableItemNumber+"'");
                    while (resultSet.next()){
                        deleteQuantity =  resultSet.getInt("Quantity");
                        if (deleteQuantity==tableQuantity){
                            deleteCondition = "DELETE ALL";

                        }else if (deleteQuantity>tableQuantity){
                            deleteCondition = "CHANGE QUANTITY";

                        }
                    }
                    if (deleteCondition.equals("DELETE ALL")){
                        databaseStatement.executeUpdate("DELETE FROM TemporaryGoods WHERE ItemNumber='"+tableItemNumber+"'");
                    }else if (deleteCondition.equals("CHANGE QUANTITY")){
                        int newQuantity = deleteQuantity-tableQuantity;
                        databaseStatement.executeUpdate("UPDATE TemporaryGoods SET Quantity='"+newQuantity+"' WHERE ItemNumber='"+tableItemNumber+"'");
                    }
                    if (tableCategory.equals("Food Item")){
                        categoryQuantityObj.setFoodItems(-tableQuantity);
                    }else if (tableCategory.equals("Cleaning Item")){
                        categoryQuantityObj.setCleaningItem(-tableQuantity);
                    }else if (tableCategory.equals("Personal Care")){
                        categoryQuantityObj.setPersonalCare(-tableQuantity);
                    }else if (tableCategory.equals("Beverages")) {
                        categoryQuantityObj.setBeverages(-tableQuantity);
                    }else if (tableCategory.equals("Other")){
                        categoryQuantityObj.setOther(-tableQuantity);
                    }
                    System.out.println("Food item---"+categoryQuantityObj.getFoodItems());
                    System.out.println("Cleaning Item---"+categoryQuantityObj.getCleaningItem());
                    System.out.println("Personal care---"+categoryQuantityObj.getPersonalCare());
                    System.out.println("Beverages---"+categoryQuantityObj.getBeverages());
                    System.out.println("Other---"+categoryQuantityObj.getOther());

                    databaseStatement.close();
                    databaseConnection.close();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                
            }
        });
        itemNoEntryLayout2.setOnKeyPressed(event->{
            if (event.getCode()== KeyCode.RIGHT){
                quantityEntryLayout2.requestFocus();
            }else if (event.getCode()==KeyCode.ENTER){
                quantityEntryLayout2.requestFocus();
            }
            System.out.println(event.getCode().toString());
        });
        quantityEntryLayout2.setOnKeyPressed(event->{
            if (event.getCode()==KeyCode.RIGHT){
                addButton.requestFocus();
            }else if (event.getCode()==KeyCode.ENTER){
                addButton.requestFocus();
            }else if (event.getCode()==KeyCode.LEFT){
                itemNoEntryLayout2.requestFocus();
            }
        });
        unitPriceEntryLayout2.setOnKeyPressed(event->{
            if (event.getCode()==KeyCode.RIGHT){
                addButton.requestFocus();
            }else if (event.getCode()==KeyCode.LEFT){
                quantityEntryLayout2.requestFocus();
            }else if (event.getCode()==KeyCode.ENTER){
                addButton.requestFocus();
            }

        });
        addButton.setOnKeyPressed(event->{
            if (event.getCode()==KeyCode.LEFT){
                quantityEntryLayout2.requestFocus();
            }else if (event.getCode()==KeyCode.RIGHT){
                addButton.requestFocus();
            }
        });

        scene.setOnKeyPressed(event->{
            if (event.getCode()==KeyCode.DOWN){
                table.requestFocus();
            }
        });

        checkoutLayout3.setOnAction(event->{
            Connection databaseConnection = null;
            Statement databaseStatement = null;
            int profitIndex = 0;
            int newProfitIndex = 0;
            float netTotal = 0;
            Float oldProfit = null;
            //String billDate = String.valueOf(LocalDate.now());
            String billDate = balanceEntry.getText().trim();
            String date = null;

            if (cashEntry.getText().trim().isEmpty()){
                AlertBox.displayAlertBox("Error","Please enter cash amount");
            }
            else{
                float cash = Float.parseFloat(cashEntry.getText().trim());
                netTotal = Float.parseFloat(subTotalEntry.getText().trim());
                float balance = cash-netTotal;
                //balanceEntry.setText(String.format("%.2f",balance));
                try {
                    databaseConnection = DriverManager.getConnection(url,user,password);
                    databaseStatement = databaseConnection.createStatement();

                    ResultSet resultSet = databaseStatement.executeQuery("SELECT * FROM DailyProfit");
                    if (!(resultSet.next())){
                        databaseStatement.executeUpdate("INSERT INTO DailyProfit(Number,Date,Profit)"+
                                " VALUES('"+1+"','"+billDate+"','"+profitObj.getProfit()+"')");
                        databaseStatement.executeUpdate("INSERT INTO ProfitIndex(Number,TableName)"+
                                " VALUES('"+1+"',"+"'DailyProfit')");
                    }else {
                        resultSet = databaseStatement.executeQuery("SELECT Number FROM ProfitIndex WHERE TableName='DailyProfit'");

                        while (resultSet.next()){
                            profitIndex = resultSet.getInt("Number");

                        }
                        resultSet = databaseStatement.executeQuery("SELECT Date FROM DailyProfit WHERE Number='"+profitIndex+"'");
                        while (resultSet.next()){
                            date = resultSet.getString("Date");
                        }

                        if (date.equals(billDate)){
                            resultSet = databaseStatement.executeQuery("SELECT Profit FROM DailyProfit WHERE Number='"+profitIndex+"'");
                            while(resultSet.next()){
                                oldProfit = resultSet.getFloat("Profit");
                            }
                            Float newProfit = oldProfit+profitObj.getProfit();
                            databaseStatement.executeUpdate("UPDATE DailyProfit SET Profit='"+newProfit+"' WHERE Number='"+profitIndex+"'");
                        }else {
                            newProfitIndex = profitIndex+1;
                            databaseStatement.executeUpdate("INSERT INTO DailyProfit(Number,Date,Profit)"+
                                    " VALUES('"+newProfitIndex+"','"+billDate+"','"+profitObj.getProfit()+"')");
                            databaseStatement.executeUpdate("UPDATE ProfitIndex SET Number="+
                                    "'"+newProfitIndex+"' WHERE TableName='DailyProfit'");
                        }


                    }
                    try {
                        String billDateInDatabase="";
                        resultSet = databaseStatement.executeQuery("SELECT * FROM DailySales");
                        if (!(resultSet.next())){
                            databaseStatement.executeUpdate("INSERT INTO DailySales(Date,FoodItem,CleaningItem,PersonalCare,Beverages,Other,Revenue,Profit)"+
                                    " VALUES('"+billDate+"','"+categoryQuantityObj.getFoodItems()+"','"+categoryQuantityObj.getCleaningItem()+"','"+ categoryQuantityObj.getPersonalCare()+"','"+
                                    categoryQuantityObj.getBeverages()+"','"+categoryQuantityObj.getOther()+"','"+netTotal+"','"+profitObj.getProfit()+"')");
                        }else {
                            resultSet = databaseStatement.executeQuery("SELECT * FROM DailySales");
                            while (resultSet.next()){
                                if (billDate.equals(resultSet.getString("Date"))){
                                    billDateInDatabase = "Yes";
                                    break;
                                }else {
                                    billDateInDatabase = "No";
                                }
                            }
                            if (billDateInDatabase.equals("No")){
                                databaseStatement.executeUpdate("INSERT INTO DailySales(Date,FoodItem,CleaningItem,PersonalCare,Beverages,Other,Revenue,Profit)"+
                                        " VALUES('"+billDate+"','"+categoryQuantityObj.getFoodItems()+"','"+categoryQuantityObj.getCleaningItem()+"','"+ categoryQuantityObj.getPersonalCare()+
                                        "','"+categoryQuantityObj.getBeverages()+"','"+categoryQuantityObj.getOther()+"','"+netTotal+"','"+profitObj.getProfit()+"')");
                            }else if (billDateInDatabase.equals("Yes")){
                                int newFoodItem = 0;
                                int newCleaningItem = 0;
                                int newPersonalCare = 0;
                                int newBeverages = 0;
                                int newOther = 0;
                                float newRevenue = 0;
                                float newProfit = 0;


                                resultSet = databaseStatement.executeQuery("SELECT * FROM DailySales WHERE Date='"+billDate+"'");
                                while (resultSet.next()){
                                    newFoodItem = resultSet.getInt("FoodItem")+categoryQuantityObj.getFoodItems();
                                    newCleaningItem = resultSet.getInt("CleaningItem")+ categoryQuantityObj.getCleaningItem();
                                    newPersonalCare = resultSet.getInt("PersonalCare")+ categoryQuantityObj.getPersonalCare();
                                    newBeverages = resultSet.getInt("Beverages")+categoryQuantityObj.getBeverages();
                                    newOther = resultSet.getInt("Other")+ categoryQuantityObj.getOther();
                                    newRevenue = resultSet.getFloat("Revenue")+netTotal;
                                    newProfit = resultSet.getFloat("Profit")+profitObj.getProfit();
                                }
                                databaseStatement.executeUpdate("UPDATE DailySales SET FoodItem='"+newFoodItem+"',CleaningItem='"+newCleaningItem+"',PersonalCare='"+newPersonalCare+
                                        "',Beverages='"+newBeverages+"',Other='"+newOther+"',Revenue='"+newRevenue+"',Profit='"+newProfit+
                                        "'WHERE Date='"+billDate+"'");
                            }
                        }
                    }catch (SQLException throwables){
                        throwables.printStackTrace();
                    }
                    try {
                        databaseStatement.executeUpdate("DELETE FROM TemporaryGoods");
                    }catch (SQLException throwables){
                        throwables.printStackTrace();
                    }


                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }


        });

        logoutLabel.setOnMouseClicked(e->{
            stage.close();
        });

        resetWindowLayout4.setOnAction(event-> {
            cashEntry.clear();
            //balanceEntry.setText("");
            categoryQuantityObj.setOther(-categoryQuantityObj.getOther());
            categoryQuantityObj.setBeverages(-categoryQuantityObj.getBeverages());
            categoryQuantityObj.setPersonalCare(-categoryQuantityObj.getPersonalCare());
            categoryQuantityObj.setFoodItems(-categoryQuantityObj.getFoodItems());
            categoryQuantityObj.setCleaningItem(-categoryQuantityObj.getCleaningItem());
            table.getItems().clear();
            float resetProfit = profitObj.getProfit();
            profitObj.setProfit(-(resetProfit));
            totalObj.setTotal(-(totalObj.getTotal()));
            subTotalEntry.setText("");
        });
       // System.out.println(tr);
        stage.setScene(scene);
        stage.setMaximized(true);
        scene.getStylesheets().add(BillingWindow.class.getResource("Main.css").toExternalForm());
        stage.setResizable(false);
        //stage.resizableProperty().setValue(true);
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();


    }

    //@Override
    // public void handle(KeyEvent keyEvent) {
    //System.out.println(keyEvent.getCode().toString());
}