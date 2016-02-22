/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.winterdev.server;

import io.winterdev.server.net.MainServer;
import java.sql.SQLException;

/**
 *  Manages the program
 * @author frede
 */
public class Server {
    private MainServer mainServer;
    private ContentManager contentManager;
    private DataManager data;
    
    public Server() throws SQLException{
        
        data = new DataManager(this);
        mainServer = new MainServer(this);
        contentManager = new ContentManager(this);
       
        //mainServer.start();
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
