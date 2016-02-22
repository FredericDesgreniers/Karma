package io.winterdev.server;

import io.winterdev.server.content.source.Source;
import io.winterdev.server.content.source.SourceYoutube;
import io.winterdev.server.content.source.filter.Filter;
import io.winterdev.server.content.source.filter.WeightedFilter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author frede
 */
public class ContentManager {
    private static int id=0;
    public static int getUniqueId(){
        return id++;
    }
    
    private Server server;
    public List<Source> sources;
    public ContentManager(Server server){
        this.server  = server;
        sources = new ArrayList();
    }
    
    private void addSources(){
        try {
            SourceYoutube theScore = new SourceYoutube("theScore Esports","",5000,new Filter(new WeightedFilter[]{
                
            }),server.getData().getUniqueStatement());
            addSource(theScore);
            
        } catch (SQLException ex) {
            Logger.getLogger(ContentManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void addSource(Source source){
        sources.add(source);
        source.start();
    }
    
    
}
