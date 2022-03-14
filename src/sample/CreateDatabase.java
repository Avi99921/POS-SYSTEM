package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLClientInfoException;
import java.sql.Statement;
import java.sql.SQLException;

public class CreateDatabase {
    //public static final String user = "root";
    //public static final String password = "Avi990921pj";
    public static final String url = "jdbc:mysql://localhost";
    public static final String urlTwo = "jdbc:mysql://localhost/BILLINGDB";

    public static void createDatabase(String user,String password) throws SQLException{
        Connection databaseConnection = null;
        Statement databaseStatement = null;

        databaseConnection = DriverManager.getConnection(url,user,password);
        databaseStatement = databaseConnection.createStatement();
        String sqlStatement = "CREATE DATABASE BILLINGDB";
        databaseStatement.executeUpdate(sqlStatement);
        databaseStatement.close();
        databaseConnection.close();


        //Connection con = null;
       // Statement stmnt = null;
        databaseConnection = DriverManager.getConnection(urlTwo,user,password);
        databaseStatement = databaseConnection.createStatement();

        sqlStatement = "CREATE TABLE User ("
                +"Username VARCHAR(255),"
                +"Password VARCHAR(255))";
        databaseStatement.executeUpdate(sqlStatement);

        sqlStatement = "CREATE TABLE Goods("
                +"ItemNumber INT(255),"
                +"ItemName VARCHAR(255),"
                +"Category VARCHAR(255),"
                +"BuyingPrice FLOAT(20,2),"
                +"UnitPrice FLOAT(20,2),"
                +"DiscountPermission VARCHAR(255),"
                +"DiscountPrice FLOAT(20,2),"
                +"DiscountPolicy FLOAT(20,2),"
                +"Quantity INT(255))";
        databaseStatement.executeUpdate(sqlStatement);

        sqlStatement = "INSERT INTO User (Username,Password)"
                +" VALUES('admin','admin')";
        databaseStatement.executeUpdate(sqlStatement);

        sqlStatement = "CREATE TABLE DailyProfit("
                +"Number INT(255),"
                +"Date VARCHAR(255),"
                +"Profit FLOAT(20,2))";
        databaseStatement.executeUpdate(sqlStatement);

        sqlStatement = "CREATE TABLE ProfitIndex("
                + "Number INT(255),"
                + "TableName VARCHAR(255))";
        databaseStatement.executeUpdate(sqlStatement);

        sqlStatement = "CREATE TABLE TemporaryGoods("
                + "ItemNumber VARCHAR(255),"
                + "Quantity INT(255))";
        databaseStatement.executeUpdate(sqlStatement);

        sqlStatement = "CREATE TABLE DailySales("
                +"Date VARCHAR(255),"
                +"FoodItem INT(255),"
                +"CleaningItem INT(255),"
                +"PersonalCare INT(255),"
                +"Beverages INT(255),"
                +"Other INT(255),"
                +"Revenue FLOAT(20,2),"
                +"Profit FLOAT(20,2))";
        databaseStatement.executeUpdate(sqlStatement);
        databaseStatement.close();
        databaseConnection.close();

        databaseStatement.close();
        databaseConnection.close();
    }
}
