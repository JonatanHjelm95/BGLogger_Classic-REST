/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analysis;

import EventHandler.Event;
import EventHandler.MyEventType;
import GrafikObjects.DataLine;
import GrafikObjects.Plot;
import Listeners.Listener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.*;

/**
 *
 * @author jonab
 */
public class DamageAnalysis extends Analysis {

    List<Event> Spells = new ArrayList<>();
    List<Event> Swings = new ArrayList<>();
    List<Event> Ranged = new ArrayList<>();

    public DamageAnalysis(String _initiator, AnalysisHandler _instance) {
        super(_initiator, _instance);
    }

    @Override
    public void Setup() {
        Swings = Swings.stream()
                .sorted(Comparator.comparing(Event::getDate))
                .filter(evt -> evt.getEventType() == MyEventType.SWING_DAMAGE)
                .collect(Collectors.toList());
        Spells = Spells.stream()
                .sorted(Comparator.comparing(Event::getDate))
                .filter(evt -> evt.getEventType() == MyEventType.SPELL_DAMAGE)
                .collect(Collectors.toList());
        Ranged = Ranged.stream()
                .sorted(Comparator.comparing(Event::getDate))
                .filter(evt -> evt.getEventType() == MyEventType.RANGE_DAMAGE)
                .collect(Collectors.toList());
    }

    @Override
    void run() {
        // Swings per minute
        Map<Double, Long> SwingsPM = new HashMap<>();
        if (Swings.size() > 0) {
            final Long tSwing = Swings.get(0).getDate().getTime();
            SwingsPM = Swings.stream()
                    .map(s -> (s.getDate().getTime() - tSwing))
                    .map(ms -> TimeUnit.MILLISECONDS.toMinutes(ms))
                    .map(Double::valueOf)
                    .collect(Collectors.groupingBy(k -> k, Collectors.counting()));
            SwingsPM = new TreeMap<Double, Long>(SwingsPM);
            Plot plotSwingsPM = new Plot();
            plotSwingsPM.X = SwingsPM.keySet().toArray(new Double[SwingsPM.keySet().size()]);
            List<Double> l = SwingsPM.values().stream().map(s -> (double) s).collect(Collectors.toList());
            plotSwingsPM.Y = l.toArray(new Double[l.size()]);
            plotSwingsPM.Name = "Mele Swings pr minute";
            ResultSet.addPlot(plotSwingsPM);
        }

        // Spells per minute    
        if (Spells.size() > 0) {
            Map<Double, Long> SpellsPM = new HashMap<>();
            final Long tSpell = Spells.get(0).getDate().getTime();
            SpellsPM = Spells.stream()
                    .map(s -> (s.getDate().getTime() - tSpell))
                    .map(ms -> TimeUnit.MILLISECONDS.toMinutes(ms))
                    .map(Double::valueOf)
                    .collect(Collectors.groupingBy(k -> k, Collectors.counting()));
            SpellsPM = new TreeMap<Double, Long>(SpellsPM);
            Plot plotSpellsPM = new Plot();
            plotSpellsPM.X = SpellsPM.keySet().toArray(new Double[SpellsPM.keySet().size()]);
            List<Double> l2 = SpellsPM.values().stream().map(s -> (double) s).collect(Collectors.toList());
            plotSpellsPM.Y = l2.toArray(new Double[l2.size()]);
            plotSpellsPM.Name = "Damaging Spells pr minute";
            ResultSet.addPlot(plotSpellsPM);
        }

        // Ranges per minute
        if (Ranged.size() > 0) {
            Map<Double, Long> RangesPM = new HashMap<>();
            final Long tRanged = Ranged.get(0).getDate().getTime();
            RangesPM = Ranged.stream()
                    .map(s -> (s.getDate().getTime() - tRanged))
                    .map(ms -> TimeUnit.MILLISECONDS.toMinutes(ms))
                    .map(Double::valueOf)
                    .collect(Collectors.groupingBy(k -> k, Collectors.counting()));
            RangesPM = new TreeMap<Double, Long>(RangesPM);
            Plot plotRangesPM = new Plot();
            plotRangesPM.X = RangesPM.keySet().toArray(new Double[RangesPM.keySet().size()]);
            List<Double> l3 = RangesPM.values().stream().map(s -> (double) s).collect(Collectors.toList());
            plotRangesPM.Y = l3.toArray(new Double[l3.size()]);
            plotRangesPM.Name = "Ranged Attacks minute";
            ResultSet.addPlot(plotRangesPM);
        }
        DataLine SumRanged = new DataLine();
        DataLine SumSpell = new DataLine();
        DataLine SumSwing = new DataLine();

        try {
            SumRanged.Name = "Sum of ranged dmg";
            SumRanged.datapoint = Swings.stream().mapToDouble(s -> Double.parseDouble(s.getData()[25])).sum();

            SumSpell.Name = "Sum of spell dmg";
            SumSpell.datapoint = Spells.stream().mapToDouble(s -> Double.parseDouble(s.getData()[26])).sum();
            SumSwing.Name = "Sum of autoAttack dmg";
            SumSwing.datapoint = Ranged.stream().mapToDouble(s -> Double.parseDouble(s.getData()[28])).sum();
            ResultSet.addData(SumSwing);
            ResultSet.addData(SumRanged);
            ResultSet.addData(SumSpell);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void shutdown() {
        List<Event> combined = new ArrayList<>();
        Plot dpsTotal = new Plot();
        if (Swings.size() > 0) {
            combined.addAll(Swings);
        }
        if (Spells.size() > 0) {
            combined.addAll(Spells);
        }
        if (Ranged.size() > 0) {
            combined.addAll(Ranged);
        }
        dpsTotal.X = new Double[combined.size()];
        dpsTotal.Y = new Double[combined.size()];
        dpsTotal.Name = "All Dmg with timestamp in seconds";
        combined = combined.stream()
                .sorted(Comparator.comparing(Event::getDate))
                .collect(Collectors.toList());
        for (int i = 0; i < combined.size(); i++) {
            dpsTotal.X[i] = (double) TimeUnit.MILLISECONDS.toSeconds(combined.get(i).getDate().getTime() - combined.get(0).getDate().getTime());
            String arr[] = combined.get(i).getData();
            for (int j = arr.length - 1; j > 0; j--) {
                try {
                    Double val = Double.parseDouble(arr[j]);
                    if (val > 10 && val < 5000) {
                        dpsTotal.Y[i] = val;
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            if(dpsTotal.Y[i] == null) dpsTotal.Y[i] = 0.0;
        }
        ResultSet.addPlot(dpsTotal);
    }

    @Listener(event = MyEventType.SPELL_DAMAGE)
    public void SpellDamage(Event evt) {
        if (evt.getInitiator().contains(this.initiator)) {
            Spells.add(evt);
        }
    }
    @Listener(event = MyEventType.SPELL_PERIODIC_DAMAGE)
    public void SpellPeriodicDamage(Event evt) {
        if (evt.getInitiator().contains(this.initiator)) {
            Spells.add(evt);
        }
    }
    

    @Listener(event = MyEventType.SWING_DAMAGE)
    public void SwingDamage(Event evt) {
        if (evt.getInitiator().contains(this.initiator)) {
            Swings.add(evt);
        }
    }

    @Listener(event = MyEventType.RANGE_DAMAGE)
    public void RangedDamage(Event evt) {
        if (evt.getInitiator().contains(this.initiator)) {
            Ranged.add(evt);
        }
    }

}
