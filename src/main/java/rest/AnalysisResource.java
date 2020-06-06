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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.cettia.Server;
import static io.cettia.ServerSocketPredicates.tag;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.LoggerFactory;

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

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("test")
//    public String testAnalyzizzz() {
//        //String data = "C:\\Users\\Jutsu\\Downloads\\WoWCombatLog (2).txt";
//        String data = "C:\\Users\\jonab\\Desktop\\WoWCombatLog.txt";
//        try {
//            AnalysisHandler ah = new AnalysisHandler("Maloni-Mograine", data, "wohoo");
//        } catch (IOException ex) {
//            Logger.getLogger(AnalysisResource.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(AnalysisResource.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return "helloSenior";
//    }
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
        JsonArray data = json.get("data").getAsJsonArray();
        try {
            AnalysisHandler a = new AnalysisHandler(initiator, data);
            return GSON.toJson("Analyzing CombatLog");
        } catch (IOException io) {
            GSON.toJson(io.toString());
        } catch (InterruptedException i) {
            GSON.toJson(i.toString());
        }
        return GSON.toJson(data.get(0));
    }

    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String submit(@FormDataParam("initiator") String initiator, @FormDataParam("file") InputStream file) {
        try {
            byte[] fileByteArray = convertInputStreamToByteArrary(file);
            AnalysisHandler ah = new AnalysisHandler(initiator, fileByteArray);
        } 
        catch ( IllegalStateException ex) {
            ex.printStackTrace();
        }
        catch ( IOException ex) {
            ex.printStackTrace();
        }
        catch ( InterruptedException ex) {
            ex.printStackTrace();
        }
        
        return GSON.toJson("Success");
    }

    public static byte[] convertInputStreamToByteArrary(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        final int BUF_SIZE = 1024;
        byte[] buffer = new byte[BUF_SIZE];
        int bytesRead = -1;
        while ((bytesRead = in.read(buffer)) > -1) {
            out.write(buffer, 0, bytesRead);
        }
        in.close();
        byte[] byteArray = out.toByteArray();
        return byteArray;
    }
}
