/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.winterdev.server;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.winterdev.server.api.ApiGetter;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author frede
 */
public class SmsManager extends Thread{
    public Server server;
    public boolean waiting=true;
    private int currentID=0;
    public SmsManager(Server server){
        this.server=server;
    }
    public boolean sendSms(String num,String text){

        try{
        JsonObject response = ApiGetter.get("https://rest.nexmo.com/sms/json?api_key="+Private.NEXMO_KEY+"&api_secret="+Private.NEXMO_SECRET+"&to="+num+"&text="+text+"&from="+Private.NEXMO_NUMBER);
        JsonObject messages = response.getAsJsonArray("messages").get(0).getAsJsonObject();
        JsonPrimitive status = messages.getAsJsonPrimitive("status");
        if(status.getAsString().equalsIgnoreCase("0"))return true;
        }catch(Exception e){e.printStackTrace();}
        return false;
    }
    @Override
    public void run(){
        try {
            Statement statement = server.getData().getUniqueStatement();
            while(waiting){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SmsManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                try{
                for(String s:server.getData().getSmsList(statement)){
                    String[] args = s.split(" ");
                    if(args.length>2){
                        String command = args[0];
                        int id = Integer.valueOf(args[1]);
                        switch(command){
                            case "p":
                                server.getReddit().post(id);
                                break;
                            case "s":
                                server.getReddit().addSpoiler(id);
                                break;
                            case "r":
                                server.getReddit().remove(id);
                                break;
                        }
                    }
                }
                }catch(Exception e){e.printStackTrace();};
            }
        } catch (SQLException ex) {
            Logger.getLogger(SmsManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
