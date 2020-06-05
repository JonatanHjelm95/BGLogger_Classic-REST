/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventHandler;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Martin
 */
public class Input implements Event {

    private Date d;

    private MyEventType _type;
    String _date;
    String _time;
    String[] _data;

    public Input(String _date, String _time, String _data[], MyEventType _type) {
        this._type = _type;
        this._data = _data;
        this._date = _date;
        this._time = _time;
    }

    @Override
    public MyEventType getEventType() {
        return _type;
    }

    @Override
    public void setDate(Date date) {
        d = date; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTime(String time) {
        _time = time;
    }

    @Override
    public void setData(String[] data) {
        _data = data;
    }

    @Override
    public String getInitiator() {
        return "Drillenissen";
    }

    @Override
    public Date getDate() {
        try {
            return new SimpleDateFormat("dd/MM").parse(_date);
        } catch (ParseException ex) {
            Logger.getLogger(Input.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Date();
    }

    @Override
    public String[] getData() {
        return _data;
    }

    @Override
    public <T> List<T> getResult() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "Input{" + "d=" + d + ", _type=" + _type + ", _date=" + _date + ", _time=" + _time + ", _data=" + _data + '}';
    }
}
