/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analysis;

/**
 *
 * @author Martin
 */
public class CombatDpsAnalysis extends Analysis implements Plugable{

    public CombatDpsAnalysis(String _initiator, AnalysisHandler _instance) {
        super(_initiator, _instance);
    }

    @Override
    void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Plug(socket = {DamageAnalysis.class,CombatTimeAnalysis.class})
    @Override
    public void Plug(Result data, String Sender) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
