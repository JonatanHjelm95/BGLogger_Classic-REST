/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analysis;

import EventHandler.*;
import GrafikObjects.DataLine;
import Listeners.Listener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 * @author Martin
 */
public class CombatTimeAnalysis extends Analysis {

    List<Event> Actions = new ArrayList<>();
    List<Event> TargetingActions = new ArrayList<>();
    List<Event> combined = new ArrayList<>();

    public CombatTimeAnalysis(String _initiator, AnalysisHandler _instance) {
        super(_initiator, _instance);
    }

    @Override
    public void Setup() {
        
        combined.addAll(Actions);
        combined.addAll(TargetingActions);
        combined = combined.stream()
                .sorted(Comparator.comparing(Event::getDate))
                .collect(Collectors.toList());

    }

    @Override
    void run() {

        DataLine combatStart = new DataLine();
        combatStart.data = new ArrayList<>();
        combatStart.Name = "Start of combat";
        DataLine combatEnd = new DataLine();
        combatEnd.data = new ArrayList<>();
        combatEnd.Name = "end of combat";
        Long t0 = combined.get(0).getDate().getTime();
        boolean inCombat = false;
        //Why not streams? Streams are not intended to compare elements to next element
        for (int i = 0; i < combined.size() - 1; i++) {
            if (!inCombat) {
                combatStart.data.add((double) TimeUnit.MILLISECONDS.toSeconds(combined.get(i).getDate().getTime()-t0));
                inCombat = !inCombat;
            } else {                
                if (5 < TimeUnit.MILLISECONDS.toSeconds(combined.get(i+1).getDate().getTime() - combined.get(i).getDate().getTime())) {
                    combatEnd.data.add((double) TimeUnit.MILLISECONDS.toSeconds(combined.get(i).getDate().getTime()-t0));
                    inCombat = !inCombat;
                }
            }
        }
        if(combatStart.data.size() != combatEnd.data.size()){
            combatEnd.data.add((double)TimeUnit.MILLISECONDS.toSeconds(combined.get(combined.size()-1).getDate().getTime()-t0));
        }
        ResultSet.addData(combatStart);
        ResultSet.addData(combatEnd);
             
        DataLine minAvgMax = new DataLine();
        minAvgMax.data = new ArrayList<>();
        minAvgMax.Name = "Shortest combat, Average combat and Longest Combat";
        List<Double> combatLengts =IntStream.range(0, combatStart.data.size())
                .mapToObj(i -> combatEnd.data.get(i) - combatStart.data.get(i))
                .collect(Collectors.toList());
        minAvgMax.data.add(combatLengts.get(combatLengts.indexOf(Collections.min(combatLengts))));
        minAvgMax.data.add(combatLengts.stream().mapToDouble(Double::doubleValue).sum()/combatLengts.size());
        minAvgMax.data.add(combatLengts.get(combatLengts.indexOf(Collections.max(combatLengts))));
        
        DataLine combatLen = new DataLine();
        combatLen.data = combatLengts;
        combatLen.Name = "Combat Length in Seconds";    
        ResultSet.addData(minAvgMax);
        ResultSet.addData(combatLen);

    }

    @Listener
    public void ListenBattle(Event evt) {
        if (evt.getInitiator().contains(initiator)) {
            Actions.add(evt);
        }
        try {
            if (evt.getData()[6].contains(initiator)) {
                TargetingActions.add(evt);
            }
        } catch (Exception e) {
        }

    }

}
