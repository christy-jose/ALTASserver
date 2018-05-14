import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author devika
 */
public class ServerBackEnd {
    public String findError(BufferedReader reader) {
        try {
            String line = "";
            StringBuilder sb= new StringBuilder();
            while ((line = reader.readLine())!= null) {
                sb.append(line + "\n");
            }
            System.out.println(new String(sb));
            return new String(sb);
        } 
        catch (IOException ex) {
            Logger.getLogger(ServerBackEnd.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public String validate(String SubID, String QID) {
        StringBuilder sb = new StringBuilder();
        try {
            Process p = Runtime.getRuntime().exec("pwd");
            //System.out.println("Successful");
            p.waitFor();
            ///*
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";			
            while ((line = reader.readLine())!= null) {
                sb.append(line);
            }
            System.out.println(sb.toString());
           //
        }
        catch( Exception e) {
            System.out.println( e.getMessage());
            return new String("path error");
        }
        //*/
        sb.append("/Dumb");
        String pat = new String(sb);
        File wdir = new File(pat);
        Process p;
        boolean NTLE;
        BufferedReader reader;
        try {
            getFileFromDB FDB = new getFileFromDB();
            int ret = FDB.getFile(new String(sb),SubID,1);
            if(ret!=0) {
                return new String("File not feteched from DB");
            }
            System.out.println("compiling "+SubID+".cpp");
            p = Runtime.getRuntime().exec("g++ -std=c++0x "+SubID+".cpp -o "+SubID,null,wdir);
            System.out.println("Done");
            p.waitFor();
            reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String err = findError(reader);
            if(!err.equals("")) {
                return new String("Compilation Error");
            }
            System.out.println("Compilation Successful");
            int a = 0 ;
            for(int i=1;;i++) {
                System.out.println("Testing with testcase "+i);
                String inp = QID + "inp" + i;
                String out = QID + "out" + i;
                System.out.println("input  file name = "+inp);
                System.out.println("output file name = "+out);
                a =  FDB.getFile(new String(sb),inp,0);
                System.out.println("Result of fetching input "+a);
                a += FDB.getFile(new String(sb),out,0);
                System.out.println("Result of fetching output "+a);
                if(a!= 0) {
                    System.out.println("No more testcases. Accepted");
                    return "Accepted";
                }
                System.out.println("Running testcase "+i);
                String[] cmd = {"/bin/sh","-c","./"+SubID+" < "+inp+" > " + SubID +"out"};
                p = Runtime.getRuntime().exec(cmd,null,wdir);
                System.out.println("Done");
                try {
                    NTLE = p.waitFor(2, TimeUnit.SECONDS);
                }
                catch (InterruptedException ie) {
                    System.out.println(ie.getMessage());
                    return new String("Server Error");
                }
                if(!NTLE) {
                    System.out.println("Time Limit Exceded");
                    return new String("Time Limit Exceded");
                }
                reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                err = findError(reader);
                if(!err.equals("")) {
                    return new String("Server Error");
                }
                System.out.println("Comparing output with output file");
                p = Runtime.getRuntime().exec("cmp "+SubID+"out "+out,null,wdir);
                System.out.println("Done");
                p.waitFor();
                reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                err = findError(reader); 
                if(!err.equals("")) {
                    return "Wrong answer on test case "+i+"\n";
                }
                System.out.println("output is valid");
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerBackEnd.class.getName()).log(Level.SEVERE, null, ex);
            return "IOException";
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerBackEnd.class.getName()).log(Level.SEVERE, null, ex);
            return "InterruptedException";
        }
    }
}
