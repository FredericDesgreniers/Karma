/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.winterdev.client.ui;

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
        
        addToHistory(new HistoryElement(this,-2,"Thorin's Thoughts - The Chilling Tale of the Freeze Curse (LoL)","https://www.youtube.com/watch?v=y-QGpymV_uM"));
        addToHistory(new HistoryElement(this,-3,"Top 5 Plays from Week 6 of the LCS","https://www.youtube.com/watch?v=uxMxevcD2Kk"));
        

    }
    public ScreenMain getMainScreen(){
        return main;
    }
    public void addToHistory(HistoryElement el){
        this.getChildren().add(el);
    }
    public void setSelected(HistoryElement el){
        for(Node n : this.getChildren()){
            n.getStyleClass().remove("elSelected");
        }
        el.getStyleClass().add("elSelected");
        main.loadBrowser(el.getUrl());
    }
}
