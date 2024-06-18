package gbingo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import javafx.application.Application;
import org.rmj.appdriver.GRider;

public class LetMeInn {
    public static void main(String [] args){     
        String path;
        if(System.getProperty("os.name").toLowerCase().contains("win")){
            path = "D:/GGC_Java_Systems";
        }
        else{
            path = "/srv/mac/GGC_Java_Systems";
        }
        System.setProperty("sys.default.path.config", path);
        
        GRider oApp = new GRider();
        
        if (!oApp.loadEnv("Tabulation")) {
            System.err.println(oApp.getErrMsg());
            System.exit(1);
        }
        
        if (!oApp.logUser("Tabulation", "M001111122")) {
            System.err.println(oApp.getErrMsg());
            System.exit(1);
        }   
        
        loadProperties();
        GriderGui instance = new GriderGui();
        instance.setGRider(oApp);
        
        Application.launch(instance.getClass());
    }
    private static boolean loadProperties(){
        try {
            Properties po_props = new Properties();
            po_props.load(new FileInputStream(System.getProperty("sys.default.path.config") + "/config/BingoBrand.properties"));

            System.setProperty("brand", po_props.getProperty("brand"));
            
            return true;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
      
        }
    }
}