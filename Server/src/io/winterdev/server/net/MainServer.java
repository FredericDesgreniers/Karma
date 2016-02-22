/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.winterdev.server.net;

import io.winterdev.server.Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author frede
 */
public class MainServer extends Thread{
    private Server server;
    public static final int PORT=2343;
    /**
     * socket thats waiting for a connection
     */
    private ServerSocket socket;
    /**
     * Should server be waiting for connection
     */
    private boolean waiting = true;
    /**
     * list of all clients
     */
    private List<ClientConnection> clients;
    private HeartbeatServer keepAlive;
    public MainServer(Server server){
        this.server = server;
        clients = new ArrayList();
        this.setName("ClientWaiter");
        keepAlive=  new HeartbeatServer(this);
    }
    public void send(String str){
        for(ClientConnection client:clients){
            client.sendRaw(str);
        }
    }
    @Override
    public void run() {
        try {
            socket = new ServerSocket(PORT);
            keepAlive.start();
        while(waiting){
            Socket clientSock = socket.accept();
            ClientConnection client = new ClientConnection(this,clientSock);
            clients.add(client);
            client.start();
            System.out.println("New client added!");
        }
        
        } catch (IOException ex) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void remove(ClientConnection client){
        if(clients.contains(client))
        clients.remove(client);
    }
    public List<ClientConnection> getClients(){
        return this.clients;
    }
    public Server getServer(){
        return server;
    }
}
