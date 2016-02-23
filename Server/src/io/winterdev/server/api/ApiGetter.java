/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.winterdev.server.api;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


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
    
    public static JsonObject get(String urlStr) throws MalformedURLException, IOException{
        URL url = new URL(urlStr.replaceAll(" ", "%20"));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.addRequestProperty("User-agent", "winterdev_io v0.1");
        
        conn.setRequestMethod("GET");
        conn.setUseCaches(false);


        conn.connect();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),Charset.forName("UTF-8")));
        String jsonStr = readAll(rd);
        JsonObject obj = new JsonParser().parse(jsonStr).getAsJsonObject();
        return obj;

    }
    
}
