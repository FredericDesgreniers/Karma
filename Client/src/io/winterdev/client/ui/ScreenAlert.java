/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.winterdev.client.ui;

import io.winterdev.client.Client;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author frede
 */
public class ScreenAlert extends VBox{
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
        VBox.setVgrow(this, Priority.ALWAYS);
        label.setPrefWidth(Double.MAX_VALUE);
        this.getChildren().add(label);
        scene.setOnKeyPressed((KeyEvent ke)->{
            System.out.println(ke.getCode());
            if(null != ke.getCode())switch (ke.getCode()) {
                case A:
                    client.post(id);
                    stage.close();
                    break;
                case R:
                    client.remove(id);
                    stage.close();
                    break;
                case S:
                    client.spoil(id);
                    stage.close();
                    break;
                default:
                    break;
            }
        });
        HBox options = new HBox();
        options.getStyleClass().add("alertOptions");
        
        Button post = new Button("Accept");
        post.setOnAction((ActionEvent e)->{
            client.post(id);
            stage.close();
        });
        post.getStyleClass().add("alertAccept");
        Button remove = new Button("Remove");
        remove.setOnAction((ActionEvent e)->{
            client.remove(id);
            stage.close();
        });
        remove.getStyleClass().add("alertReject");
        Button spoil = new Button("Spoil");
        spoil.setOnAction((ActionEvent e)->{
            client.spoil(id);
            stage.close();
        });
        spoil.getStyleClass().add("alertSpoil");
        
        options.getChildren().addAll(post,remove,spoil);
        this.getChildren().add(options);
        
    }
}
