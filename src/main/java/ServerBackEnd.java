import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
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
            String sb="";
            while ((line = reader.readLine())!= null) {
                sb = sb + line + "\n";
            }
            return sb;
        } catch (IOException ex) {
            Logger.getLogger(ServerBackEnd.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public String validate(String SubID, String QID) {
        try {
            getFileFromDB FDB = new getFileFromDB();
            FDB.getFile("/home",SubID);
            String loc = "/home" + SubID +".cpp";
            Process p = Runtime.getRuntime().exec("g++ "+loc+" -std=c++0x -o "+SubID);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String err = findError(reader);
            if(!"".equals(err)) {
                return err;
            }
            for(int i=1;;i++) {
                String inp = QID + "inp" + i;
                String out = QID + "out" + i;
                int a = FDB.getFile("/home",inp);
                a += FDB.getFile("/home",out);
                if(a!= 0) return "Accepted";
                Process q = Runtime.getRuntime().exec("./"+SubID + " < " + inp + " > " + SubID +"out");
                q.waitFor();
                Process r = Runtime.getRuntime().exec("cmp "+SubID+"out "+out);
                BufferedReader reader1 = new BufferedReader(new InputStreamReader(q.getInputStream()));
                String err1 = findError(reader1);
                if(!"".equals(err1)) {
                    return "Wrong answer on test case "+i+"\n";
                }
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
