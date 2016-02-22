/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.winterdev.server;



import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
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
            + "   title            TEXT,"
            + "   reddit          TEXT,"
            + "   type           TEXT,"
            + "   specificKey           TEXT)";
        
        serverStm.execute(sqlCreate);
        
        
    }
    
    public Statement getUniqueStatement() throws SQLException{
        return connection.createStatement();
    }
}
