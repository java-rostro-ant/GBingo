/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gbingo;

import gbingo.controller.GBingoController;
import gbingo.controller.GBingoController1204;
import gbingo.controller.MainController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.rmj.appdriver.GRider;

public class GriderGui extends Application {
    public final static String pxeMainFormTitle = "MarketPlace";
    public static String pxeMainForm = "/gbingo/ui/GBingo.fxml";
    public final static String pxeStageIcon = "/gbingo/images/guanzo_small_logo.png";
    public static GRider oApp;
    
    @Override
    public void start(Stage stage) throws Exception {      
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        System.out.println(screenBounds.getWidth());
        if(screenBounds.getWidth() <= 1024){
//            pxeMainForm = "/gbingo/ui/GBingo(1024x7l8).fxml";
            loadScreen1(stage,"/gbingo/ui/GBingoSmall.fxml");
        }else{
            loadScreen(stage,"/gbingo/ui/GBingo.fxml");
        }
    }
     /*Parameters*/
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public void setGRider(GRider foValue){
        oApp = foValue;
    }
    private void loadScreen(Stage stage,String val) throws IOException{
        FXMLLoader view = new FXMLLoader();
        view.setLocation(getClass().getResource(val));
        
        GBingoController controller = new GBingoController();
        controller.setGRider(oApp);
        
        view.setController(controller);        
        Parent parent = view.load();
        Scene scene = new Scene(parent);
//        Parent root = FXMLLoader.load(getClass().getResource(pxeMainForm));
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();


        //get the screen size
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(pxeStageIcon));
        stage.setTitle(pxeMainFormTitle);
        
        // set stage as maximized but not full screen
        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
        stage.centerOnScreen();
        stage.show();
        
    }
     private void loadScreen1(Stage stage, String val) throws IOException{
        FXMLLoader view = new FXMLLoader();
        view.setLocation(getClass().getResource(val));
        
        GBingoController1204 controller = new GBingoController1204();
        controller.setGRider(oApp);
        
        view.setController(controller);        
        Parent parent = view.load();
        Scene scene = new Scene(parent);
//        Parent root = FXMLLoader.load(getClass().getResource(pxeMainForm));
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();


        //get the screen size
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(pxeStageIcon));
        stage.setTitle(pxeMainFormTitle);
        
        // set stage as maximized but not full screen
        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
        stage.centerOnScreen();
        stage.show();
        
    }
}
