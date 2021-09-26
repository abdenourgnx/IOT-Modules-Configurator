package sample.controllers;

import com.sun.javafx.tk.Toolkit;
import javafx.application.Platform;
import javafx.concurrent.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Tools.Commander;
import sample.Tools.FileHandler;

import javax.comm.CommPortIdentifier;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Pattern;

public class homeController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;


    @FXML
    private Pane wifiPan;

    @FXML
    private Pane gsmPan;

    @FXML
    private Pane LoraPan;

    @FXML
    private Pane gpioPane;


    Stage stage;
    Parent root;
//    Service service;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {


//        Commander.runCommand("dir");


//        Commander.convertToHex("D:\\dev\\test");

        wifiPan.setOnMousePressed(mouseEvent -> {
            changeScene("../fxmls/wifiUi.fxml");
        });
        LoraPan.setOnMousePressed(mouseEvent -> {
            changeScene("../fxmls/loraUi.fxml");
        });
        gpioPane.setOnMousePressed(mouseEvent -> {
            changeScene("../fxmls/GpioUi.fxml");
        });
        gsmPan.setOnMousePressed(mouseEvent -> {
            changeScene("../fxmls/GSMUi.fxml");
        });



//        Service<Void> service = new Service<Void>() {
//            @Override
//            protected Task<Void> createTask() {
//                return new Task<Void>() {
//                    @Override
//                    protected Void call() throws Exception {
//
//                        final CountDownLatch latch = new CountDownLatch(1000);
//                        Platform.runLater(new Runnable() {
//                            Boolean state = true;
//                            @Override
//                            public void run() {
//                                try{
//
//                                        state = !state;
//                                        wifiPan.setVisible(state);
//                                        System.out.println("hello");
//
//
//
//                                }finally{
//                                    latch.countDown();
//                                }
//                            }
//                        });
//                        latch.await();
//                        //Keep with the background work
//                        return null;
//                    }
//                };
//            }
//        };
//        service.start();








//        List<String> list = new ArrayList<>();
//        Enumeration thePorts = CommPortIdentifier.getPortIdentifiers();
//        while (thePorts.hasMoreElements())
//        {
//            CommPortIdentifier com = (CommPortIdentifier) thePorts.nextElement();
//            com.getName();
//        }



//        Commander.linker("C:\\Users\\pc\\Desktop\\workspace\\Module Cofig\\inputs", null,null);
//        System.out.println(file.getAbsolutePath());



        /*String  hel =  FileHandler.read("inputs\\main.c");
        System.out.println(hel);

        String may = hel.replace("NETPASSWORD" , "ABDENOOUR");

        FileHandler.writeIntoFile(may , "inputs\\main.c");

        hel =  FileHandler.read("inputs\\main.c");
        System.out.println(hel);
*/






    }

    private void changeScene(String path){
        try {
            stage = (Stage) wifiPan.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource(path));

            mouvment(stage,root);

            stage.setScene(new Scene(root));
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void mouvment(Stage stage, Parent root) {

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        //move around here
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }



}
