/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.winterdev.server.content.source;

import io.winterdev.server.content.Content;
import io.winterdev.server.content.source.filter.Filter;
import java.sql.Statement;

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
    public Source(String name, int delay, Filter filter,Statement statement){
        this.name = name;
        this.delay = delay;
        this.filter = filter;
    }
    
    protected abstract void fetch();
    
    protected void checkConent(Content content){
        int weight = filter.getWeight(content.getTitle().toLowerCase());
        if(weight > 0){
            
        }
        
    }
    
    @Override
    public void run(){
        while(fetching){
            try{
                Thread.sleep(delay);
            }catch(Exception e){};
            fetch();
        }
    }
}
