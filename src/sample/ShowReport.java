package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.*;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShowReport {
    public static final String url = "jdbc:mysql://localhost/BILLINGDB";
    //public static final String user = "root";
    //public static final String password = "Avi990921pj";

    public static void showDailyReport(String user,String password)throws SQLException {
        Stage stage = new Stage();
        GridPane root = new GridPane();
        GridPane choiceBoxLayout = new GridPane();
        Label yearLabel = new Label("Year");
        Label monthLabel = new Label("Month");
        Label dayLabel = new Label("Day");
        Button showButton = new Button("Show");

        ChoiceBox<Integer> yearSelectionBox = new ChoiceBox<>();
        ChoiceBox<Integer> monthSelectionBox = new ChoiceBox<>();
        monthSelectionBox.setDisable(true);
        ChoiceBox<Integer> dateSelectionBox = new ChoiceBox<>();
        dateSelectionBox.setDisable(true);
        showButton.setDisable(true);

        PieChart pieChart = new PieChart();

        yearSelectionBox.setOnAction(event->{
            int year = yearSelectionBox.getValue();
            monthSelectionBox.setDisable(false);
            dateSelectionBox.setDisable(true);
            showButton.setDisable(true);
            try {
                generateMonthSelection(monthSelectionBox,year,user,password);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });

        monthSelectionBox.setOnAction(event->{
            dateSelectionBox.getItems().clear();
           // System.out.println(monthSelectionBox.getValue());
            if (!(monthSelectionBox.getValue()==null)){
                int month = monthSelectionBox.getValue();
                dateSelectionBox.setDisable(false);
                showButton.setDisable(true);
                try {
                    generateDateSelection(dateSelectionBox,yearSelectionBox.getValue(),monthSelectionBox.getValue(),user,password);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                //System.out.println("Month is "+month);
            }
        });
        dateSelectionBox.setOnAction(event->{
            if (!(dateSelectionBox.getValue()==null)){
                showButton.setDisable(false);
            }
        });

        showButton.setOnMouseClicked(event->{
            String date = yearSelectionBox.getValue()+"-"+monthSelectionBox.getValue()+"-"+dateSelectionBox.getValue();
            try {
                pieChart.getData().clear();
                pieChart.getData().addAll(getPieChartData(date,user,password));
                pieChart.setLegendVisible(false);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });


        choiceBoxLayout.add(yearLabel,0,0);
        choiceBoxLayout.add(generateYearBox(yearSelectionBox,user,password),1,0);
        choiceBoxLayout.add(monthLabel,2,0);
        choiceBoxLayout.add(monthSelectionBox,3,0);
        choiceBoxLayout.add(dayLabel,4,0);
        choiceBoxLayout.add(dateSelectionBox,5,0);
        choiceBoxLayout.add(showButton,6,0);

        choiceBoxLayout.setHgap(8);


        root.add(choiceBoxLayout,0,0);
        root.add(pieChart,0,1);
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(ShowReport.class.getResource("Main.css").toExternalForm());

        stage.setScene(scene);
        stage.showAndWait();
    }
    public static ChoiceBox generateYearBox(ChoiceBox yearSelection,String user,String password)throws SQLException{
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
    public static ChoiceBox generateDateSelection(ChoiceBox dateSelectionBox,int year,int month,String user,String password)throws SQLException{
        dateSelectionBox.getSelectionModel().clearSelection();
        dateSelectionBox.getItems().clear();
        Connection databaseConnetion = DriverManager.getConnection(url,user,password);
        Statement databaseStatement = databaseConnetion.createStatement();
        ResultSet resultSet = databaseStatement.executeQuery("SELECT Date FROM DailySales");
        List<Integer> dayList = new ArrayList<>();
        while (resultSet.next()){
            int salesDate = Integer.parseInt(resultSet.getString("Date").substring(8));
            if (year==Integer.parseInt(resultSet.getString("Date").substring(0,4))){
                if (month==Integer.parseInt(resultSet.getString("Date").substring(5,7))){
                    if (!(dayList.contains(salesDate))){
                        dayList.add(salesDate);
                    }
                }
            }
        }
        Collections.sort(dayList);
        for (int count=0;count<dayList.size();count++){
            dateSelectionBox.getItems().add(dayList.get(count));
        }
        databaseStatement.close();
        databaseConnetion.close();
        return dateSelectionBox;

    }
    public static ObservableList getPieChartData(String date,String user,String password)throws SQLException{
        Connection databaseConnection = DriverManager.getConnection(url,user,password);
        Statement databaseStatement = databaseConnection.createStatement();
        ObservableList data = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = databaseStatement.executeQuery("SELECT * FROM DailySales WHERE Date='"+date+"'");
            //ResultSet resultSet = databaseStatement.executeQuery("SELECT * FROM DailySales WHERE Date='2015-10-01'");
            while (resultSet.next()){
                System.out.println(resultSet.getInt("FoodItem"));
                System.out.println(resultSet.getInt("CleaningItem"));
                System.out.println(resultSet.getInt("PersonalCare"));
                System.out.println(resultSet.getInt("Beverages"));
                System.out.println(resultSet.getInt("Other"));



                if (resultSet.getInt("FoodItem")>0){
                    data.add(new PieChart.Data("Food Items ("+resultSet.getInt("FoodItem")+")",resultSet.getInt("FoodItem")));
                }
                if (resultSet.getInt("CleaningItem")>0){
                    data.add(new PieChart.Data("Cleaning Items ("+resultSet.getInt("CleaningItem")+")",resultSet.getInt("CleaningItem")));
                }
                if (resultSet.getInt("PersonalCare")>0){
                    data.add(new PieChart.Data("Personal Care ("+resultSet.getInt("PersonalCare")+")",resultSet.getInt("PersonalCare")));
                }

                if (resultSet.getInt("Beverages")>0){
                    data.add(new PieChart.Data("Beverages ("+resultSet.getInt("Beverages")+")",resultSet.getInt("Beverages")));
                }
                if (resultSet.getInt("Other")>0){
                    data.add(new PieChart.Data("Other ("+resultSet.getInt("Other")+")",resultSet.getInt("Other")));
                }

            }
        }catch (SQLException throwables){
            throwables.printStackTrace();

        }
        System.out.println(data.size());

        return data;
    }

}
