package sample;

import java.sql.*;

public class TemporaryDatabaseOperations {
    public static String dbState = "";
    public static int databaseQuantity=0;
    public static int newQuantity=0;
    public static final String url = "jdbc:mysql://localhost/BILLINGDB";
    //public static final String user = "root";
    //public static final String password = "Avi990921pj";

    public void resetTemporaryDatabase(String user,String password) throws SQLException {
        Connection databaseConnection = DriverManager.getConnection(url,user,password);
        Statement databaseStatement = databaseConnection.createStatement();
        databaseStatement.executeUpdate("DELETE FROM TemporaryGoods");
    }public String checkTemporaryDatabaseAvailable(String user,String password) throws SQLException {
        Connection databaseConnection = DriverManager.getConnection(url,user,password);
        Statement databaseStatement = databaseConnection.createStatement();
        ResultSet resultSet = databaseStatement.executeQuery("SELECT * FROM TemporaryGoods");
        if (!(resultSet.next())){
            dbState = "false";
        }else {
            dbState = "true";
        }
        return dbState;

    }public void updateGoodsDatabase(String itemNumber,int temporaryQuantity,String user,String password) throws SQLException {
        Connection databaseConnection = DriverManager.getConnection(url,user,password);
        Statement databaseStatement = databaseConnection.createStatement();
        ResultSet resultSet = databaseStatement.executeQuery("SELECT Quantity FROM goods WHERE ItemNumber='"+itemNumber+"'");
        while (resultSet.next()){
            databaseQuantity = resultSet.getInt("Quantity");
        }
        newQuantity = databaseQuantity+temporaryQuantity;
        databaseStatement.executeUpdate("UPDATE goods SET Quantity='"+newQuantity+"' WHERE ItemNumber='"+itemNumber+"'");
    }
}
