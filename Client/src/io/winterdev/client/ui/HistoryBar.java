/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.winterdev.client.ui;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.winterdev.client.ApiGetter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 *
 * @author frede
 */
public class HistoryBar extends VBox{
    private ScreenMain main;
    private HistoryElement selected;
    public HistoryBar(ScreenMain main){
        this.main = main;
        this.getStyleClass().addAll("list","history");
        
        try{
        getServerHistory();
        }catch(Exception e){e.printStackTrace();};
    }
    public ScreenMain getMainScreen(){
        return main;
    }
    public void addToHistory(int i,HistoryElement el){
        for(Node e:getChildren()){
            if(e instanceof HistoryElement){
                if(((HistoryElement)e).getUrl().equals(el.getUrl()))
                    return;
            }
        }
        this.getChildren().add(i,el);
    }
    public void addToHistory(HistoryElement el){
        for(Node e:getChildren()){
            if(e instanceof HistoryElement){
                if(((HistoryElement)e).getUrl().equals(el.getUrl()))
                    return;
            }
        }
        this.getChildren().add(el);
    }
    public void setSelected(HistoryElement el){
        for(Node n : this.getChildren()){
            n.getStyleClass().remove("elSelected");
            if(n instanceof HistoryElement)
                ((HistoryElement)n).setContext(false);
        }
        el.getStyleClass().add("elSelected");
       el.setContext(true);
       selected = el;
        main.loadBrowser(el.getUrl());
    }
    
    public void getServerHistory() throws Exception{
        JsonArray historyList = ApiGetter.get("https://winterdev.io/karma/content.php").getAsJsonArray();
        for(JsonElement el:historyList){
            JsonObject obj = el.getAsJsonObject();
            HistoryElement hEl = new HistoryElement(this,-1,obj.get("title").getAsString(),obj.get("url").getAsString(),obj.get("fetchTime").getAsString());
            addToHistory(hEl);
        }
    }
    public HistoryElement getSelected(){
        return selected;
    }
}
