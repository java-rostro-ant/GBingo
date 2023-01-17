/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gbingo.model;

import javafx.scene.layout.AnchorPane;

/**
 *
 * @author User
 */
public class NumberModel {
    private AnchorPane number;
    private boolean val;

    public NumberModel(AnchorPane num){
        this.number = num;
    }
    
    public AnchorPane getNumber() {
        return number;
    }

    public void setNumber(AnchorPane number) {
        this.number = number;
    }
    
    public void setVisible(boolean val){
        this.val = val;
    }
    public boolean getVisible(){
        return val;
    }
}
