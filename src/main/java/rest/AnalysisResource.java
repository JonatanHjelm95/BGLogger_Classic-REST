/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import Analysis.AnalysisHandler;
import RealTime.CettiaBootstrap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.cettia.Server;
import static io.cettia.ServerSocketPredicates.tag;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

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
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("Stream")
    public String StreamTest() {
        Server S = CettiaBootstrap.getServer();
        Map<String, Object> output = new LinkedHashMap<>();
        output.put("sender", "Example/Stream");
        output.put("text", "Enpoint called");
        S.find(tag("channel:log")).send("message", output);
        return "{\"msg\":\"Hello anonymous\"}";
    }
    
    @Path("postlog")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public String analyze(String jsonString) {
        JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
        String initiator = json.get("initiator").getAsString();
        String data = json.get("data").getAsString();
        try {
            AnalysisHandler a = new AnalysisHandler(initiator, data);
            return GSON.toJson("der er hul igennem");
         
            //Return resultObject
        }
        catch (IOException e) {
            return GSON.toJson(e.toString());
        } catch (InterruptedException ex) {
            Logger.getLogger(AnalysisResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
