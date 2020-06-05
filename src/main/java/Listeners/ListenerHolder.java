/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listeners;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Martin
 */
public class ListenerHolder {

    private Method method;
    private Object obj;

    public ListenerHolder(Method _method, Object _obj) {
        this.method = _method;
        this.obj = _obj;
    }

    public void invoke(Object params) {
        
        try {
            if (params == null|| method.getParameterCount() ==0 ) {
                method.invoke(obj);
            } else {
                method.invoke(obj, params);
            }
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ListenerHolder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ListenerHolder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ListenerHolder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
