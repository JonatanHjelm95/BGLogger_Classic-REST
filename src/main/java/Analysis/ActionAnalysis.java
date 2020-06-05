/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analysis;

import EventHandler.*;
import GrafikObjects.DataLine;
import GrafikObjects.Plot;
import Listeners.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Martin
 */
public class ActionAnalysis extends Analysis{

    List<Event> Attempts = new ArrayList<>();
    List<Event> Succeses = new ArrayList<>();
    List<Event> Fails = new ArrayList<>();
    Result results = new Result();

    public ActionAnalysis(String _initiator, AnalysisHandler _instance) {
        super(_initiator, _instance);
    }




    @Override
    public void Setup() {
        System.out.println("Starting analysis: " + this.getClass().getName());
        
        Attempts = Attempts.stream()
                .sorted(Comparator.comparing(Event::getDate))
                .filter(evt -> !Arrays.asList(evt.getData()).contains("Dazed"))
                .collect(Collectors.toList());
        Succeses = Succeses.stream()
                .sorted(Comparator.comparing(Event::getDate))
                .filter(evt -> !Arrays.asList(evt.getData()).contains("Dazed"))
                .collect(Collectors.toList());
        Fails = Fails.stream()
                .sorted(Comparator.comparing(Event::getDate))
                .collect(Collectors.toList());
    }

    @Override
    void run() {
        
        double succesPercent = Succeses.size() / Attempts.size();
        try {
            double succesFailScore = Succeses.size() / Fails.size();
        } catch (Exception e) {
            double succesFailScore = 0;
        }
        
        Long t0 = Attempts.get(0).getDate().getTime();
        
        Map <Double,Long> resAPM = new HashMap<>();
        resAPM = Attempts.stream().map(s->(s.getDate().getTime()-t0) )
                .map(ms -> TimeUnit.MILLISECONDS.toMinutes(ms))
                .map(Double::valueOf)              
                .collect(Collectors.groupingBy(k -> k, Collectors.counting()));               
        Plot plotAPM = new Plot();
        plotAPM.X = resAPM.keySet().toArray(new Double[resAPM.keySet().size()]);
        
        List<Double> l = resAPM.values().stream().map(s-> (double)s).collect(Collectors.toList());        
        plotAPM.Y = l.toArray(new Double[l.size()]);
        plotAPM.Name ="Actions pr Minute";
        
        Map <Double,Long> resFPM = new HashMap<>();
        resFPM = Fails.stream().map(s->(s.getDate().getTime()-t0) )
                .map(ms -> TimeUnit.MILLISECONDS.toMinutes(ms))
                .map(Double::valueOf)              
                .collect(Collectors.groupingBy(k -> k, Collectors.counting()));               
        Plot plotFPM = new Plot();
        plotFPM.X = resAPM.keySet().toArray(new Double[resFPM.keySet().size()]);
        List<Double> l2 = resFPM.values().stream().map(s-> (double)s).collect(Collectors.toList());        
        plotAPM.Y = l2.toArray(new Double[l.size()]);
        plotFPM.Name = "Failed/interuptet casts pr minute";
        
        Map <Double,Long> resCPM = new HashMap<>();
        resCPM = Fails.stream().map(s->(s.getDate().getTime()-t0) )
                .map(ms -> TimeUnit.MILLISECONDS.toMinutes(ms))
                .map(Double::valueOf)              
                .collect(Collectors.groupingBy(k -> k, Collectors.counting()));               
        Plot plotCPM = new Plot();
        plotCPM.X = resCPM.keySet().toArray(new Double[resFPM.keySet().size()]);
        List<Double> l3 = resCPM.values().stream().map(s-> (double)s).collect(Collectors.toList());        
        plotAPM.Y = l3.toArray(new Double[l.size()]);
        plotCPM.Y = resCPM.values().toArray(new Double[resFPM.values().size()]);
        plotCPM.Name="Succesfull casts pr minute";
        
        DataLine d1 = new DataLine();
        DataLine d2 = new DataLine();
        d1.datapoint = succesPercent;
        d1.Name="Succes%";
        //d2.datapoint = succesFailScore;
        d2.Name="SpellCast fail score";
        
        results.addData(d1);
        results.addData(d2);
        
        results.addPlot(plotAPM);
        //results.addPlot(plotFPM);
        //results.addPlot(plotCPM);
        

        instance.submitResult(results, this.getClass() );
        
    }

    @Listener(event = MyEventType.SPELL_CAST_START)
    public void castAttempt(Event evt) {
        if (evt.getInitiator().equals(this.initiator)) {
            Attempts.add(evt);
        }
    }

    @Listener(event = MyEventType.SPELL_AURA_APPLIED)
    public void SuccesAura(Event evt) {
        if (evt.getInitiator().equals(this.initiator)) {
            Succeses.add(evt);
            Attempts.add(evt);
        }
    }

    @Listener(event = MyEventType.SPELL_CAST_SUCCESS)
    public void SuccesCast(Event evt) {
        if (evt.getInitiator().equals(this.initiator)) {
            Succeses.add(evt);
        }
    }

    @Listener(event = MyEventType.SPELL_CAST_FAILED)
    public void FailedCast(Event evt) {
        if (evt.getInitiator().equals(this.initiator)) {
            Fails.add(evt);
        }
    }
}
