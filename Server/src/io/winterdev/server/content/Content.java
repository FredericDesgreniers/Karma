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
public abstract class Content {    
    protected String title,url;
    private int id;
    public Content(int id,String title, String url){
        this.title = title;
        this.url = url;
        this.id = id;
    }
    
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    
    public String getUrl(){
        return url;
    }
    public void setUrl(String url){
        this.url = url;
    }
    
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    
    public abstract boolean checkUrl(String url);
}
