/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author christy
 */
import java.io.*;
import java.util.Properties;
public class DBDetails {
    String phost,pport,pdbname,puser,ppass;
    String mhost,mport,mdbname,muser,mpass;
    private File DBConfig = new File("/home/christy/NetBeansProjects/ALTASserver/DBconfig.properties");
    private Properties configProps;
    public int fetchDetails() {
        InputStream inputStream = null;
        configProps = new java.util.Properties();
        /*
        try {
            Process p = Runtime.getRuntime().exec("pwd");
            //System.out.println("Successful");
            p.waitFor();
            ///*
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = "";			
            while ((line = reader.readLine())!= null) {
                sb.append(line + "\n");
                System.out.println(sb.toString());
            }
           //
        }
        catch( Exception e) {
            System.out.println( e.getMessage());
        }
        //*/
        //System.out.println("end");
        try {
            inputStream = new FileInputStream(DBConfig);
            configProps.load(inputStream);
        }
        catch (FileNotFoundException fnf) {
            return 1;
        }
        catch (IOException e ){
            return 2;
        }
        finally {
            if(inputStream!=null) {
                try {
                    inputStream.close();
                }
                catch ( IOException e ){
                    return 2;
                }
            }    
        }
        phost = configProps.getProperty("phost");
        pport = configProps.getProperty("pport");
        pdbname = configProps.getProperty("pdbname");
        puser = configProps.getProperty("puser");
        ppass = configProps.getProperty("ppass");
        mhost = configProps.getProperty("mhost");
        mport = configProps.getProperty("mport");
        mdbname = configProps.getProperty("mdbname");
        muser = configProps.getProperty("muser");
        mpass = configProps.getProperty("mpass");
        return 0;
    }
    public int pushDetails() {
        configProps = new java.util.Properties();
        configProps.setProperty("phost", phost);
	configProps.setProperty("pport", pport);
        configProps.setProperty("pdbname",pdbname);
	configProps.setProperty("puser", puser);
	configProps.setProperty("ppass", ppass);
        configProps.setProperty("mhost", mhost);
	configProps.setProperty("mport", mport);
        configProps.setProperty("mdbname",mdbname);
	configProps.setProperty("muser", muser);
	configProps.setProperty("mpass", mpass);
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(DBConfig);
            configProps.store(outputStream, "Database Details");
            
        }
        catch (FileNotFoundException fnf)  {
            return 1;
        }
        catch (IOException e) {
            return 2;
        }
        finally {
            if(outputStream!=null) {
                try {
                    outputStream.close();
                }
                catch ( IOException e ){
                    return 2;
                }
            }    
        }
        return 0;
    }
    public static void main(String[] args ) {
       DBDetails DB = new DBDetails();
       DB.fetchDetails();
       System.out.println(DB.phost);
    }
}
