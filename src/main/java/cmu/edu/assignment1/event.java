package cmu.edu.assignment1;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.json.JSONObject;

import java.util.UUID;

@Path("/event")
public class event {
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response addEvent(String request) {
        System.out.println("Request is " + request);
        JSONObject payload = new JSONObject(request);
        try {
            JSONObject event = payload.getJSONObject("event");
            String doctor = event.getString("doctorId");
            String patient = event.getString("patientId");
            event.put("id", UUID.randomUUID());
            return Response.status(200).entity(event.toString()).build();
        } catch (Exception e) {
            JSONObject errResponse = new JSONObject();
            errResponse.put("message", "Missing required field");
            return Response.status(400).entity(errResponse.toString()).build();
        }
    }
}


