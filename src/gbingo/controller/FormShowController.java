/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gbingo.controller;

import gbingo.base.Bingo;
import gbingo.base.LTranDet;
import gbingo.base.ScreenInterface;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.rmj.appdriver.GRider;

/**
 * FXML Controller class
 *
 * @author User
 */
public class FormShowController implements Initializable, ScreenInterface {

    private GRider oApp;
    private Timeline timeline;
    private Integer timeSeconds = 5;
    private boolean running = false;
    /**
     * Initializes the controller class.
     */
    public String numValue = "";
    @FXML
    private Label lblNumber;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        timeline = new Timeline();
        lblNumber.setText(numValue);
        if(!running){
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(1), (ActionEvent event1) -> {
                        timeSeconds--;
                        // update timerLabel
                        if(timeSeconds <= 0){
                           timeSeconds = 0 ; 
                        }
                        if (timeSeconds == 0) {
                            getStage().close();
                        }
                } // KeyFrame event handler
            ));
            timeline.playFromStart();
        }
        
    }    
    @Override
    public void setGRider(GRider foValue) {
        oApp = foValue;
        
    }
    public void setNumber(String fsValue){
        numValue = fsValue;
    }
    private Stage getStage(){
	return (Stage) lblNumber.getScene().getWindow();
    }
}
