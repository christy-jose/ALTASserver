
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author christy
 */
public class UpdateModule {
     Connection conn;
    void UpdateModule(){
        conn = null;
    }
    void setconn(Connection con){
        conn=con;
    }
    int changePassword(String user, String Pass) {
        PreparedStatement prestmt = null;
        int ret = 0;
        try {
            conn.setAutoCommit(false);
            String query = "Update  userlist set password = ? where username = ?";
            //System.out.println(query);
            prestmt = conn.prepareStatement(query);
            prestmt.setString(1,Pass);
            prestmt.setString(2,user);
            ret = prestmt.executeUpdate();
            conn.commit();
            //print(res);
            System.out.println("Statement excetued Successfully");
            
        } catch (SQLException sqle){
            System.out.println("Statement not excetued");
            ///*
            try {
                conn.rollback();
                System.out.println("Rollback successful");
                 
            } catch (SQLException sqle1){
                System.out.println("Error");
                System.out.println(sqle1.getMessage());
            }
            //*/
            System.out.println(sqle.getMessage());
            
        } finally {
            try {
                conn.setAutoCommit(true);
                if(prestmt!=null)
                    prestmt.close();
            } catch (SQLException sqle){
                System.out.println("Error");
                System.out.println(sqle.getMessage());
            }
        }
        return ret; 
    }
    int updateSubmission(String status,int score,String id) {
        PreparedStatement prestmt = null;
        int ret = 0;
        try {
            conn.setAutoCommit(false);
            String query = "Update  submission set score = ? status = ? where id = ?";
            //System.out.println(query);
            prestmt = conn.prepareStatement(query);
            prestmt.setInt(1,score);
            prestmt.setString(2,status);
            prestmt.setString(3,id);
            ret = prestmt.executeUpdate();
            conn.commit();
            //print(res);
            System.out.println("Statement excetued Successfully");
            
        } catch (SQLException sqle){
            System.out.println("Statement not excetued");
            ///*
            try {
                conn.rollback();
                System.out.println("Rollback successful");
                 
            } catch (SQLException sqle1){
                System.out.println("Error");
                System.out.println(sqle1.getMessage());
            }
            //*/
            System.out.println(sqle.getMessage());
            
        } finally {
            try {
                conn.setAutoCommit(true);
                if(prestmt!=null)
                    prestmt.close();
            } catch (SQLException sqle){
                System.out.println("Error");
                System.out.println(sqle.getMessage());
            }
        }
        return ret;
    }
}
