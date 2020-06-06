/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analysis;

import EventHandler.*;
import GrafikObjects.*;
import FileHandler.*;
import Listeners.Listener;
import Listeners.ListenerHolder;
import RealTime.CettiaBootstrap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.cettia.Server;
import static io.cettia.ServerSocketPredicates.tag;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Martin
 */
public class AnalysisHandler {

    private List<Analysis> analysis = new ArrayList<>();
    private EventHandler eh;
    private String initiator;
    
    public String getSubmittingPlayer() {
        return "";
    }
    public AnalysisHandler(String initiator, String data) throws IOException, InterruptedException{
        eh = new EventHandler();
        this.initiator = initiator;
        analysis.add(new ActionAnalysis(initiator, this));
        analysis.add(new DamageAnalysis(initiator, this));
        analysis.add(new ChainedAnalysis(initiator, this));
        AddListeners();
        FileHandler.FileReaderFromBase64(eh, data);
        while (!eh.eventlogComplete()) {            
            sleep(100);
        }
        StartAnalysis();
        //StartAnalysis();   
    }
    
    private AnalysisHandler() {
    }

    private void AddListeners() {
        for (Analysis analysi : analysis) {
            Class obj = analysi.getClass();
            for (Method method : obj.getMethods()) {
                if (method.isAnnotationPresent(Listener.class)) {
                    Listener l = method.getAnnotation(Listener.class);
                    eh.addListener(l.event(), new ListenerHolder(method, analysi));
                }
            }
        }
    }

    public void StartAnalysis() {
        System.out.println("Event Que Processed. Starting Analysis:");
        analysis.stream()
                .forEach(Analysis::start);
        
    }

    public void AddAnalysis(Analysis _analysi) {
        analysis.add(_analysi);
    }

    void returnPlot(Plot p){
        
    }
    
    void submitResult(Result res,Class<?> sender) {
        System.out.println("Result submitted from: " +sender.getName());
        //TODO hand to frontend
        Gson GSON = new GsonBuilder().setPrettyPrinting().create();
        Server S = CettiaBootstrap.getServer();
        Map<String, Object> output = new LinkedHashMap<>();
        output.put("sender", sender.getName());
        output.put("text", GSON.toJson(res));
        
        S.find(tag("channel:log")).send("message", output);
        
        
        analysis.stream()
                .filter(a -> Arrays.asList(a.getClass().getInterfaces()).contains(Plugable.class))
                .forEach(a -> {
                    Class c = a.getClass();
                    Arrays.asList(c.getMethods()).stream()
                            .filter(m -> m.isAnnotationPresent(Plug.class))
                            .filter(m -> Arrays.asList(m.getAnnotation(Plug.class).socket()).contains(sender))
                            .forEach(m -> {
                                try {
                                    m.invoke(a, res,sender.getName()); //TODO fixx this plox
                                } catch (IllegalAccessException ex) {
                                    Logger.getLogger(AnalysisHandler.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IllegalArgumentException ex) {
                                    Logger.getLogger(AnalysisHandler.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (InvocationTargetException ex) {
                                    Logger.getLogger(AnalysisHandler.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            });
                });
    }
}
