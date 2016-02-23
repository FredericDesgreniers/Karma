/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.winterdev.client.ui;

import io.winterdev.client.Client;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;

/**
 *
 * @author frede
 */
public class ScreenMain extends BorderPane{
    private HistoryBar history;
    private WebView browser;
    Client client;
    public ScreenMain(Client client){
        this.client = client;
        
        history = new HistoryBar(this);
        
        
        
        
        Button alert = new Button("Alert");
        
        browser = new WebView();
        
        this.setLeft(history);
        this.setRight(alert);
        this.setCenter(browser);
        alert.setOnAction((ActionEvent e)->{
            new ScreenAlert(client,-2,"TEST","alert");
        });
        this.getStyleClass().add("main");
        
    }
    public HistoryBar getHistory(){
        return history;
    }
    public Client getClient(){
        return client;
    }
    public void loadBrowser(String url){
        browser.getEngine().load(url);
    }
}
