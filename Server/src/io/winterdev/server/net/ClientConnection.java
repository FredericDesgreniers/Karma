/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.winterdev.server.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author frede
 */
public class ClientConnection extends Thread{
    private Socket socket;
    private boolean connected = true;
    private PrintWriter out;
    private BufferedReader in;
    
    private boolean pinged = false;
    private MainServer server;
    public ClientConnection(MainServer server,Socket socket) throws IOException{
        this.socket = socket;
        this.server = server;
        out = new PrintWriter(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
    }
    public synchronized void sendRaw(String data){
        out.println(data);
        out.flush();
    }
    public void run(){
            String input;
            sendRaw("new;;0;;FAILLED(0) Thorins Though - The return of the bot!");
            try {
                while(connected && (input = in.readLine()) != null){
                    if(input.equalsIgnoreCase("pong")){
                        pinged = false;
                    }
                    String[] args = input.split(";;");
                    if(args.length>0){
                        switch(args[0]){
                            case "post":
                                if(args.length>1){
                                    System.out.println("POSTING "+Integer.valueOf(args[1]));
                                }
                                break;
                            case "spoil":
                                if(args.length>1){
                                    System.out.println("SPOIL "+Integer.valueOf(args[1]));
                                }
                                break;
                            case "remove":
                                if(args.length>1){
                                    System.out.println("REMOVE "+Integer.valueOf(args[1]));
                                }
                                break;
                        }
                    }
                }
            } catch (IOException ex) {
                connected  = false;
            }
            close();
        System.out.println("Client has been disconnected");
    }
    private void close(){
        try {
            socket.close();
        } catch (IOException ex) {}
        
        server.remove(this);
    }
    public void setConnected(boolean connected){
        this.connected = connected;
    }
    public void ping(){
        if(pinged){
            connected = false;
            System.out.println("Failed to ping back, client closed");
            close();
        }
        sendRaw("PING");
        pinged = true;
    }
}
