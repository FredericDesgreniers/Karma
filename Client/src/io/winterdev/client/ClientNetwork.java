/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.winterdev.client;

import io.winterdev.client.ui.HistoryElement;
import io.winterdev.client.ui.ScreenAlert;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author frede
 */
public class ClientNetwork extends Thread{
    private boolean connected = true;
    private Socket socket;
    PrintWriter out;
    
    private final Client client;
    
    public ClientNetwork(Client client){
        this.client = client;
    }
    
    public void run(){
        Toolkit.getDefaultToolkit().beep();
        while(true){
            System.out.println("Connecting attempt");
        try{
             socket = new Socket(Private.HOST,Private.PORT);
              out = new PrintWriter(socket.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             
             String input=null;
             while(connected && (input = in.readLine()) !=null){
                 try{
                 if(input.equalsIgnoreCase("ping")){
                     sendRaw("pong");
                     continue;
                 }
                 String[] args = input.split(";;");
                 System.out.println(input);
                 if(args.length>1){
                     String type = args[0];
                     String command = args[1];
                     
                     switch(command){
                         case "waiting":
                             if(args.length>2){
                                 String title = args[3];
                                 String url = args[4];
                                 int id = Integer.valueOf(args[2]);
                                 if(type.equalsIgnoreCase("alert")){
                                   Platform.runLater(()->{
                                       new ScreenAlert(client,id,title,"alert");
                                       client.getMainScreen().getHistory().addToHistory(0,new HistoryElement(client.getMainScreen().getHistory(),id,title,url));
                                   
                                   }) ;
                                   
                                 }
                             }
                             break;
                         case "submitted":
                             if(args.length>2){
                                 String title = args[3];
                               
                                String url = args[4];
                                 
                                 int id = Integer.valueOf(args[2]);
                                 if(type.equalsIgnoreCase("alert")){
                                    Platform.runLater(()->{
                                        
                                        new ScreenAlert(client,id,title,"submitted");
                                    
                                    client.getMainScreen().getHistory().addToHistory(0,new HistoryElement(client.getMainScreen().getHistory(),id,title,url));
                                    
                                    });
                                    
                                 }
                             }
                             break;
                             
                     }
                 }
                 }catch(Exception e){e.printStackTrace();}
             }
             if(input==null)System.out.println("null");
             System.out.println("disconnected "+connected);
        }catch(Exception e){System.out.println("Error");}
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public synchronized void sendRaw(String str){
        out.println(str);
        out.flush();
    }
    public void setConnected(boolean c){
        connected = c;
    }
}
