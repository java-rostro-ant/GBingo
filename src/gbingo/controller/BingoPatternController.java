/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gbingo.controller;

import gbingo.base.Bingo;
import gbingo.base.LTranDet;
import gbingo.base.ScreenInterface;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.agentfx.CommonUtils;
import org.rmj.appdriver.agentfx.ShowMessageFX;
import org.rmj.appdriver.constants.EditMode;

/**
 * FXML Controller class
 *
 * @author User
 */
public class BingoPatternController implements Initializable, ScreenInterface {

    private GRider oApp;
    private Bingo oTrans;
    private LTranDet oListener;
    @FXML
    private Pane btnClose;
    @FXML
    private Button btnLoad;
    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField txtField01;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        oListener = new LTranDet() {
            @Override
            public void MasterRetreive(int fnIndex, Object foValue) {
              
                switch(fnIndex){
                    case 1:
                        break;
                }
            }

            @Override
            public void DetailRetreive(int fnRow, int fnIndex, Object foValue) {
            }
        };
        
        oTrans.setListener(oListener);
        
        btnLoad.setOnAction(this::cmdButton_Click);
        txtField01.setOnKeyPressed(this::txtField_KeyPressed);
    }    
    @Override
    public void setGRider(GRider foValue) {
        oApp = foValue;
    }
    public void setTrans(Bingo foValue) {
        oTrans = foValue;
    }
    
    private Stage getStage(){
	return (Stage) txtField01.getScene().getWindow();
    }
    
    @FXML
    private void handleButtonCloseClick(MouseEvent event) {
        MainController.setFrmState(false);
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    
    private void cmdButton_Click(ActionEvent event) {
        String lsButton = ((Button)event.getSource()).getId();
        switch (lsButton){
            case "btnLoad":
                if(!txtField01.getText().isEmpty()){
                    if(oTrans.getEditMode() == EditMode.ADDNEW){
                        MainController.setFrmState(true);
                        CommonUtils.closeStage(btnLoad);
                    }
                }else{
                    ShowMessageFX.Warning(getStage(), "Select Bingo Pattern first!!!","Warning", null);
                }
                break;

                    
        }
       
    } 
    private void txtField_KeyPressed(KeyEvent event){
        TextField txtField = (TextField)event.getSource();
        int lnIndex = Integer.parseInt(((TextField)event.getSource()).getId().substring(8,10));
        
        switch (event.getCode()){
            case F3:
            case ENTER:
                switch (lnIndex){
                    case 01:
                        {
                            try {
                                if(oTrans.SearchPattern(txtField.getText(), false)){ 
                                    System.out.println("sDescipt = " + (String) oTrans.getMaster("sDescript"));
                                    txtField.setText((String) oTrans.getMaster("sDescript")); 
                                }else{
                                    ShowMessageFX.Warning(getStage(), oTrans.getMessage(),"Warning", null);
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        event.consume();
                        return;

                }
                break;
            }
    }
    
}
