/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import Analysis.AnalysisHandler;
import RealTime.CettiaBootstrap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.cettia.Server;
import static io.cettia.ServerSocketPredicates.tag;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Martin
 */
@Path("analyze")
public class AnalysisResource {

    @Context
    private UriInfo context;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String Test() {
        return "helloSenior";
    }
    /*
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("test")
    public String testAnalyzizzz() {
        String data = "C:\\Users\\Jutsu\\Downloads\\WoWCombatLog.txt";

        try {
            AnalysisHandler ah = new AnalysisHandler("Maloni-Mograine", data, "wohoo");
        } catch (IOException ex) {
            Logger.getLogger(AnalysisResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(AnalysisResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "helloSenior";
    }
    */
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("Stream")
    public String StreamTest() {
        Server S = CettiaBootstrap.getServer();

        Map<String, Object> output = new LinkedHashMap<>();
        output.put("sender", "Example/Stream");
        output.put("text", "Enpoint called");
        S.find(tag("channel:log")).send("message", output);
        System.out.println("did stream");
        return "{\"msg\":\"Hello anonymous\"}";
    }



    @Path("upload/{initiator}")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces({MediaType.APPLICATION_JSON})
    public String ree(
            @PathParam("initiator") String initiatoir,
            @FormDataParam("file") InputStream uploadedInputStream
    ) {
        System.out.println("omg a post!");
        System.out.println(initiatoir);
 
        try {
            AnalysisHandler ah = new AnalysisHandler(initiatoir, uploadedInputStream);
        } catch (IOException ex) {
            Logger.getLogger(AnalysisResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(AnalysisResource.class.getName()).log(Level.SEVERE, null, ex);
        }

       
        return "it woorks! THATS SO WIZARD ANNIE!";
    }

}
