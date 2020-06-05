/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analysis;

import static java.lang.Thread.sleep;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Martin
 */
public class ChainedAnalysis extends Analysis implements Plugable{
    private boolean iWait = true;
    
    public ChainedAnalysis(String _initiator, AnalysisHandler _instance) {
        super(_initiator, _instance);
    }



    @Override
    void run() {
        while(iWait){
            try {
                sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(ChainedAnalysis.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Plug(socket = {ActionAnalysis.class,DamageAnalysis.class})
    @Override
    public void Plug(Result data,String Sender) {
        System.out.println("recieved Data from: "+Sender+" now finishing up my stuff");
        iWait=false;
                
    }
    
}
