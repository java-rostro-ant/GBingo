/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gbingo.controller;

import gbingo.base.Bingo;
import gbingo.base.LTranDet;
import gbingo.base.ScreenInterface;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.agentfx.ShowMessageFX;
import org.rmj.appdriver.constants.EditMode;

/**
 * FXML Controller class
 *
 * @author User
 */
public class GBingoController implements Initializable, ScreenInterface {

    private final String pxeModuleName = "E - BINGO";
    private GRider oApp;
    private Bingo oTrans;
    private LTranDet oListener;
    private int pnNewNo,pnOldNo;
    private String psTransNox;
    private int pnEditMode;
    public static boolean frmState = false;
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private AnchorPane num01,num02,num03,num04,num05,num06,num07,num08,num09,num10,
            num11,num12,num13,num14,num15,num16,num17,num18,num19,num20,
            num21,num22,num23,num24,num25,num26,num27,num28,num29,num30,
            num31,num32,num33,num34,num35,num36,num37,num38,num39,num40,
            num41,num42,num43,num44,num45,num46,num47,num48,num49,num50,
            num51,num52,num53,num54,num55,num56,num57,num58,num59,num60,
            num61,num62,num63,num64,num65,num66,num67,num68,num69,num70,
            num71,num72,num73,num74,num75,AnchorParent, anchoBody;
    
    @FXML
    private Label lbl01,lbl02,lbl03,lbl04,lbl05,lbl06,lbl07,lbl08,lbl09,lbl10,
            lbl11,lbl12,lbl13,lbl14,lbl15,lbl16,lbl17,lbl18,lbl19,lbl20,
            lbl21,lbl22,lbl23,lbl24,lbl25,lbl26,lbl27,lbl28,lbl29,lbl30,
            lbl31,lbl32,lbl33,lbl34,lbl35,lbl36,lbl37,lbl38,lbl39,lbl40,
            lbl41,lbl42,lbl43,lbl44,lbl45,lbl46,lbl47,lbl48,lbl49,lbl50,
            lbl51,lbl52,lbl53,lbl54,lbl55,lbl56,lbl57,lbl58,lbl59,lbl60,
            lbl61,lbl62,lbl63,lbl64,lbl65,lbl66,lbl67,lbl68,lbl69,lbl70,
            lbl71,lbl72,lbl73,lbl74,lbl75, lblB, lblI, lblN, lblG, lblO,
            lblCurrentNo,lblCurrentLetter;
    
    @FXML
    private StackPane stackMin, stackClose, stackBody;
    @FXML
    private ImageView imgMin,imgBrands, imgClose,imgHeader;
    @FXML
    private VBox vbCurrent;
    
    private final ObservableList<AnchorPane> anchor = FXCollections.observableArrayList();
    private final ObservableList<Label> number = FXCollections.observableArrayList();
    private final ObservableList<Label> bingo = FXCollections.observableArrayList();
    
    @FXML 
    private AnchorPane anchorB,anchorI,anchorN,anchorG,anchorO;
    @FXML 
    private ImageView imgPattern,imgBG;
    @FXML
    private TextField txtField01;
    @FXML
    private HBox hbMenu;
    @FXML
    private Button btnRefresh,btnNew, btnSearch;
    @FXML
    private Pane btnMin, btnClose;
    /**
     * Initializes the controller class.
     */
    public static void setFrmState(boolean fsValue){
        frmState = fsValue;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        oListener = new LTranDet() {
            @Override
            public void MasterRetreive(int fnIndex, Object foValue) {
              
                initBingo();
                pnEditMode = EditMode.ADDNEW;
                initButton(pnEditMode);
                switch(fnIndex){
                    case 1:
                        break;
                }
            }

            @Override
            public void DetailRetreive(int fnRow, int fnIndex, Object foValue) {
            }
        };
        
        oTrans = new Bingo(oApp, oApp.getBranchCode(), false);
        oTrans.setListener(oListener);
//        oTrans.setTranStat(1);
        oTrans.setWithUI(true);
        
        
        
        initFields();
        initBingoBrand();
        btnSearch.setOnAction(this::cmdButton_Click);
        btnNew.setOnAction(this::cmdButton_Click);
        btnRefresh.setOnAction(this::cmdButton_Click);
        txtField01.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtField01.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        txtField01.setOnKeyPressed(this::txtField_KeyPressed);
        pnEditMode = EditMode.UNKNOWN;
        initButton(pnEditMode);
        // TODO
    }   
    
    private void updateFields(){
//        initBingo(true);
        vbCurrent.setVisible(false);
        try {
            if(oTrans.LoadDetail(oTrans.getMaster("sTransNox").toString())){
                int lnCtr;
                for(lnCtr = 0; lnCtr < number.size(); lnCtr++){
                        number.get(lnCtr).setVisible(false);
                }
                for(lnCtr = 0; lnCtr < anchor.size(); lnCtr++){
                        anchor.get(lnCtr).setStyle("-fx-background-color: #133053; -fx-background-radius: 15px");
                        anchor.get(lnCtr).setVisible(true);
                }
                if(oTrans.getItemCount() > 0){
                    vbCurrent.setVisible(true);
                }
                for(int x = 1; x <= oTrans.getItemCount(); x++){
                    
                    int val = Integer.parseInt(oTrans.getDetail(x, "nBingoNox").toString());
                    
                    for(int y = 0; y < number.size(); y++){
                        
                        int lnIndex = Integer.parseInt((number.get(y)).getId().substring(3,5));
                        if(lnIndex == val){
                            number.get(y).setVisible(true);
                            pnNewNo = val;
//                            anchor.get(y).setVisible(true);

                            pnNewNo = val;
                            number.get(y).setStyle("-fx-text-fill: ORANGE;");
                            setCurrentNo(val);
                        }else{
//                            number.get(y).setVisible(true);
//                            anchor.get(y).setStyle("-fx-background-color: orange");
//                            number.get(y).setStyle("-fx-text-fill: #353232");
                            number.get(y).setStyle("-fx-text-fill: #ffffff");
                        }
                    }
                }
                
            }
            
//        if(val > 0 && val <= 75){
//            for(int x = 0; x < anchor.size(); x++){
//                int lnIndex = Integer.parseInt((number.get(x)).getId().substring(3,5));
//                if(lnIndex == val){
//                    pnNewNo = val;
//                    anchor.get(x).setVisible(true);
//                    
//                    pnNewNo = val;
//                    anchor.get(x).setStyle("-fx-background-color: #993d00");
//                    number.get(x).setStyle("-fx-text-fill: #ffffff");
//                }else{
//                anchor.get(x).setStyle("-fx-background-color: #FF901A");
//                number.get(x).setStyle("-fx-text-fill: #000000");
//                }
//            }
//        }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void cmdButton_Click(ActionEvent event) {
        try {
            String lsButton = ((Button)event.getSource()).getId();
            switch (lsButton){
                case "btnNew":
                    if(oTrans.SearchPattern("", false)){
                        frmState = true;
                        initBingo();
                        pnEditMode = EditMode.ADDNEW;
                        initButton(pnEditMode);
                        updateFields();
                    }
//                    else{
//                        ShowMessageFX.Warning(getStage(), oTrans.getMessage(),"Warning", null);
//                    }
                    break;
                case "btnSearch":
                    if(oTrans.SearchTransaction("", false)){
                        frmState = true;
                        initBingo();
                        pnEditMode = EditMode.ADDNEW;
                        initButton(pnEditMode);
                        updateFields();
                    }
                    break;
                case "btnRefresh":
                    if (ShowMessageFX.OkayCancel(null, pxeModuleName, "Are you sure, do you want to close this record?") == true){  
                            oTrans = new Bingo(oApp, oApp.getBranchCode(), false);
                            oTrans.setListener(oListener);
                            oTrans.setWithUI(true);
                             frmState = false;
                            pnEditMode = EditMode.UNKNOWN;
                            initButton(pnEditMode);

                            initBingo();
                            initFields();
                    }
                   
//                    
                    break;

            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
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
                                if(!txtField.getText().isEmpty()){
                                    int value = Integer.parseInt(txtField.getText().toString());
                                    if(value > 0 && value <= 75){
                                        if(imgBG.isVisible()){
                                            if(oTrans.AddDetail(value)){
//                                                showNumber(txtField.getText().toString());
                                                setCurrentNo(value);
                                                updateFields();
                                            }
//                                            updateFields(Integer.parseInt(txtField.getText().toString()));
                                            txtField.clear();
                                        }
                                    }else{
                                            ShowMessageFX.Warning(getStage(), "Bingo number field must greater than 0 or lesser than equal 75!!!","Warning", null);
                                        }
                                }else{
                                    ShowMessageFX.Warning(getStage(), "Bingo number field must not be empty!!!","Warning", null);
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
    
    private Stage getStage(){
	return (Stage) txtField01.getScene().getWindow();
    }
    public void setPattern(String imgVal){
        imgPattern.setImage(new Image(imgVal));
    }
    private void initFields(){
        pnNewNo = 0;
        pnOldNo = 0;
//        
        anchor.add(num01);
        anchor.add(num02);
        anchor.add(num03);
        anchor.add(num04);
        anchor.add(num05);
        anchor.add(num06);
        anchor.add(num07);
        anchor.add(num08);
        anchor.add(num09);
        anchor.add(num10);
        anchor.add(num11);
        anchor.add(num12);
        anchor.add(num13);
        anchor.add(num14);
        anchor.add(num15);
        anchor.add(num16);
        anchor.add(num17);
        anchor.add(num18);
        anchor.add(num19);
        anchor.add(num20);
        anchor.add(num21);
        anchor.add(num22);
        anchor.add(num23);
        anchor.add(num24);
        anchor.add(num25);
        anchor.add(num26);
        anchor.add(num27);
        anchor.add(num28);
        anchor.add(num29);
        anchor.add(num30);

        anchor.add(num31);
        anchor.add(num32);
        anchor.add(num33);
        anchor.add(num34);
        anchor.add(num35);
        anchor.add(num36);
        anchor.add(num37);
        anchor.add(num38);
        anchor.add(num39);
        anchor.add(num40);

        anchor.add(num41);
        anchor.add(num42);
        anchor.add(num43);
        anchor.add(num44);
        anchor.add(num45);
        anchor.add(num46);
        anchor.add(num47);
        anchor.add(num48);
        anchor.add(num49);
        anchor.add(num50);

        anchor.add(num51);
        anchor.add(num52);
        anchor.add(num53);
        anchor.add(num54);
        anchor.add(num55);
        anchor.add(num56);
        anchor.add(num57);
        anchor.add(num58);
        anchor.add(num59);
        anchor.add(num60);

        anchor.add(num61);
        anchor.add(num62);
        anchor.add(num63);
        anchor.add(num64);
        anchor.add(num65);
        anchor.add(num66);
        anchor.add(num67);
        anchor.add(num68);
        anchor.add(num69);
        anchor.add(num70);

        anchor.add(num71);
        anchor.add(num72);
        anchor.add(num73);
        anchor.add(num74);
        anchor.add(num75);
        
        number.add(lbl01);
        number.add(lbl02);
        number.add(lbl03);
        number.add(lbl04);
        number.add(lbl05);
        number.add(lbl06);
        number.add(lbl07);
        number.add(lbl08);
        number.add(lbl09);
        number.add(lbl10);
        number.add(lbl11);
        number.add(lbl12);
        number.add(lbl13);
        number.add(lbl14);
        number.add(lbl15);
        number.add(lbl16);
        number.add(lbl17);
        number.add(lbl18);
        number.add(lbl19);
        number.add(lbl20);
        number.add(lbl21);
        number.add(lbl22);
        number.add(lbl23);
        number.add(lbl24);
        number.add(lbl25);
        number.add(lbl26);
        number.add(lbl27);
        number.add(lbl28);
        number.add(lbl29);
        number.add(lbl30);

        number.add(lbl31);
        number.add(lbl32);
        number.add(lbl33);
        number.add(lbl34);
        number.add(lbl35);
        number.add(lbl36);
        number.add(lbl37);
        number.add(lbl38);
        number.add(lbl39);
        number.add(lbl40);

        number.add(lbl41);
        number.add(lbl42);
        number.add(lbl43);
        number.add(lbl44);
        number.add(lbl45);
        number.add(lbl46);
        number.add(lbl47);
        number.add(lbl48);
        number.add(lbl49);
        number.add(lbl50);

        number.add(lbl51);
        number.add(lbl52);
        number.add(lbl53);
        number.add(lbl54);
        number.add(lbl55);
        number.add(lbl56);
        number.add(lbl57);
        number.add(lbl58);
        number.add(lbl59);
        number.add(lbl60);

        number.add(lbl61);
        number.add(lbl62);
        number.add(lbl63);
        number.add(lbl64);
        number.add(lbl65);
        number.add(lbl66);
        number.add(lbl67);
        number.add(lbl68);
        number.add(lbl69);
        number.add(lbl70);

        number.add(lbl71);
        number.add(lbl72);
        number.add(lbl73);
        number.add(lbl74);
        number.add(lbl75);
        
        bingo.add(lblB);
        bingo.add(lblI);
        bingo.add(lblN);
        bingo.add(lblG);
        bingo.add(lblO);
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        
        AnchorParent.setMaxWidth(screenBounds.getWidth());
        AnchorParent.setMaxHeight(screenBounds.getHeight());
        System.out.println(screenBounds.getWidth());
        System.out.println(screenBounds.getHeight());
        imgBG.setImage(null);
        
        if(screenBounds.getWidth() > 1366){
            imgBG.setImage(new Image("/gbingo/images/UI Background_Guanzon Fest (1920x1080)_1.png"));
            imgHeader.setImage(new Image("/gbingo/images/header(1920x1080).png"));
//            anchoBody.setPadding(new Insets(90,50,0,50));
//            stackBody.setMargin(new Insets(90,50,0,50));
            BorderPane.setMargin(stackBody,new Insets(0,40,0,40));
            imgPattern.setFitHeight(240.0);
            imgMin.setFitHeight(16.0);
            imgClose.setFitHeight(16.0);
            imgMin.setFitWidth(16.0);
            imgClose.setFitWidth(16.0);
            imgBrands.setFitWidth(260);
            HBox.setMargin(btnMin, new Insets(-20, 0, 0, 0));
            HBox.setMargin(btnClose, new Insets(-20, 0, 0, 0));
                   
            stackMin.setPadding(new Insets(10,20,10,20));
            stackClose.setPadding(new Insets(10,20,10,20));
            for(int x = 0; x < number.size(); x ++){
                number.get(x).getStyleClass().add("lbl-default-lg");
            }
            for(int x = 0; x < bingo.size(); x ++){
                bingo.get(x).getStyleClass().add("lbl-default-lg");
            }
        }else if(screenBounds.getWidth() > 1024  && screenBounds.getWidth() <= 1366){
//            imgBG.setImage(new Image("/gbingo/images/bg(1366x768).jpg"));
            imgBG.setImage(new Image("/gbingo/images/UI Background_Guanzon Fest (1366x768)_1.png"));
            imgHeader.setImage(new Image("/gbingo/images/header(1366x768).png"));
            
            BorderPane.setMargin(stackBody,new Insets(0,30,0,30));
            imgMin.setFitHeight(14.0);
            imgClose.setFitHeight(14.0);
            imgMin.setFitWidth(14.0);
            imgClose.setFitWidth(14.0);
            imgBrands.setFitWidth(200);
            stackMin.setPadding(new Insets(5,15,5,15));
            stackClose.setPadding(new Insets(5,15,5,15));
            HBox.setMargin(btnMin, new Insets(-25, 0, 0, 0));
            HBox.setMargin(btnClose, new Insets(-25, 0, 0, 0));
            for(int x = 0; x < number.size(); x ++){
                number.get(x).getStyleClass().add("lbl-default-md");
            }
            for(int x = 0; x < bingo.size(); x ++){
                bingo.get(x).getStyleClass().add("lbl-default-md");
            }
//            lblCurrentLetter.setStyle("-fx-font-size: 36px");
//            lblCurrentNo.setStyle("-fx-font-size: 48px");
            anchoBody.setPadding(new Insets(0,0,0,5));
            imgPattern.setFitWidth(180.0);
            imgPattern.setFitHeight(180.0);
            
            anchorB.setPadding(new Insets(0, 5, 0, 5));
            anchorI.setPadding(new Insets(0, 5, 0, 5));
            anchorN.setPadding(new Insets(0, 5, 0, 5));
            anchorG.setPadding(new Insets(0, 5, 0, 5));
            anchorO.setPadding(new Insets(0, 5, 0, 5));

        }  else{
            imgBG.setImage(new Image("/gbingo/images/UI Background_Guanzon Fest (1024x768)_1.png"));
            imgHeader.setImage(new Image("/gbingo/images/header(1024x768).png"));
            
//            anchoBody.setPadding(new Insets(20,20,10,20));
//            imgPattern.setFitHeight(180.0);
            lblCurrentLetter.setStyle("-fx-font-size: 24px");
            lblCurrentNo.setStyle("-fx-font-size: 36px");
            stackMin.setPadding(new Insets(5,15,5,15));
            stackClose.setPadding(new Insets(5,15,5,15));
            imgMin.setFitHeight(12.0);
            imgClose.setFitHeight(12.0);
            imgMin.setFitWidth(12.0);
            imgClose.setFitWidth(12.0);
            imgBrands.setFitWidth(160);
            HBox.setMargin(btnMin, new Insets(-25, 0, 0, 0));
            HBox.setMargin(btnClose, new Insets(-25, 0, 0, 0));
            for(int x = 0; x < number.size(); x ++){
                number.get(x).getStyleClass().add("lbl-default");
            }
            for(int x = 0; x < bingo.size(); x ++){
                bingo.get(x).getStyleClass().add("lbl-default");
            }
            
            BorderPane.setMargin(stackBody,new Insets(0,10,0,10));
            anchoBody.setPadding(new Insets(0,0,0,5));
            imgPattern.setFitWidth(160.0);
            imgPattern.setFitHeight(160.0);
            
            anchorB.setPadding(new Insets(0, 5, 0, 5));
            anchorI.setPadding(new Insets(0, 5, 0, 5));
            anchorN.setPadding(new Insets(0, 5, 0, 5));
            anchorG.setPadding(new Insets(0, 5, 0, 5));
            anchorO.setPadding(new Insets(0, 5, 0, 5));
        } 
        for(int y = 0; y < anchor.size(); y ++){
            anchor.get(y).setVisible(false);
        }
        initBingo(); 
    }
    private void initBingo(){
        anchorB.setVisible(frmState);
        anchorI.setVisible(frmState);
        anchorN.setVisible(frmState);
        anchorG.setVisible(frmState);
        anchorO.setVisible(frmState);
        imgPattern.setVisible(frmState);
       
        if(frmState){
            try {
                
                if(oTrans.getMaster("sPatternx") != null){
                    switch(oTrans.getMaster("sPatternx").toString()){
                        case "001": 
                            imgPattern.setImage(new Image("/gbingo/images/corners.png"));
                            break;
                        case "002":
                            imgPattern.setImage(new Image("/gbingo/images/S.png"));
                            break;
                        case "003":
                            imgPattern.setImage(new Image("/gbingo/images/cross.png"));
                            break;
                        case "004":
                            imgPattern.setImage(new Image("/gbingo/images/K.png"));
                            break;
                        case "005":
                            imgPattern.setImage(new Image("/gbingo/images/X.png"));
                            break;
                        case "006":
                            imgPattern.setImage(new Image("/gbingo/images/Y.png"));
                            break;
                        case "007":
                            imgPattern.setImage(new Image("/gbingo/images/G.png"));
                            break;
                        case "008":
                            imgPattern.setImage(new Image("/gbingo/images/H.png"));
                            break;
                        case "009":
                            imgPattern.setImage(new Image("/gbingo/images/blackout.png"));
                            break;
                        
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            vbCurrent.setVisible(false);
       }
        
    }
    public void initButton(int fnValue){
        boolean lbShow = (fnValue == EditMode.ADDNEW || fnValue == EditMode.UPDATE);
        btnRefresh.setVisible(lbShow);
        txtField01.setVisible(lbShow);
        btnSearch.setVisible(!lbShow);
        btnNew.setVisible(!lbShow);
        
        btnRefresh.setManaged(lbShow);
        txtField01.setManaged(lbShow);
        btnSearch.setManaged(!lbShow);
        btnNew.setManaged(!lbShow);
        
    }
    private void initBingoBrand(){
         Image image;
         
        if(System.getProperty("brand").toString().equalsIgnoreCase("YAMAHA")){
            image = new Image("/gbingo/images/Yamaha2.png"); 
            imgBrands.setImage(image);
        }else if(System.getProperty("brand").toString().equalsIgnoreCase("HONDA")){
            image = new Image("/gbingo/images/Honda2.png"); 
            imgBrands.setImage(image);
        }else if(System.getProperty("brand").toString().equalsIgnoreCase("KAWASAKI")){
            image = new Image("/gbingo/images/Kawasaki2.png"); 
            imgBrands.setImage(image);
        }else if(System.getProperty("brand").toString().equalsIgnoreCase("SUZUKI")){
            image = new Image("/gbingo/images/Suzuki2.png"); 
            imgBrands.setImage(image);
        }else if(System.getProperty("brand").toString().equalsIgnoreCase("OPPO")){
            image = new Image("/gbingo/images/Oppo2.png"); 
            imgBrands.setImage(image);
        }else{
            imgBrands.setVisible(false);
        }
    }
    @Override
    public void setGRider(GRider foValue) {
        oApp = foValue;
    }
    @FXML
    private void handleButtonCloseClick(MouseEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleButtonMinimizeClick(MouseEvent event) {
        Stage stage = (Stage) btnMin.getScene().getWindow();
        stage.setIconified(true);
    }

    private void setCurrentNo(int value) {
        if(value >= 1 && value <= 15){
            lblCurrentLetter.setText("B");
        }else if(value >= 16 && value <= 30){
            lblCurrentLetter.setText("I");
        }else if(value >= 31 && value <= 45){
            lblCurrentLetter.setText("N");
        }else if(value >= 46 && value <= 60){
            lblCurrentLetter.setText("G");
        }else if(value >= 61 && value <= 75){
            lblCurrentLetter.setText("O");
        }
        lblCurrentNo.setText(String.valueOf(value));
    
    }
}
