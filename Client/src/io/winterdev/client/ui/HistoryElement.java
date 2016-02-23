/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.winterdev.client.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author frede
 */
public class HistoryElement extends VBox{
    private Label title;
    private Label time;
    private int id;
    private String url;
    private HistoryBar history;
    
    private VBox context;
    private boolean expanded;
            
    public HistoryElement(HistoryBar history,int id, String title,String url){
        this.id = id;
        this.history = history;
        this.title = new Label(title);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        time = new Label(sdf.format(now));
        this.url = url;
        this.getStyleClass().addAll("listEl","historyEl");
        this.title.getStyleClass().add("elTitle");
        time.getStyleClass().add("elTime");
        
        
        context = new VBox();
        context.getStyleClass().add("elContext");
        Label contextApprove = new Label("Approve");
        contextApprove.getStyleClass().addAll("elContextSub","elApprove");
        contextApprove.setOnMouseClicked((MouseEvent e)->{
            if(e.getButton().PRIMARY == MouseButton.PRIMARY){
                history.getMainScreen().getClient().post(id);
                setContext(false);
            }
        });
        Label contextRemove = new Label("Remove");
        contextRemove.getStyleClass().addAll("elContextSub","elRemove");
        contextRemove.setOnMouseClicked((MouseEvent e)->{
            if(e.getButton().PRIMARY == MouseButton.PRIMARY){
                history.getMainScreen().getClient().remove(id);
                setContext(false);
            }
        });
        Label contextSpoil = new Label("Spoiler");
        contextSpoil.getStyleClass().addAll("elContextSub","elSpoil");
        contextSpoil.setOnMouseClicked((MouseEvent e)->{
            if(e.getButton().PRIMARY == MouseButton.PRIMARY){
                history.getMainScreen().getClient().spoil(id);
                setContext(false);
            }
        });
        context.getChildren().addAll(contextApprove,contextRemove,contextSpoil);
        setContext(false);
        this.getChildren().addAll(this.title,time);
        
        this.setOnMouseClicked((MouseEvent e)->{
            if(e.getButton() == MouseButton.PRIMARY)
                history.setSelected(this);
            if(e.getButton() == MouseButton.SECONDARY)
                toggleContext();
        });
        
        
    }
    public String getUrl(){
        return url;
    }
    public void setContext(boolean b){
        if(b){
            if(!this.getChildren().contains(context))
            this.getChildren().add(context);
                expanded = true;
        }else{
            if(this.getChildren().contains(context))
                this.getChildren().remove(context);
                expanded = false;
        }
        
    }
    public void toggleContext(){
        setContext(!expanded);
    }
}
