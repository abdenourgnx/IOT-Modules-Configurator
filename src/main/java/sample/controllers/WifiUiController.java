package sample.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.CubicCurve;
import javafx.stage.Stage;
import sample.Tools.Commander;
import sample.Tools.FileHandler;

import java.net.URL;
import java.util.ResourceBundle;


public class WifiUiController implements Initializable {


    String clientWifiMainDirIN="";
    String clientWifiMainDirOUT="";
    String hotWifiMainDirIN="";
    String hotWifiMainDirOUT="";

    @FXML
    private RadioButton clientBox;

    @FXML
    private RadioButton hotspotBox;

    @FXML
    private TextField accesPointField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField nodeNameField;

    @FXML
    private Label warningsLabel;

    @FXML
    private ChoiceBox<?> PortList;

    @FXML
    private Button compileBtn;

    @FXML
    private Button uploadBtn;

    @FXML
    private AnchorPane a1;

    @FXML
    private AnchorPane a2;

    @FXML
    private AnchorPane a3;

    @FXML
    private AnchorPane a4;

    @FXML
    private HBox a5;

    @FXML
    private CubicCurve s1;

    @FXML
    private CubicCurve s2;

    @FXML
    private CubicCurve s3;

    @FXML
    private CubicCurve s4;

    @FXML
    private CubicCurve s5;

    @FXML
    private Label t1;

    @FXML
    private Label t2;

    @FXML
    private Label t3;

    @FXML
    private Label t4;

    @FXML
    private Label t5;

    @FXML
    private ImageView loading;


    ToggleGroup deviceChoice;

    Thread inThread;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDetection();
        hideAll();

        warningsLabel.setVisible(false);

        loading.setVisible(false);


        deviceChoice = new ToggleGroup();
        hotspotBox.setToggleGroup(deviceChoice);
        clientBox.setToggleGroup(deviceChoice);

        hotspotBox.setOnAction(e->{
            hotspotBox.setStyle("-fx-text-fill: #2f89fc");
            clientBox.setStyle("-fx-text-fill: black");

        });
        clientBox.setOnAction(e->{
            clientBox.setStyle("-fx-text-fill: #2f89fc");
            hotspotBox.setStyle("-fx-text-fill: black");
        });

        compileBtn.setOnAction(e->compileClicked());


        inThread = infoThread();
        inThread.start();

    }

    private void compileClicked(){
        Thread t ;

        if(deviceChoice.getSelectedToggle() == null ||
                passwordField.getText().isEmpty() ||
                accesPointField.getText().isEmpty() ||
                nodeNameField.getText().isEmpty()
                ){
            warningsLabel.setVisible(true);
            warningsLabel.setText("Check every field is filled or the Device type choice");

        }else {



            t= new Thread() {
                public void run() {

                    try {

                        Platform.runLater(new Runnable() {
                            public void run() {


                                loading.setVisible(true);
                                warningsLabel.setVisible(true);
                                warningsLabel.setText("Compiling your configuration ...");
                                warningsLabel.setStyle("-fx-background-color: color3");
//                                    compile();

                            }
                        });

                        Thread.sleep(5000);

                        Platform.runLater(new Runnable() {
                            public void run() {
                                warningsLabel.setText("Compillation is done");
                                loading.setVisible(false);
                            }
                        });

                        Thread.sleep(4000);

                        Platform.runLater(new Runnable() {
                            public void run() {
                                warningsLabel.setVisible(false);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            };


            t.start();
        }
    }

    private void compile(){
        if(deviceChoice.getSelectedToggle() == clientBox){
            String code = FileHandler.read(clientWifiMainDirIN);
            code = code.replace("SSID",accesPointField.getText());
            code = code.replace("PASSWORD",passwordField.getText());
            code = code.replace("NODENAME",nodeNameField.getText());

            FileHandler.writeIntoFile(code , clientWifiMainDirOUT);
            Commander.compileWifi();
        }else {
            String code = FileHandler.read(hotWifiMainDirIN);
            code = code.replace("SSID",accesPointField.getText());
            code = code.replace("PASSWORD",passwordField.getText());
            code = code.replace("NODENAME",nodeNameField.getText());

            FileHandler.writeIntoFile(code , hotWifiMainDirOUT);
            Commander.compileWifi();
        }

    }

    private Thread infoThread(){
        return new Thread() {

            Boolean t = true;
            CubicCurve[] c = {s1,s2,s3,s4,s5};
            Label[] l ={t1,t2,t3,t4,t5};


            int i =0;

            public void run() {
                while(true) {
                    try {
                        if(i==4) i=0 ;
                        i++;
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    Platform.runLater(new Runnable() {
                        public void run() {

                            hideAll();
                            c[i].setVisible(true);
                            l[i].setVisible(true);


                        }
                    });
                }
            }
        };
    }

    private void hideAll(){
        CubicCurve[] c = {s1,s2,s3,s4,s5};
        Label[] l ={t1,t2,t3,t4,t5};


        for (CubicCurve cubicCurve : c) {
            cubicCurve.setVisible(false);
        }
        for (Label cubicCurve : l) {
            cubicCurve.setVisible(false);
        }
    }

    void setDetection(){
        AnchorPane[] h ={a1 ,a2,a3,a4};

        CubicCurve[] c = {s1,s2,s3,s4,s5};
        Label[] l ={t1,t2,t3,t4,t5};

        for (int i = 0; i < h.length; i++) {

            int finalI = i;
            h[i].setOnMouseEntered(e->{



                hideAll();
                c[finalI].setVisible(true);
                l[finalI].setVisible(true);

            });

            h[i].setOnMouseExited(e->{

                hideAll();
                c[finalI].setVisible(false);
                l[finalI].setVisible(false);
            });

            a5.setOnMouseEntered(e->{



                hideAll();
                c[4].setVisible(true);
                l[4].setVisible(true);

            });

            a5.setOnMouseExited(e->{


                hideAll();
                c[4].setVisible(false);
                l[4].setVisible(false);

            });
        }

    }

    @FXML
    void close(MouseEvent event) {
        Stage stage = (Stage) warningsLabel.getScene().getWindow();


        stage.close();

    }
}
