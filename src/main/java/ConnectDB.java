/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author christy
 */

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import java.sql.*;
import java.util.*;
import java.text.*;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.postgresql.util.PSQLException;
public class ConnectDB {
    private Connection conn;
    void ConnectDB() {
        conn = null;
    }
    Connection getconn() {
        return conn;
    }
    public int connect(){
        try {
            Class.forName("org.postgresql.Driver");
            DBDetails DB = new DBDetails();
            int ret = DB.fetchDetails();
            if(ret!=0) {
                System.out.println("No DBDetails");
                return 1;
            }
            conn = DriverManager.getConnection("jdbc:postgresql://"+DB.phost+":"+DB.pport+"/"+DB.pdbname,DB.puser, DB.ppass);
            System.out.println("Opened database successfully");
        } catch (PSQLException sqle) {
            System.out.println("Could not connect to Database");
            System.out.println(sqle.getMessage());
            return 1;
        } catch( ClassNotFoundException cnfe) {
            System.out.println("jdbc driver not found");
            System.out.println(cnfe.getMessage());
            return 2;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not connect to Database");
            return 3;
        }
        return 0;
    }
    public DB connectMongo(){
        DB db = null;
        try {
            DBDetails DBdel = new DBDetails();
            int ret = DBdel.fetchDetails();
            if(ret!=0) {
                System.out.println("No DBDetails");
                return null;
            }
            MongoClient mongo = new MongoClient(new MongoClientURI("mongodb://"+DBdel.muser+":"+DBdel.mpass+"@"+DBdel.mhost+":"+DBdel.mport+"/"+DBdel.mdbname));
            db = mongo.getDB(DBdel.mdbname);
        }
        catch (UnknownHostException uhe) {
            System.out.println("Error");
        }
        return db;
    }
    public int disconnect() {
        try {
            conn.close();
            System.out.println("Closed connection sucessfully");
        }
        catch (SQLException sqle){
            System.out.println("Could not disconnect form Database");
            System.out.println(sqle.getMessage());
            return 1;
        }
        return 0;
    }
    
}
