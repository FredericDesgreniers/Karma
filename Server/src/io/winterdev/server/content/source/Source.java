/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.winterdev.server.content.source;

import io.winterdev.server.ContentManager;
import io.winterdev.server.Private;
import io.winterdev.server.Server;
import io.winterdev.server.content.Content;
import io.winterdev.server.content.source.filter.Filter;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author frede
 */
public abstract class Source extends Thread{
    private String name;
    private int delay;
    private boolean fetching = true;
    private Filter filter;
    protected Statement statement;
    private List<String> urlBuffer;
    private Server server;
    
    public Source(Server server, String name, int delay, Filter filter,Statement statement){
        this.name = name;
        this.delay = delay;
        this.filter = filter;
        urlBuffer = new ArrayList();
        this.server = server;
        this.statement = statement;
    }
    
    protected abstract void fetch();
    
    protected void checkConent(Content content){
        if(urlBuffer.contains(content.getUrl()))return;
        urlBuffer.add(content.getUrl());
        try{
            if(server.getData().checkContent(statement, content))return;
        }catch(Exception ex){
            fetching = false;
            ex.printStackTrace();
            
            System.out.println("STOPPED "+name+" :: COULD NOT CHECK SQL");
            return;
        }
       content.setId(ContentManager.getUniqueId());
        int weight = filter.getWeight(content.getTitle().toLowerCase());
        if(weight > 0){
            server.getReddit().submit(content);
        }else{
            server.getReddit().addWaiting(content);
            server.getSms().sendSms(Private.PHONE_NUMBER, content.getId()+" WAITING "+content.getTitle());
        }
        
        try {
            
            server.getData().insertContent(statement, content);
            System.out.println("INSERTED "+content.getTitle()+" : "+content.getUrl());
        } catch (SQLException ex) {
            fetching = false;
            ex.printStackTrace();
            System.out.println("STOPPED "+name+" :: COULD NOT INSERT (woudl cause repost)");
        }
        
    }
    
    @Override
    public void run(){
        while(fetching){
            fetch();
            try{
                Thread.sleep(delay);
            }catch(Exception e){};
            
        }
    }
}
