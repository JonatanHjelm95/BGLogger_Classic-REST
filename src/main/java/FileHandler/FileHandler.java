/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileHandler;

import EventHandler.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.*;
import java.util.Base64;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jonab
 */
public class FileHandler {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    private static Input createInput(String line) throws Exception {
        if(!line.matches("^.*\\d+\\/\\d+.*")){
            throw new Exception();
        }
        //Splitting on whitespaces IOT get date and time
        String[] dates = line.split(" ");
        String date = dates[0].contains(",")?dates[0].substring(1):dates[0];
        String time = dates[1];
        String eventString = dates[3];
        String[] eventSplit = eventString.split(",");

        try {
            return new Input(date, time, eventSplit, MyEventType.valueOf(eventSplit[0]));
        } catch (IllegalArgumentException e) {
            return new Input(date, time, eventSplit, MyEventType.OTHER);
        }
    }

    private static Input createInputFromJson(String line) {
        //String line = GSON.fromJson(json, String.class).replace("\\", "");

        //Splitting on whitespaces IOT get date and time
        String[] lineSplit = line.split("  ");
        String[] dates = lineSplit[0].split(" ");
        String date = dates[0];
        String time = dates[1];
        String[] eventString = lineSplit[1].split(",");
        try {

            return new Input(date, time, eventString, MyEventType.valueOf(eventString[0]));
        } catch (IllegalArgumentException e) {
            return new Input(date, time, eventString, MyEventType.OTHER);
        }
    }

    private static boolean advancedCombatLog(String line) {
        return Integer.parseInt(line.split(",")[3]) == 1;
    }

    public static void fileInputStream(EventHandler eh, String data) throws FileNotFoundException, IOException {
        FileInputStream inputStream = null;
        Scanner sc = null;
        try {
            inputStream = new FileInputStream(data);
            sc = new Scanner(inputStream, "UTF-8");
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                eh.addEvent(createInput(line));
                // System.out.println(line);
            }
            // note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        } catch (Exception ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.out.println("File Read");
            eh.endFile();
            if (inputStream != null) {
                inputStream.close();
            }
            if (sc != null) {
                sc.close();
            }
        }
    }

    public static void fileInputStream(EventHandler eh, InputStream inputStream) throws FileNotFoundException, IOException {
        Scanner sc = null;
        try {
            sc = new Scanner(inputStream, "UTF-8");
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                    try {
                        Input i = createInput(line);
                        eh.addEvent(i);
                    } catch (Exception e) {
                        continue;
                    }
                }
                

                // System.out.println(line);
            
            // note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        } catch (Exception e) {
            System.out.println("i went ree");
            System.out.println(e.getMessage());
        } finally {
            System.out.println("File Read");
            eh.endFile();
            if (sc != null) {
                sc.close();
            }

        }
    }

    public static void readFromJson(EventHandler eh, JsonArray data) {
        for (int i = 0; i < data.size() - 1; i++) {
            eh.addEvent(createInputFromJson(GSON.fromJson(data.get(i), String.class)));
//            try {
//                
//            }catch (ArrayIndexOutOfBoundsException e){
//                
//            }
        }

    }

    public static void main(String[] args) throws IOException {
        fileInputStream(new EventHandler(), "C:\\Users\\jonab\\.ssh\\4sem\\advProgramming\\BGLogger_Classic\\WoWCombatLog.txt");
    }
}
