/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileHandler;

import EventHandler.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.*;
import java.util.Base64;

/**
 *
 * @author jonab
 */
public class FileHandler {

    public static void FileReaderFromBase64(EventHandler eh, String encodedData) throws FileNotFoundException, IOException {
        Arrays.asList(new String(Base64.getDecoder()
                .decode(encodedData)).split("\n"))
                .stream().map(line -> createInput(line)).collect(Collectors.toList())
                .forEach((i) -> {
                    eh.addEvent(i);
                });
        eh.endFile();
    }

    public static void FileReader(EventHandler eh, String path) throws FileNotFoundException, IOException {
        List<Input> list = new ArrayList();
        try {
            try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
                list = reader.lines().map(line -> createInput(line)).collect(Collectors.toList());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Input i : list) {
            eh.addEvent(i);
        }

    }

    private static Input createInput(String line) {
        //Splitting on whitespaces IOT get date and time
        String[] dates = line.split(" ");
        String date = dates[0];
        String time = dates[1];
        String eventString = dates[3];
        String[] eventSplit = eventString.split(",");

        try {
            return new Input(date, time, eventSplit, MyEventType.valueOf(eventSplit[0]));
        } catch (IllegalArgumentException e) {
            return new Input(date, time, eventSplit, MyEventType.OTHER);
        }
    }

    private static boolean advancedCombatLog(String line) {
        return Integer.parseInt(line.split(",")[3]) == 1;
    }

    public static void main(String[] args) throws IOException {
        
    }
}
