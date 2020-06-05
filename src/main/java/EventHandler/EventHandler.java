/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventHandler;

import Listeners.*;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton event handler. Functions as both que and base handler.
 *
 * @author Martin
 */
public class EventHandler {

    private static EventHandler Instance = null;

    public boolean Finished = false;

    private Map< MyEventType, List<ListenerHolder>> Listeners = new HashMap<>();
    private boolean endOfFile = false;
    private boolean eventsHandled = false;
    public EventHandler() {
        
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Runnable runnableTask = () -> {
            try {
                while (!endOfFile || eventQue.size()>0) {
                    Event _event = getEvent();
                    invokeListeners(_event);
                }
                eventsHandled =true;
            } catch (Exception e) {

            }
        };
        executor.submit(runnableTask);
        executor.shutdown();
    }
    public void endFile(){
        endOfFile =true;
    }
    public boolean eventlogComplete(){
        return eventsHandled;
    }
    
    /*
    public static EventHandler getInstance() {
        if (Instance == null) {
            Instance = new EventHandler();
        }

        return Instance;
    }*/

    private List<Event> eventQue = new ArrayList<>();
    ReentrantLock lock = new ReentrantLock();

    public void addEvent(Event _event) {
        
        lock.tryLock();
        try {
            System.out.println("adding event of type:"+ _event.getEventType());
            eventQue.add(_event);
        } finally {
            lock.unlock();
        }
    }

    private Event getEvent() throws Exception {
        while (eventQue.size() <= 0) {
            sleep(1);
        }
        lock.tryLock();
        try {
            Event _event = eventQue.get(0);
            eventQue.remove(0);
            return _event;
        } finally {
            lock.unlock();

        }
    }

    public void addListener(MyEventType _type, ListenerHolder listener) {
        List<ListenerHolder> _listeners = Listeners.get(_type);
        if (_listeners == null) {
            List<ListenerHolder> _list = new ArrayList<>();
            _list.add(listener);
            Listeners.put(_type, _list);
            System.out.println("Listener added and list allocated.");

        } else {
            _listeners.add(listener);
            System.out.println("Listener added.");
        }
    }

    private void invokeListeners(Event _event) {
        ExecutorService executor = Executors.newCachedThreadPool();
        if (Listeners.get(MyEventType.ANY) != null) {
            Runnable task0 = () -> {
                //List<ListenerHolder> _listeners = Listeners.get(MyEventType.ANY);
                for (ListenerHolder _listener : Listeners.get(MyEventType.ANY)) {
                    _listener.invoke(_event);
                    System.out.println("invoked a listener: " + _listener.getClass().getName());
                }
            };
            executor.submit(task0);
        }
       
        if (Listeners.get(_event.getEventType())!= null) {
            Runnable task1 = () -> {
                //List<ListenerHolder> _listeners = Listeners.get(_event.getEventType());
                for (ListenerHolder _listener : Listeners.get(_event.getEventType())) {
                    _listener.invoke(_event);
                    System.out.println("invoked a listener: " + _listener.getClass().getName());
                }
            };
            executor.submit(task1);
        }
        executor.shutdown();
    }

}
