/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainTesting;

import Analysis.ActionAnalysis;
import Analysis.AnalysisHandler;
import EventHandler.*;
import Listeners.Listener;
import Listeners.ListenerHolder;

import java.io.File;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author jonab
 */
public class main {

    private static int test = 0;

    public static void main(String[] args) throws IOException {

        
        String path = "C:\\Users\\Jutsu\\Downloads\\WoWCombatLog.txt";
        
        String _date = "5/21 11:09:48.406";
        
        
        //String path = "C:\\Users\\jonab\\.ssh\\4sem\\advProgramming\\BGLogger_Classic\\WowCombatLog.txt";
        //String data = "Ni8zIDE1OjQ2OjQ3LjMxOCAgQ09NQkFUX0xPR19WRVJTSU9OLDksQURWQU5DRURfTE9HX0VOQUJMRUQsMSxCVUlMRF9WRVJTSU9OLDEuMTMuNCxQUk9KRUNUX0lELDIKNi8zIDE1OjQ3OjAyLjA0MyAgU1BFTExfQVVSQV9BUFBMSUVELFBsYXllci00NDY3LTAwNTI2Q0EwLCJJbnNwaXJlLUZpcmVtYXciLDB4NTE4LDB4MCxQbGF5ZXItNDQ2Ny0wMDUyNkNBMCwiSW5zcGlyZS1GaXJlbWF3IiwweDUxOCwweDAsMTQzMjIsIkFzcGVjdCBvZiB0aGUgSGF3ayIsMHg4LEJVRkYKNi8zIDE1OjQ3OjI4LjUxNiAgU1dJTkdfREFNQUdFLFBsYXllci00NDY3LTAwNDdBMDc3LCJEcmlsbGVuaXNzZW4tRmlyZW1hdyIsMHg1MTEsMHgwLENyZWF0dXJlLTAtNDQ0OC0xLTEyOS0xMDY4NS0wMDAwRDZGN0YyLCJTd2luZSIsMHgxMGEyOCwweDAsUGxheWVyLTQ0NjctMDA0N0EwNzcsMDAwMDAwMDAwMDAwMDAwMCwxMDAsMTAwLDAsMCwwLC0xLDAsMCwwLDEyMzcuNDcsLTQ1MzAuMTEsMTQxMSwwLjUxOTIsNjMsNTUzLDI3MCw1MzksMSwwLDAsMCwxLG5pbCxuaWwKNi8zIDE1OjQ3OjI4LjkyMCAgUEFSVFlfS0lMTCxQbGF5ZXItNDQ2Ny0wMDQ3QTA3NywiRHJpbGxlbmlzc2VuLUZpcmVtYXciLDB4NTExLDB4MCxDcmVhdHVyZS0wLTQ0NDgtMS0xMjktMTA2ODUtMDAwMEQ2RjdGMiwiU3dpbmUiLDB4MTBhMjgsMHgwCjYvMyAxNTo0NzoyOC45MjAgIFNXSU5HX0RBTUFHRV9MQU5ERUQsUGxheWVyLTQ0NjctMDA0N0EwNzcsIkRyaWxsZW5pc3Nlbi1GaXJlbWF3IiwweDUxMSwweDAsQ3JlYXR1cmUtMC00NDQ4LTEtMTI5LTEwNjg1LTAwMDBENkY3RjIsIlN3aW5lIiwweDEwYTI4LDB4MCxDcmVhdHVyZS0wLTQ0NDgtMS0xMjktMTA2ODUtMDAwMEQ2RjdGMiwwMDAwMDAwMDAwMDAwMDAwLDAsMTAwLDAsMCwwLC0xLDAsMCwwLDEyMzguNDcsLTQ1MjguMDgsMTQxMSw0LjAyNDEsMyw1NTMsMjcwLDUzOSwxLDAsMCwwLDEsbmlsLG5pbAo2LzMgMTU6NDc6MjkuMzE1ICBVTklUX0RJRUQsMDAwMDAwMDAwMDAwMDAwMCxuaWwsMHg4MDAwMDAwMCwweDgwMDAwMDAwLENyZWF0dXJlLTAtNDQ0OC0xLTEyOS0xMDY4NS0wMDAwRDZGN0YyLCJTd2luZSIsMHgxMGEyOCwweDAK";
        
        try {
            AnalysisHandler ah = new AnalysisHandler("Maloni-Mograine", path,"woowaa");

        } catch (Exception ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
