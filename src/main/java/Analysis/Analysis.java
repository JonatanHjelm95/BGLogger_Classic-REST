/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Analysis;

import EventHandler.Event;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Martin
 */
enum AnalasysTypes {
    DPS,
    Somtehin
}

public abstract class Analysis {

    final String initiator;
    
    final AnalysisHandler instance;

    public Analysis(String _initiator,AnalysisHandler _instance) {
        initiator = _initiator;
        instance = _instance;
    }

    private Runnable AsRunnable() {
        System.out.println("creating runnable for " + this.getClass().getName());
        Runnable runnableTask = () -> {
            Setup();
            run();
            shutdown();
        };
        return runnableTask;
    }

    public void Setup() {
        System.out.println("Starting analasys: "+ this.getClass().getName());
    }

    ;

    abstract void run();
    
    public void shutdown() {
        System.out.println("ending analysis: " + this.getClass().getName() );
    }

    ;

    public void start() {
        ExecutorService executor = Executors.newCachedThreadPool();
        Runnable a = AsRunnable();
        executor.submit(a);
        System.out.println("task Submittet for " +this.getClass().getName());
        executor.shutdown();
    }

    Comparator<Event> timestamp = new Comparator<Event>() {
        @Override
        public int compare(Event o1, Event o2) {
            Date timestamp1 = o1.getDate();
            Date timestamp2 = o2.getDate();
            return timestamp1.compareTo(timestamp2);
        }
    };

}
