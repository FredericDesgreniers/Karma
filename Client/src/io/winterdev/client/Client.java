/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.winterdev.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author frede
 */
public class Client {

    private boolean connected = true;
    private Socket socket;
    PrintWriter out;
    public Client(){
        try{
             socket = new Socket(Private.HOST,Private.PORT);
              out = new PrintWriter(socket.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             
             String input;
             while(connected && (input = in.readLine()) !=null){
                 try{
                 if(input.equalsIgnoreCase("ping")){
                     sendRaw("pong");
                     continue;
                 }
                 String[] args = input.split(";;");
                 System.out.println(input);
                 if(args.length>0){
                     String command = args[0];
                     switch(command){
                         case "new":
                             if(args.length>2){
                                 String title = args[2];
                                 int id = Integer.valueOf(args[1]);
                                 new AlertFrame(this,id,title);
                             }
                             break;
                     }
                 }
                 }catch(Exception e){e.printStackTrace();}
             }
        }catch(Exception e){e.printStackTrace();}
    }
    
    public synchronized void sendRaw(String str){
        out.println(str);
        out.flush();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Client();
    }
    
}
