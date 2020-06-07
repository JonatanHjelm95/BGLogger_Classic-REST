/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analysis;

import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Martin
 */
public class CombatDpsAnalysis extends Analysis implements Plugable {

    private boolean waitingForData = true;
    Result Damage;
    Result CombatTime;

    public CombatDpsAnalysis(String _initiator, AnalysisHandler _instance) {
        super(_initiator, _instance);
    }

    @Override
    public void Setup() {
        while (waitingForData) {
            try {
                sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(CombatDpsAnalysis.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    void run() {

    }

    @Plug(socket = {DamageAnalysis.class, CombatTimeAnalysis.class})
    @Override
    public void Plug(Result data, String Sender) {
        if (Sender.contains("CombatTime")) {
            CombatTime = data;
        } else {
            Damage = data;
        }
        waitingForData = !(Damage != null && CombatTime != null);
    }

}
