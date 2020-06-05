/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analysis;

import EventHandler.Event;
import EventHandler.MyEventType;
import GrafikObjects.Plot;
import Listeners.Listener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    void setup() {
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
        final Long tSwing = Swings.get(0).getDate().getTime();
        SwingsPM = Swings.stream()
                .map(s->(s.getDate().getTime()-tSwing) )
                .map(Double::valueOf)
                .collect(Collectors.groupingBy(k -> k, Collectors.counting()));
        Plot plotSwingsPM = new Plot();
        plotSwingsPM.X = SwingsPM.keySet().toArray(new Double[SwingsPM.keySet().size()]);
        plotSwingsPM.Y = SwingsPM.values().toArray(new Double[SwingsPM.values().size()]);
        // Spells per minute
        Map<Double, Long> SpellsPM = new HashMap<>();
        final Long tSpell = Swings.get(0).getDate().getTime();
        SpellsPM = Spells.stream()
                .map(s->(s.getDate().getTime()-tSpell) )
                .map(Double::valueOf)
                .collect(Collectors.groupingBy(k -> k, Collectors.counting()));
        Plot plotSpellsPM = new Plot();
        plotSpellsPM.X = SpellsPM.keySet().toArray(new Double[SpellsPM.keySet().size()]);
        plotSpellsPM.Y = SpellsPM.values().toArray(new Double[SpellsPM.values().size()]);
        // Spells per minute
        Map<Double, Long> RangesPM = new HashMap<>();
        final Long tRanged = Ranged.get(0).getDate().getTime();
        SpellsPM = Ranged.stream()
                .map(s->(s.getDate().getTime()-tSpell) )
                .map(Double::valueOf)
                .collect(Collectors.groupingBy(k -> k, Collectors.counting()));
        Plot plotRangesPM = new Plot();
        plotRangesPM.X = SpellsPM.keySet().toArray(new Double[SpellsPM.keySet().size()]);
        plotRangesPM.Y = SpellsPM.values().toArray(new Double[SpellsPM.values().size()]);
        
        int swingSum = Swings.stream().mapToInt(s->Integer.parseInt(s.getData()[25])).sum();
        int spellSum = Spells.stream().mapToInt(s->Integer.parseInt(s.getData()[26])).sum();
        int rangedSum = Ranged.stream().mapToInt(s->Integer.parseInt(s.getData()[28])).sum();
        
        Result results = new Result();
        instance.submitResult(results, this.getClass());
    }

    @Listener(event = MyEventType.SPELL_DAMAGE)
    public void SpellDamage(Event evt) {
        if (evt.getInitiator().equals(this.initiator)) {
            Spells.add(evt);
        }
    }

    @Listener(event = MyEventType.SWING_DAMAGE)
    public void SwingDamage(Event evt) {
        if (evt.getInitiator().equals(this.initiator)) {
            Swings.add(evt);
        }
    }

    @Listener(event = MyEventType.RANGE_DAMAGE)
    public void RangedDamage(Event evt) {
        if (evt.getInitiator().equals(this.initiator)) {
            Ranged.add(evt);
        }
    }

}
