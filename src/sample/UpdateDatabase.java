package sample;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.tools.Borders;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class UpdateDatabase {
    static String url = "jdbc:mysql://localhost/BILLINGDB";
    public static String checkDbItem;
    public static int quantity;

    public static void addItems(String user,String password) throws SQLException {
        Stage stage = new Stage();
        stage.setTitle("ADD ITEMS");
        GridPane root = new GridPane();
        GridPane addButtonLayout = new GridPane();
        GridPane addItemsLayout = new GridPane();
        GridPane addItemsLayout2 = new GridPane();

        //addItem.setTextAlignment(TextAlignment.JUSTIFY);
        Label itemNoAdd = new Label("Item No");
        Label itemNameAdd = new Label("Item Name");
        Label buyingPriceAdd = new Label("Buying Price");
        Label sellingPriceAdd = new Label("Selling Price");
        Label discountPermissionAdd = new Label("Discount Permission");
        Label discountPriceAdd = new Label("Discount Price");
        Label discountPolicyAdd = new Label("Discount Policy (Qty)");
        Label quantityAdd = new Label("Quantity");
        Label itemCategoryAdd = new Label("Category");



        TextField addItemNo = new TextField();
        TextField addItemName = new TextField();
        TextField addBuyingPrice = new TextField();
        TextField addSellingPrice = new TextField();

        ChoiceBox<String> addDiscountPermission = new ChoiceBox<>();
        addDiscountPermission.getItems().addAll("Yes","No");
        addDiscountPermission.setValue("Yes");
        addDiscountPermission.setMaxWidth(350);

        ChoiceBox<String> addItemCategory = new ChoiceBox<>();
        addItemCategory.getItems().addAll("Food Item","Cleaning Item","Personal Care","Beverages","Other");
        addItemCategory.setValue("Food Item");
        addItemCategory.setMaxWidth(350);

        //TextField addDiscountPermission = new TextField();
        TextField addDiscountPrice = new TextField();
        TextField addDiscountPolicy = new TextField();
        TextField addQuantity = new TextField();
        Button addButton = new Button("ADD");
        addButton.setMaxWidth(100);

        addItemsLayout.add(itemNoAdd,0,0,1,1);
        addItemsLayout.add(itemNameAdd,1,0,1,1);
        addItemsLayout.add(quantityAdd,0,2,1,1);
        addItemsLayout.add(itemCategoryAdd,1,2,1,1);

        addItemsLayout.add(addItemNo,0,1,1,1);
        addItemsLayout.add(addItemName,1,1,1,1);
        addItemsLayout.add(addQuantity,0,3,1,1);
        addItemsLayout.add(addItemCategory,1,3,1,1);


        Node wrapAddItemsLayout = Borders.wrap(addItemsLayout)
                .lineBorder()
                .title("Item Details")
                .color(Color.rgb(81,152,222))
                .build()
                .build();
        addItemsLayout2.add(buyingPriceAdd,0,0,1,1);
        addItemsLayout2.add(sellingPriceAdd,0,2,1,1);
        addItemsLayout2.add(discountPermissionAdd,1,0,1,1);
        addItemsLayout2.add(discountPriceAdd,1,2,1,1);
        addItemsLayout2.add(discountPolicyAdd,1,4,1,1);

        addItemsLayout2.add(addBuyingPrice,0,1,1,1);
        addItemsLayout2.add(addSellingPrice,0,3,1,1);
        addItemsLayout2.add(addDiscountPermission,1,1,1,1);
        addItemsLayout2.add(addDiscountPrice,1,3,1,1);
        addItemsLayout2.add(addDiscountPolicy,1,5,1,1);

        addButtonLayout.add(addButton,0,0);
        addButtonLayout.setAlignment(Pos.CENTER);

        Node wrapAddItemsLayout2 = Borders.wrap(addItemsLayout2)
                .lineBorder()
                .title("Pricing & Discount")
                .color(Color.rgb(81,152,222))
                .build()
                .build();

        addItemsLayout.setVgap(20);
        addItemsLayout.setHgap(5);
        addItemsLayout2.setVgap(20);
        addItemsLayout2.setHgap(5);
        root.add(wrapAddItemsLayout,0,0);
        root.add(wrapAddItemsLayout2,0,1);
        root.add(addButtonLayout,0,2);


        Scene scene = new Scene(root,360,565);
        scene.getStylesheets().add(UpdateDatabase.class.getResource("Main.css").toExternalForm());
        stage.setScene(scene);
        addButton.setOnAction(e->{
            Connection databaseConnection = null;
            Statement databaseStatement = null;

            if (addItemNo.getText().trim().isEmpty()){
                AlertBox.displayAlertBox("Error","Please fill required fields");
            }else if (addItemName.getText().trim().isEmpty()){
                AlertBox.displayAlertBox("Error","Please fill required fields");
            }else if (addSellingPrice.getText().trim().isEmpty()){
                AlertBox.displayAlertBox("Error","Please fill required fields");
            }else if (addQuantity.getText().trim().isEmpty()) {
                AlertBox.displayAlertBox("Error", "Please fill required fields");
            }else if (addBuyingPrice.getText().trim().isEmpty()){
                AlertBox.displayAlertBox("Error","Please fill required fields");
            }else if (addDiscountPrice.getText().trim().isEmpty()){
                AlertBox.displayAlertBox("Error","Please fill required fields");
            }else if (addDiscountPolicy.getText().trim().isEmpty()){
                AlertBox.displayAlertBox("Error","Please fill required fields");
            }else{
                String itemNo = addItemNo.getText();
                String itemName = addItemName.getText().toLowerCase();
                String buyingPrice = addBuyingPrice.getText();
                String unitPrice = addSellingPrice.getText();
                String quantity = addQuantity.getText();
                String discountPermission = addDiscountPermission.getValue();
                String category = addItemCategory.getValue();
                float discountPrice = Float.parseFloat(addDiscountPrice.getText());
                float discountPolicy = Float.parseFloat(addDiscountPolicy.getText());

                String checkItemSQL = "SELECT ItemNumber FROM goods";

                String sql = "INSERT INTO goods(ItemNumber,ItemName,Category,BuyingPrice,UnitPrice,DiscountPermission,DiscountPrice,DiscountPolicy,Quantity) VALUES('"+
                        itemNo+"','"+itemName+"','"+category+"','"+buyingPrice+"','"+unitPrice+"','"+discountPermission+"','"+discountPrice+"','"+discountPolicy+"','"+quantity+"')";
                try {
                    databaseConnection = DriverManager.getConnection(url,user,password);
                    databaseStatement = databaseConnection.createStatement();
                    ResultSet resultSet = databaseStatement.executeQuery("SELECT * FROM goods");

                    //String data = resultSet.getString("ItemNumber");
                    //System.out.println(data);
                    if (!resultSet.next()){
                        databaseStatement.execute(sql);
                        addItemNo.clear();
                        addItemName.clear();
                        addBuyingPrice.clear();
                        addSellingPrice.clear();
                        addQuantity.clear();
                        addDiscountPermission.setValue("Yes");
                        addDiscountPolicy.clear();
                        addDiscountPrice.clear();

                        databaseStatement.close();
                        databaseConnection.close();
                    }else{
                        resultSet = databaseStatement.executeQuery(checkItemSQL);
                        while (resultSet.next()){
                            System.out.println(resultSet.getString("ItemNumber"));
                            if ((resultSet.getString("ItemNumber").equals(itemNo))){
                                checkDbItem = "true";
                                break;
                            }else{
                                checkDbItem = "false";
                            }
                        }
                        if (checkDbItem.equals("false")){
                            databaseStatement.execute(sql);
                            addItemNo.clear();
                            addItemName.clear();
                            addBuyingPrice.clear();
                            addSellingPrice.clear();
                            addQuantity.clear();
                            addDiscountPermission.setValue("Yes");
                            addDiscountPolicy.clear();
                            addDiscountPrice.clear();

                            databaseStatement.close();
                            databaseConnection.close();
                        }else if (checkDbItem.equals("true")){
                            databaseStatement.close();
                            databaseConnection.close();
                            AlertBox.displayAlertBox("Error","Please check item number");
                        }
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        /*

        */
        stage.initModality(Modality.APPLICATION_MODAL);
        //stage.setMaximized(true);
        stage.setResizable(false);
        stage.showAndWait();
    }
    public static void deleteItems(String user,String password) throws SQLException{
        Stage stage = new Stage();
        GridPane deleteItemLayout = new GridPane();

        //Label itemNoDelete = new Label("Item No");
        TextField deleteItemNo = new TextField();
        deleteItemNo.setPromptText("Enter item number");
        deleteItemNo.setFocusTraversable(false);
        Button deleteButton = new Button("DELETE");

        //deleteItemLayout.add(itemNoDelete,0,4,5,1);
        deleteItemLayout.add(deleteItemNo,0,0,2,1);
        deleteItemLayout.add(deleteButton,2,0,1,1);
        deleteItemLayout.setAlignment(Pos.CENTER);
        deleteItemLayout.setHgap(5);

        deleteButton.setOnAction(e->{
            Connection databaseConnection = null;
            Statement databaseStatement = null;

            if (deleteItemNo.getText().trim().isEmpty()){
                AlertBox.displayAlertBox("Error","Please fill required fields");
            }else{
                String deleteItemNumber = deleteItemNo.getText();
                String checkDeleteItemNoSql = "SELECT * FROM goods WHERE ItemNumber='"+deleteItemNumber+"'";
                try {
                    databaseConnection = DriverManager.getConnection(url,user,password);
                    databaseStatement = databaseConnection.createStatement();
                    ResultSet resultSet = databaseStatement.executeQuery(checkDeleteItemNoSql);
                    if (!resultSet.next()){
                        databaseStatement.close();
                        databaseConnection.close();
                        deleteItemNo.clear();
                        AlertBox.displayAlertBox("Error","Item not in database");
                    }else {
                        String sql = "DELETE FROM goods WHERE ItemNumber='"+deleteItemNumber+"'";
                        databaseStatement.execute(sql);
                        deleteItemNo.clear();
                        databaseStatement.close();
                        databaseConnection.close();
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        });


        stage.setTitle("Delete item");
        Scene scene = new Scene(deleteItemLayout,360,240);
        scene.getStylesheets().add(UpdateDatabase.class.getResource("Main.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();


    }
    public static void updateItems(String user,String password)throws SQLException{
        Stage stage = new Stage();
        GridPane updateButtonLayout = new GridPane();
        GridPane updateItemsLayout = new GridPane();
        GridPane itemDetailsLayout = new GridPane();
        GridPane itemPricingLayout = new GridPane();

        Label itemNoUpdate = new Label("Item no");
        Label buyingPriceUpdate = new Label("Buying Price");
        Label unitPriceUpdate = new Label("Unit Price");
        Label quantityUpdate = new Label("Quantity");

        TextField updateItemNo = new TextField();
        TextField updateBuyingPrice = new TextField();
        TextField updateUnitPrice = new TextField();
        TextField updateQuantity = new TextField();

        Button updateButton = new Button("UPDATE");

        itemDetailsLayout.add(itemNoUpdate,0,0);
        itemDetailsLayout.add(quantityUpdate,1,0);
        itemDetailsLayout.add(updateItemNo,0,1);
        itemDetailsLayout.add(updateQuantity,1,1);
        itemDetailsLayout.setHgap(5);

        itemPricingLayout.add(buyingPriceUpdate,0,0);
        itemPricingLayout.add(unitPriceUpdate,1,0);
        itemPricingLayout.add(updateBuyingPrice,0,1);
        itemPricingLayout.add(updateUnitPrice,1,1);
        itemPricingLayout.setHgap(5);

        Node wrapItemDetailsLayout = Borders.wrap(itemDetailsLayout)
                .lineBorder()
                .title("Item Details")
                .color(Color.rgb(81,152,222))
                .build()
                .build();
        Node wrapItemPricingLayout = Borders.wrap(itemPricingLayout)
                .lineBorder()
                .title("Item Pricing")
                .color(Color.rgb(81,152,222))
                .build()
                .build();
        updateButtonLayout.add(updateButton,0,0);
        updateButtonLayout.setAlignment(Pos.CENTER);

        updateItemsLayout.add(wrapItemDetailsLayout,0,0);
        updateItemsLayout.add(wrapItemPricingLayout,0,1);
        updateItemsLayout.add(updateButtonLayout,0,2);
        updateItemsLayout.setAlignment(Pos.CENTER);


        updateButton.setOnAction(e->{
            String updateItemNumber = updateItemNo.getText();
            String updateItemUnitPrice = updateUnitPrice.getText();
            String updateItemQuantity = updateQuantity.getText();
            String updateItemBuyingPrice = updateBuyingPrice.getText();

            Connection databaseConnection = null;
            Statement databaseStatement = null;

            try {
                databaseConnection = DriverManager.getConnection(url,user,password);
                databaseStatement = databaseConnection.createStatement();

                if (!(updateItemNo.getText().trim().isEmpty())){
                    ResultSet resultSet = databaseStatement.executeQuery("SELECT Quantity From goods WHERE ItemNumber='"+updateItemNumber+"'");
                    if (!(resultSet.next())){
                        AlertBox.displayAlertBox("Error","Wrong item number");
                    }else {
                        String databaseQuantity = resultSet.getString("Quantity");
                        quantity = Integer.parseInt(databaseQuantity);

                        if (!(updateUnitPrice.getText().trim().isEmpty())) {
                            if (!(updateQuantity.getText().trim().isEmpty())) {
                                int newQuantity;
                                if (updateQuantity.getText().equals("0")){
                                    newQuantity = 0;
                                }else{
                                    newQuantity = quantity + Integer.parseInt(updateItemQuantity);
                                }

                                if (!(updateBuyingPrice.getText().trim().isEmpty())){
                                    databaseStatement.executeUpdate("UPDATE goods SET BuyingPrice="+"'"+Float.parseFloat(updateItemBuyingPrice)+"',"+
                                            "UnitPrice="+"'" + Float.parseFloat(updateItemUnitPrice)  + "'," +
                                            "Quantity='" + newQuantity + "'" +
                                            "WHERE ItemNumber='" + updateItemNumber + "'");
                                }else{
                                    databaseStatement.executeUpdate("UPDATE goods SET UnitPrice='" + Float.parseFloat(updateItemUnitPrice)  + "'," +
                                            "Quantity='" + newQuantity + "'" +
                                            "WHERE ItemNumber='" + updateItemNumber + "'");
                                }
                            }else if (!(updateBuyingPrice.getText().trim().isEmpty())){
                                databaseStatement.executeUpdate("UPDATE goods SET BuyingPrice='"+Float.parseFloat(updateItemBuyingPrice)+"',"+
                                        "UnitPrice='" + Float.parseFloat(updateItemUnitPrice)  + "'" +
                                        "WHERE ItemNumber='" + updateItemNumber + "'");
                            }
                            else {
                                databaseStatement.executeUpdate("UPDATE goods SET UnitPrice='" + updateItemUnitPrice +
                                        "'WHERE ItemNumber='" + updateItemNumber + "'");
                            }
                        }else if (!(updateBuyingPrice.getText().trim().isEmpty())){
                            if (!(updateQuantity.getText().trim().isEmpty())){
                                if (updateItemQuantity.trim().equals("0")){
                                    databaseStatement.executeUpdate("UPDATE goods SET BuyingPrice="+"'"+Float.parseFloat(updateItemBuyingPrice)+"',"+
                                            "Quantity='0'" +
                                            "WHERE ItemNumber='" + updateItemNumber + "'");
                                }else {
                                    int newQuantity = quantity + Integer.parseInt(updateItemQuantity);
                                    databaseStatement.executeUpdate("UPDATE goods SET BuyingPrice=" + "'" + Float.parseFloat(updateItemBuyingPrice) + "'," +
                                            "Quantity='" + newQuantity + "'" +
                                            "WHERE ItemNumber='" + updateItemNumber + "'");
                                }
                            }else {
                                databaseStatement.executeUpdate("UPDATE goods SET BuyingPrice='" + Float.parseFloat(updateItemBuyingPrice) +
                                        "'" + "WHERE ItemNumber='" + updateItemNumber + "'");
                            }

                        }
                        else if (!(updateQuantity.getText().trim().isEmpty())) {
                            if (updateItemQuantity.trim().equals("0")) {
                                databaseStatement.executeUpdate("UPDATE goods SET Quantity='0' WHERE ItemNumber='" + updateItemNumber + "'");
                            }else {
                                int newQuantity = quantity + Integer.parseInt(updateItemQuantity);
                                databaseStatement.executeUpdate("UPDATE goods SET Quantity='" + newQuantity + "'" +
                                        "WHERE ItemNumber='" + updateItemNumber + "'");
                            }

                        }else{
                            AlertBox.displayAlertBox("Error","Fill required fields");
                        }

                    }
                }
                updateItemNo.clear();
                updateQuantity.clear();
                updateUnitPrice.clear();
                databaseStatement.close();
                databaseConnection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        Scene scene = new Scene(updateItemsLayout,360,420);
        scene.getStylesheets().add(UpdateDatabase.class.getResource("Main.css").toExternalForm());
        stage.setTitle("Update Items");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();

    }
}
