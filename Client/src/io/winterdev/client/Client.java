/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.winterdev.client;

import io.winterdev.client.ui.ScreenMain;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author frede
 */
public class Client extends Application{
    private ClientNetwork network;
    private ScreenMain mainScreen;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        network = new ClientNetwork(this);
        network.start();
        
        primaryStage.setTitle("WINTERDEV.IO Karma bot");
        primaryStage.setOnCloseRequest((WindowEvent t)->{
            Platform.exit();
            network.stop();
            System.exit(0);
        });
        mainScreen = new ScreenMain(this);
       Scene scene = new Scene(mainScreen, 1000, 1000);
       scene.getStylesheets().add(getClass().getResource("ui/style.css").toExternalForm());
       
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void post(int id){
        network.sendRaw("post;;"+id);
        System.out.println("posting "+id);
    }
    public void spoil(int id){
        network.sendRaw("spoil;;"+id);
        System.out.println("spoilling "+id);
    }
    public void remove(int id){
        network.sendRaw("remove;;"+id);
        System.out.println("removing "+id);
    }
    public ScreenMain  getMainScreen(){
        return this.mainScreen;
    }
}
