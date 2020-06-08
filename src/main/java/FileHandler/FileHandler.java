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


    private static Input createInput(String line) throws IllegalArgumentException {
        if(!line.matches("^.*\\d+\\/\\d+.*")){
            throw new  IllegalArgumentException();
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
  
    public static void fileInputStream(EventHandler eh, InputStream inputStream) throws FileNotFoundException, IOException {
        Scanner sc = null;
        try {
            sc = new Scanner(inputStream, "UTF-8");
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                    try {
                        Input i = createInput(line);
                        eh.addEvent(i);
                    } catch (IllegalArgumentException e) {
                        continue;
                    }
                }            
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
}
