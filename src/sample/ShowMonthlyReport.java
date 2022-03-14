package sample;

import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShowMonthlyReport {
    public static final String url = "jdbc:mysql://localhost/BILLINGDB";
    //public static final String user = "root";
    //public static final String password = "Avi990921pj";
    public static void showMonthlyReport(String user,String password) throws SQLException{
        Stage stage = new Stage();
        GridPane root = new GridPane();
        GridPane choiceBoxLayout = new GridPane();
        Label yearLabel = new Label("Year");
        Label monthLabel = new Label("Month");
        Button showButton = new Button("Show");

        ChoiceBox<Integer> yearSelectionBox = new ChoiceBox<>();
        ChoiceBox<Integer> monthSelectionBox = new ChoiceBox<>();
        monthSelectionBox.setDisable(true);
        showButton.setDisable(true);

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        AreaChart<String,Number> areaChart = new AreaChart(xAxis,yAxis);

       // areaChart.setAnimated(false);
        //series1.setName("Name");
        yearSelectionBox.setOnAction(event->{
            int year = yearSelectionBox.getValue();
            monthSelectionBox.setDisable(false);
            showButton.setDisable(true);
            try {
                generateMonthSelection(monthSelectionBox,year,user,password);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });

        monthSelectionBox.setOnAction(event->{
            if (!(monthSelectionBox.getValue()==null)){
                int month = monthSelectionBox.getValue();
                showButton.setDisable(false);
                System.out.println(month);
                //System.out.println("Month is "+month);
            }
        });
        showButton.setOnMouseClicked(event->{
            System.out.println(areaChart.getData());
            if (!(areaChart.getData().isEmpty())){
                areaChart.getData().remove(1);
                areaChart.getData().remove(0);
            }
            String yearMonth = yearSelectionBox.getValue()+"-"+monthSelectionBox.getValue();
            XYChart.Series series1 = new XYChart.Series();
            XYChart.Series series2 = new XYChart.Series();

            try {
                String yearAndMonth = "";
                float revenue = 0;
                float profit = 0;
                Connection databaseConnection = DriverManager.getConnection(url,user,password);
                Statement databaseStatement = databaseConnection.createStatement();
                ResultSet resultSet = databaseStatement.executeQuery("SELECT * FROM DailySales");
                while (resultSet.next()){
                    yearAndMonth = resultSet.getString("Date");
                    revenue = resultSet.getFloat("Revenue");
                    profit = resultSet.getFloat("Profit");
                    if (yearAndMonth.substring(0,7).equals(yearMonth)){
                        series1.getData().add(new XYChart.Data(yearAndMonth.substring(8),revenue));
                        series2.getData().add(new XYChart.Data(yearAndMonth.substring(8),profit));
                    }
                }
                areaChart.getData().addAll(series1,series2);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }



            //areaChart.getData().addAll(series1, series2);


        });



        choiceBoxLayout.add(yearLabel,0,0);
        choiceBoxLayout.add(generateYearBox(yearSelectionBox,user,password),1,0);
        choiceBoxLayout.add(monthLabel,2,0);
        choiceBoxLayout.add(monthSelectionBox,3,0);
        choiceBoxLayout.add(showButton,4,0);

        choiceBoxLayout.setHgap(8);
        root.add(choiceBoxLayout,0,0);
        root.add(areaChart,0,1);

        Scene scene = new Scene(root,480,480);
        stage.setScene(scene);
        stage.showAndWait();

    }
    public static ChoiceBox generateYearBox(ChoiceBox yearSelection,String user,String password)throws SQLException {
        Connection databaseConnetion = DriverManager.getConnection(url,user,password);
        Statement databaseStatement = databaseConnetion.createStatement();
        ResultSet resultSet = databaseStatement.executeQuery("SELECT Date FROM DailySales");
        List<Integer> yearList = new ArrayList<>();
        while (resultSet.next()){
            int salesYear = Integer.parseInt(resultSet.getString("Date").substring(0,4));
            if(!(yearList.contains(salesYear))){
                yearList.add(salesYear);
            }
            //yearSelection.getItems().add(salesYear);
            //yearSelection.setValue(resultSet.getString("Date"));
        }
        //System.out.println("YEARS======="+yearList);
        Collections.sort(yearList);
        for (int count=0;count<yearList.size();count++){
            yearSelection.getItems().add(yearList.get(count));
        }
        //yearSelection.setValue(yearList.get(0));
        databaseStatement.close();
        databaseConnetion.close();
        return yearSelection;
    }
    public static ChoiceBox generateMonthSelection(ChoiceBox monthSelectionBox,int year,String user,String password)throws SQLException{
        monthSelectionBox.getSelectionModel().clearSelection();
        monthSelectionBox.getItems().clear();
        List<Integer> monthList = new ArrayList<>();
        Connection databaseConnetion = DriverManager.getConnection(url,user,password);
        Statement databaseStatement = databaseConnetion.createStatement();
        ResultSet resultSet = databaseStatement.executeQuery("SELECT Date FROM DailySales");
        while (resultSet.next()){
            int salesMonth = Integer.parseInt(resultSet.getString("Date").substring(5,7));
            if (year==Integer.parseInt(resultSet.getString("Date").substring(0,4))){
                if (!(monthList.contains(salesMonth))){
                    monthList.add(salesMonth);
                }
            }
        }
        Collections.sort(monthList);
        for (int count=0;count<monthList.size();count++){
            monthSelectionBox.getItems().add(monthList.get(count));
        }
        //monthSelectionBox.setValue(monthList.get(0));
        databaseStatement.close();
        databaseConnetion.close();
        return monthSelectionBox;
    }
    public static XYChart.Series generateRevenueProfitChart(XYChart.Series series, String yearMonth, String columnName,String user,String password) throws SQLException{
        String yearAndMonth = "";
        float data = 0;
        Connection databaseConnetion = DriverManager.getConnection(url,user,password);
        Statement databaseStatement = databaseConnetion.createStatement();
        XYChart.Series chartSeries = new XYChart.Series();
        ResultSet resultSet = databaseStatement.executeQuery("SELECT * FROM DailySales");
        while (resultSet.next()){
            yearAndMonth = resultSet.getString("Date");
            data = resultSet.getFloat(columnName);
            if (yearAndMonth.substring(0,7).equals(yearMonth)){
                chartSeries.getData().add(new XYChart.Data(yearAndMonth.substring(8),data));
            }
        }
        return chartSeries;
    }
}
