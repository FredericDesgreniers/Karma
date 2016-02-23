/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.winterdev.client.ui;

import io.winterdev.client.Client;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author frede
 */
public class ScreenAlert extends StackPane{
    public ScreenAlert(Client client,int id,String title,String type){
        
        Stage stage =  new Stage();
        stage.setTitle(title);
        stage.setAlwaysOnTop(true);
        Scene scene = new Scene(this,800,500);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        
        stage.setScene(scene);
        stage.show();
       this.getStyleClass().add(type.equalsIgnoreCase("alert")?"alert":"notification");
        Label label = new Label(title);
        label.getStyleClass().add("alertLabel");
        this.getChildren().add(label);
        scene.setOnKeyPressed((KeyEvent ke)->{
            System.out.println(ke.getCode());
            if(ke.getCode() == KeyCode.A){
                client.post(id);
                stage.close();
            }else if(ke.getCode() == KeyCode.R){
                client.remove(id);
                stage.close();
            }else if(ke.getCode() == KeyCode.S){
                client.spoil(id);
                stage.close();
            }
        });
        
        
    }
}
