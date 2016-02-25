/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.winterdev.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 *
 * @author frede
 */
public class ApiGetter {
    private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }
    
    public static JsonElement get(String urlStr) throws MalformedURLException, IOException{
        URL url = new URL(urlStr.replaceAll(" ", "%20"));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.addRequestProperty("User-agent", "winterdev_io v0.1");
        
        conn.setRequestMethod("GET");
        conn.setUseCaches(false);


        conn.connect();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),Charset.forName("UTF-8")));
        String jsonStr = readAll(rd);
        
        JsonElement obj = new JsonParser().parse(jsonStr);
        return obj;

    }
    
}
