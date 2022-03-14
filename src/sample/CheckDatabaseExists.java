package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckDatabaseExists{
    public static String dbExist;
    public static String dName = "BILLINGDB";
    public static boolean dbState;
    //public static final String uName = "root";
    //public static final String uPassword = "Avi990921pj";
    public static final String url = "jdbc:mysql://localhost";
    public static boolean checkDatabase(String user,String password) throws SQLException {
        Connection con = null;
        con = DriverManager.getConnection(url,user,password);
        ResultSet resultSet = con.getMetaData().getCatalogs();

        while (resultSet.next()){
            String dbname = resultSet.getString(1).toUpperCase();
            if (dbname.equals(dName)){
                dbState = true;
                break;
            }else {
                dbState = false;
            }
        }
        if (con!=null){
            con.close();
        }
        return dbState;
    }

}
