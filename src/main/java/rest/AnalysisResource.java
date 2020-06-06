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
    @Path("test")
    public String testAnalyzizzz(){
        String data = "Ni8zIDE1OjQ2OjQ3LjMxOCAgQ09NQkFUX0xPR19WRVJTSU9OLDksQURWQU5DRURfTE9HX0VOQUJMRUQsMSxCVUlMRF9WRVJTSU9OLDEuMTMuNCxQUk9KRUNUX0lELDIKNi8zIDE1OjQ3OjAyLjA0MyAgU1BFTExfQVVSQV9BUFBMSUVELFBsYXllci00NDY3LTAwNTI2Q0EwLCJJbnNwaXJlLUZpcmVtYXciLDB4NTE4LDB4MCxQbGF5ZXItNDQ2Ny0wMDUyNkNBMCwiSW5zcGlyZS1GaXJlbWF3IiwweDUxOCwweDAsMTQzMjIsIkFzcGVjdCBvZiB0aGUgSGF3ayIsMHg4LEJVRkYKNi8zIDE1OjQ3OjI4LjUxNiAgU1dJTkdfREFNQUdFLFBsYXllci00NDY3LTAwNDdBMDc3LCJEcmlsbGVuaXNzZW4tRmlyZW1hdyIsMHg1MTEsMHgwLENyZWF0dXJlLTAtNDQ0OC0xLTEyOS0xMDY4NS0wMDAwRDZGN0YyLCJTd2luZSIsMHgxMGEyOCwweDAsUGxheWVyLTQ0NjctMDA0N0EwNzcsMDAwMDAwMDAwMDAwMDAwMCwxMDAsMTAwLDAsMCwwLC0xLDAsMCwwLDEyMzcuNDcsLTQ1MzAuMTEsMTQxMSwwLjUxOTIsNjMsNTUzLDI3MCw1MzksMSwwLDAsMCwxLG5pbCxuaWwKNi8zIDE1OjQ3OjI4LjkyMCAgUEFSVFlfS0lMTCxQbGF5ZXItNDQ2Ny0wMDQ3QTA3NywiRHJpbGxlbmlzc2VuLUZpcmVtYXciLDB4NTExLDB4MCxDcmVhdHVyZS0wLTQ0NDgtMS0xMjktMTA2ODUtMDAwMEQ2RjdGMiwiU3dpbmUiLDB4MTBhMjgsMHgwCjYvMyAxNTo0NzoyOC45MjAgIFNXSU5HX0RBTUFHRV9MQU5ERUQsUGxheWVyLTQ0NjctMDA0N0EwNzcsIkRyaWxsZW5pc3Nlbi1GaXJlbWF3IiwweDUxMSwweDAsQ3JlYXR1cmUtMC00NDQ4LTEtMTI5LTEwNjg1LTAwMDBENkY3RjIsIlN3aW5lIiwweDEwYTI4LDB4MCxDcmVhdHVyZS0wLTQ0NDgtMS0xMjktMTA2ODUtMDAwMEQ2RjdGMiwwMDAwMDAwMDAwMDAwMDAwLDAsMTAwLDAsMCwwLC0xLDAsMCwwLDEyMzguNDcsLTQ1MjguMDgsMTQxMSw0LjAyNDEsMyw1NTMsMjcwLDUzOSwxLDAsMCwwLDEsbmlsLG5pbAo2LzMgMTU6NDc6MjkuMzE1ICBVTklUX0RJRUQsMDAwMDAwMDAwMDAwMDAwMCxuaWwsMHg4MDAwMDAwMCwweDgwMDAwMDAwLENyZWF0dXJlLTAtNDQ0OC0xLTEyOS0xMDY4NS0wMDAwRDZGN0YyLCJTd2luZSIsMHgxMGEyOCwweDAK";
        try {
            AnalysisHandler ah = new AnalysisHandler("Drillenissen", data);
        } catch (IOException ex) {
            Logger.getLogger(AnalysisResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(AnalysisResource.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        System.out.println("did stream");
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
