/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Listeners;

import EventHandler.MyEventType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author Martin
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Listener {
    MyEventType event() default MyEventType.ANY;
}
