/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.winterdev.server;

import io.winterdev.server.net.MainServer;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.dean.jraw.http.NetworkException;
import net.dean.jraw.http.oauth.OAuthException;

/**
 *  Manages the program
 * @author frede
 */
public class Server {
    private MainServer mainServer;
    private ContentManager contentManager;
    private DataManager data;
    private RedditManager reddit;
    
    public Server() throws SQLException{
        
        data = new DataManager(this);
        try {
            reddit = new RedditManager(this);
        } catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }
        mainServer = new MainServer(this);
        contentManager = new ContentManager(this);
       
        mainServer.start();
    }
    public ContentManager getContentManager(){
        return contentManager;
    }
    public MainServer getMainServer(){
        return mainServer;
    }
    public DataManager getData(){
        return data;
    }
    public RedditManager getReddit(){
        return reddit;
    }
    
    
    
    /**
     * Instance of the Server
     */
    public static Server instance;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        instance = new Server();
    }
    
    
}
