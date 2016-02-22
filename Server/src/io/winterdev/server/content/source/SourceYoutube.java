/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.winterdev.server.content.source;

import io.winterdev.server.ContentManager;
import io.winterdev.server.content.ContentYoutube;
import io.winterdev.server.content.source.filter.Filter;
import java.io.IOException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

/**
 *
 * @author frede
 */
public class SourceYoutube extends Source{

    private String channelId;
    public SourceYoutube(String name,String channelId, int delay, Filter filter,Statement statement) {
        super(name, delay, filter,statement);
        this.channelId = channelId;
    }
    
    @Override
    protected void fetch() {
        
    }
public static synchronized List<ContentYoutube> crawlChannel(String channelid) throws IOException{
        List<ContentYoutube> videos = new ArrayList();
        
        org.jsoup.nodes.Document doc = Jsoup.connect("https://www.youtube.com/channel/"+channelid+"/videos").userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36").get();
        Elements videosEl = doc.select("#channels-browse-content-grid");
        for(int i=0;i<videosEl.size();i++){
            org.jsoup.nodes.Element e = videosEl.get(i);
            for(org.jsoup.nodes.Element title: e.select(".yt-lockup-content a"))
            {
                videos.add(new ContentYoutube(ContentManager.getUniqueId(),title.text(),title.attr("href").split("v=")[1]));
               // System.out.println(title.attr("href")+"   "+title.text());
                break;
            }
            
        }
        return videos;
    }
    
}
