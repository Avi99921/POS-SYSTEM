package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteDatabase {
    //public static final String user = "root";
    //public static final String password = "Avi990921pj";
    public static final String urlTwo = "jdbc:mysql://localhost/BILLINGDB";
    public static void deleteDatabase(String user,String password)throws SQLException {
        Connection connection = null;
        Statement statement = null;
        connection = DriverManager.getConnection(urlTwo,user,password);
        statement = connection.createStatement();
        String sqlStatement = "DROP DATABASE BILLINGDB";
        statement.executeUpdate(sqlStatement);
        statement.close();
        connection.close();
    }
}
