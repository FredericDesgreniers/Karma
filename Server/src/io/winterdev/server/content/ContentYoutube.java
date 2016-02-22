/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.winterdev.server.content;

/**
 *
 * @author frede
 */
public class ContentYoutube extends Content{
    private String ytId;
    public ContentYoutube(int id, String title, String ytId) {
        super(id, title, "https://www.youtube.com/watch?v="+ytId);
        this.ytId = ytId;
    }
    @Override
    public boolean checkUrl(String url) {
        if(url.contains("v="+ytId))return true;
        
        return false;
    }
    
    public String getYtId(){
        return this.ytId;
    }
    public void setYtId(String ytId){
        this.ytId = ytId;
    }
    
}
