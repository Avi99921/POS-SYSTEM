package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigDatabase {
    private static void encryptPassword()
    {
    }
    public static void writeConfig(String userName, String password, String path) throws IOException {
        FileWriter obj = new FileWriter(path);
        obj.write(userName+"\n");
        obj.write(password);
        obj.close();
    }


    public static void configDatabaseWindow()
    {
        GridPane root = new GridPane();
        Label connName = new Label("Connection name");
        TextField connectionName = new TextField();
        Label connPswd = new Label("Connection password");
        TextField connectionPassword = new TextField();
        Button changeButton = new Button("Change");
        changeButton.setPrefWidth(180);
        Scene scene = new Scene(root,240,480);
        Stage stage = new Stage();
        scene.getStylesheets().add(ConfigDatabase.class.getResource("Main.css").toExternalForm());
        root.add(connName,0,0);
        root.add(connectionName,0,1);
        root.add(connPswd,0,2);
        root.add(connectionPassword,0,3);
        root.add(changeButton,0,4);
        root.setVgap(5);
        root.setAlignment(Pos.CENTER);
        stage.setTitle("Config");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);


        changeButton.setOnAction(e->
        {
            String userName = connectionName.getText();
            String userPassword = connectionPassword.getText();
            File textFile = new File("myfile.txt");
            connectionName.clear();
            connectionPassword.clear();

            try {
                if (textFile.createNewFile()){
                    System.out.println("OK");
                }else
                {
                    if (textFile.delete())
                    {
                        textFile = new File("myfile.txt");
                        textFile.createNewFile();
                    }
                    System.out.println("NOT OK");
                }
                String path = textFile.getPath();
                System.out.println(path);
                writeConfig(userName,userPassword,path);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            AlertBox.displayAlertBox("Success!","Updated Successfully");
            stage.close();



        });

        stage.showAndWait();






    }
}
