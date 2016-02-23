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
        addSources();
    }
    
    private void addSources(){
        try {
            SourceYoutube theScore = new SourceYoutube(server, "theScore Esports","UCSCoziKHqjqbox3Fv3Pb4BA",5000,new Filter(new WeightedFilter[]{
                
            }),server.getData().getUniqueStatement());
            addSource(theScore);
            
            SourceYoutube riotgames = new SourceYoutube(server, "Riot Games","UC2t5bjwHdUX4vM2g8TRDq5g",3000,new Filter(new WeightedFilter[]{
                new WeightedFilter("spotlight",1),
                new WeightedFilter("patch rundown",1),
                new WeightedFilter("video guide",1),
                new WeightedFilter("tunes",-1),
                new WeightedFilter("remix",-1),
                new WeightedFilter("login screen",5),
                new WeightedFilter("video guide",-3)
            }),server.getData().getUniqueStatement());
            addSource(riotgames);
            
            SourceYoutube lolesports = new SourceYoutube(server, "LoL Esports","UCvqRdlKsE5Q8mf8YXbdIJLw",3000,new Filter(new WeightedFilter[]{
                new WeightedFilter("player spotlight",1),
                new WeightedFilter("team spotlight",1),
                new WeightedFilter("closer look",1),
                new WeightedFilter("mic check",1)
            }),server.getData().getUniqueStatement());
            addSource(lolesports);
            
            SourceYoutube htcesports = new SourceYoutube(server, "HTC eSports","UCcLgWeVW3Z_ZWCYURqfgvaQ",5000,new Filter(new WeightedFilter[]{
                new WeightedFilter("rebirth",1)
            }),server.getData().getUniqueStatement());
            addSource(htcesports);
            
            SourceYoutube insight = new SourceYoutube(server, "insight","UC4AGeYoOM9lRH6-L7wNFgTQ",5000,new Filter(new WeightedFilter[]{
                new WeightedFilter("Summoning Insight",1)
            }),server.getData().getUniqueStatement());
            addSource(insight);
            
            SourceYoutube thoorin = new SourceYoutube(server, "thoorin","UCfeeUuW7edMxF3M_cyxGT8Q",4000,new Filter(new WeightedFilter[]{
                new WeightedFilter("(lol)",1)
            }),server.getData().getUniqueStatement());
            addSource(thoorin);
            
        } catch (SQLException ex) {
            Logger.getLogger(ContentManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void addSource(Source source){
        sources.add(source);
        source.start();
    }
    
    
}
