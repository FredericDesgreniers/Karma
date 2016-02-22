/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.winterdev.server;



import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import io.winterdev.server.content.Content;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author frede
 */
public class DataManager {
    private Server server;
    Statement serverStm;
    Connection connection;
    public DataManager(Server server) throws SQLException{
        this.server = server;
        
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(Private.MYSQL_USER);
        dataSource.setPassword(Private.MYSQL_PASS);
        dataSource.setServerName("localhost");
        dataSource.setDatabaseName("karma");
        connection = dataSource.getConnection();
        serverStm = connection.createStatement();
        String sqlCreate = "CREATE TABLE IF NOT EXISTS content"
            + "  (id           int PRIMARY KEY  AUTO_INCREMENT,"
            + "   title              TEXT,"
            + "   reddit             TEXT,"
            + "   type               TEXT,"
            + "   url                TEXT)";
        
        serverStm.execute(sqlCreate);
        
        
    }
    public Statement getUniqueStatement() throws SQLException{
        return connection.createStatement();
    }
    public boolean checkContent(Statement statement, Content content) throws SQLException{
        
        
        ResultSet rs = statement.executeQuery("SELECT url FROM content WHERE url='"+content.getUrl()+"'");
        
        while(rs.next()){
            System.out.println("SQL FOUND:\t"+content.getTitle());
            return true;
        }
        return false;
        
    }
    public void insertContent(Statement statement, Content content) throws SQLException{
        PreparedStatement stat = connection.prepareStatement("INSERT INTO `content`(`title`, `url`, `reddit`, `type`) VALUES (?,?,?,?)");
        stat.setString(1, content.getTitle());
        stat.setString(2, content.getUrl());
        stat.setString(3, content.getReddit());
        stat.setString(4, "CONTENT");
        stat.executeUpdate();
    }
}
