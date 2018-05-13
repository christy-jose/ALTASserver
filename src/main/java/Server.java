/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author christy
 */
public class Server {
    public static void Judge (String subid,String testid,String qid) {
        System.out.println("Judging "+subid+" of question "+qid+" of test "+testid);
        ServerBackEnd SB = new ServerBackEnd();
        String res = SB.validate(subid, qid);
        ConnectDB DB =  new ConnectDB();
        DB.connect();
        UpdateModule UP = new UpdateModule();
        UP.setconn(DB.getconn());
        int score = 0;
        if(res.equals("Accepted")) {
            score = 100;
        }
        int ret = UP.updateSubmission(res, score, subid);
        if(ret!=1) {
            System.out.println("Error in judge or DB");
        }
        DB.disconnect();
    }
    
}
