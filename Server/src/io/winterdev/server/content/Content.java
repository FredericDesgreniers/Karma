/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.winterdev.server.content;

import net.dean.jraw.models.Submission;

/**
 *
 * @author frede
 */
public abstract class Content {    
    protected String title,url,reddit;
    private Submission submission;
    private int id;
    public Content(int id,String title, String url){
        this.title = title;
        this.url = url;
        this.id = id;
        reddit = "";
    }
    public void setSubmission(Submission submission){
        this.submission = submission;
    }
    public Submission getSubmission(){
        return this.submission;
    }
    public void setReddit(String reddit){
        this.reddit = reddit;
    }
    public String getReddit(){
        return reddit;
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
