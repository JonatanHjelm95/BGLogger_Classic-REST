/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventHandler;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Martin
 */
public class Duel implements Event {
    MyEventType _event = MyEventType.ANY;
    private Date d;
    
    @Override
    public MyEventType getEventType() {
        return _event;
    }


    @Override
    public <T> List<T> getResult() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



    @Override
    public void setTime(String time) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setData(String[] data) {
        
    }
    public String getInitiator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setDate(Date date) {
        d = date; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Date getDate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String[] getData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
}
