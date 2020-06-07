/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analysis;

import GrafikObjects.*;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        Plot dps = Damage.getPlots().get(Damage.getPlots().size()-1);
        DataLine cmbtStart = CombatTime.getData().get(0);
        DataLine cmbtEnd = CombatTime.getData().get(1);
        List<Double> dmgPrCombat = new ArrayList<>();
        for (int i = 0; i < cmbtEnd.data.size(); i++) {
            double sum = 0.0;
            for (int j = 0; j < dps.X.length; j++) {
                if(dps.X[j] < cmbtStart.data.get(i)){
                    continue;
                }
                if(dps.X[j] > cmbtEnd.data.get(i)){
                    break;
                }
                sum += dps.Y[j];                
            }
            dmgPrCombat.add(sum);            
        }
        
        Plot combatTimeDmg = new Plot();
        combatTimeDmg.Y = dmgPrCombat.toArray(new Double[dmgPrCombat.size()]);
        DataLine dl = CombatTime.getData().get( CombatTime.getData().size()-1);
        combatTimeDmg.X = cmbtStart.data.toArray(new Double[cmbtStart.data.size()]);
        combatTimeDmg.Name = "Damage given pr combat encounter";
        ResultSet.addPlot(combatTimeDmg);
        
        double sumTimeInCombat =0.0;
        double sumDps = 0.0;
        DataLine dpsdata = new DataLine();
        dpsdata.Name = "Total Damage, Total Time in Combat, DPS/secInCombat";
        for (int i = 0; i < combatTimeDmg.Y.length; i++) {
            sumDps+= combatTimeDmg.Y[i];
            sumTimeInCombat += dl.data.get(i);
        }
        
        List<Double> data = new ArrayList<>();
        data.add(sumDps);
        data.add(sumTimeInCombat);
        data.add(sumDps/sumTimeInCombat);
        dpsdata.data= data;
        ResultSet.addData(dpsdata);
            //List<Double> dpsPrCombat = IntStream.range(0, CombatTime.getData().get(0).data.size())
                   // .mapToObj(i->{
                   //     double sum = 0;
                        
                    //    return 0.0;
                   // })
                   // .collect(Collectors.toList());
                    
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
