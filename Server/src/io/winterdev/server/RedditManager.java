/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.winterdev.server;

import io.winterdev.server.content.Content;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.dean.jraw.ApiException;
import net.dean.jraw.RedditClient;
import net.dean.jraw.http.NetworkException;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.http.oauth.Credentials;
import net.dean.jraw.http.oauth.OAuthData;
import net.dean.jraw.http.oauth.OAuthException;
import net.dean.jraw.managers.AccountManager;
import net.dean.jraw.managers.ModerationManager;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import net.dean.jraw.paginators.Sorting;
import net.dean.jraw.paginators.SubredditPaginator;
import net.dean.jraw.paginators.TimePeriod;

/**
 *
 * @author frede
 */
public class RedditManager {
    private UserAgent agent;
    private RedditClient client;
    private Credentials credentials;
    private OAuthData authData;
    public static final String subreddit = "leagueoflegends";
    private Server server;
    
    public List<Content> waiting;
    public List<Content> submitted;
    
    public RedditManager(Server server) throws NetworkException, OAuthException{
        this.server  = server;
        agent = UserAgent.of(Private.platform, Private.packageName,Private.version,Private.name);
        client  = new RedditClient(agent);
        credentials = Credentials.script(Private.REDDIT_USERNAME, Private.REDDIT_PASSWORD, Private.REDDIT_CLIENT_ID, Private.REDDIT_SECRET);
        authData = client.getOAuthHelper().easyAuth(credentials);
        client.authenticate(authData);
        client.me();
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            public void run(){
                try{
                    refresh();
                }catch(Exception e){}
            }
        }, 100,600000);
        
        waiting = new ArrayList();
        submitted = new ArrayList();
    }
    
    
    public synchronized void refresh() throws OAuthException{
        credentials = Credentials.script(Private.REDDIT_USERNAME, Private.REDDIT_PASSWORD, Private.REDDIT_CLIENT_ID, Private.REDDIT_SECRET);
        authData = client.getOAuthHelper().easyAuth(credentials);
        client.authenticate(authData);
    }
public void verifyContent(String sub,Submission s){
        SubredditPaginator sp = new SubredditPaginator(client);
        sp.setLimit(10);
        sp.setSorting(Sorting.NEW);
        sp.setTimePeriod(TimePeriod.HOUR);
        sp.setSubreddit(sub);
        
        sp.next(true);
        Listing<Submission> list = sp.getCurrentListing();
        boolean after=false;
        for(Submission sub1:list){
            if(sub1.getId().equalsIgnoreCase(s.getId())){
                after=true;
            }else
            if(after)
            if(sub1.getUrl().contains(s.getUrl().replace("https://", "").trim())){
                try {
                    ModerationManager manager = new ModerationManager(client);
                    manager.delete(s);
                    System.out.println(s.getTitle()+" DELETED FROM "+s.getSubredditName());
                } catch (NetworkException ex) {
                    Logger.getLogger(RedditManager.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ApiException ex) {
                    Logger.getLogger(RedditManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    public void postConent(Content content){
        String redditLink="";
        try{
        String url = content.getUrl();
        String title = content.getTitle();
        AccountManager manager = new AccountManager(client);
        AccountManager.SubmissionBuilder sub = new AccountManager.SubmissionBuilder(new URL(url),subreddit,title);
        sub.resubmit(false);
        System.out.println("SUBMITTING CONTENT: "+title+"  | "+url+" to /r/"+subreddit);
        Submission s = manager.submit(sub);
        
        redditLink = s.getPermalink();
        
        submitted.add(content);
        
        try {
            Thread.sleep(2000);
            
        } catch (InterruptedException ex) {
            Logger.getLogger(RedditManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        verifyContent(subreddit,s);
        }catch(MalformedURLException | NetworkException | ApiException e){
            Logger.getLogger(RedditManager.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("error summitting");
        }
    }
    public void addWaiting(Content content){
        waiting.add(content);
        
    }
}
