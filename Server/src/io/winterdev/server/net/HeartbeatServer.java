/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.winterdev.server.net;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Used to check if connections are alive
 * @author frede
 */
public class HeartbeatServer extends Thread{
    public static final int DELAY=5000;
    private MainServer server;
    private boolean running = true;
    public HeartbeatServer(MainServer server){
        this.server = server;
    }
    public void run(){
        while(running){
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException ex) {}
            List<ClientConnection> clients = new ArrayList(server.getClients());
            for(ClientConnection client : clients){
                client.ping();
            }
            
            
        }
    }
}
