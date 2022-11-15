package cmu.edu.assignment1;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Path("/event")
public class event {

    List<JSONObject> eventList;

    public event() {
        eventList = new ArrayList<>();
        JSONObject event1 = new JSONObject()
                .put("id", "d80355fd-3a7b-4faa-b775-dbd671d4924c")
                .put("date","2022-11-07")
                .put("doctorId","3acf2b08-d7a2-4937-80bf-82a4598f82bf")
                .put("patientId","c8303d2e-00c9-4fe8-b199-6efc6b842aae")
                .put("desc","test desc 1");
        JSONObject event2 = new JSONObject()
                .put("id", "626f964c-9ef0-4ded-8bff-705d00881645")
                .put("date","2022-11-07")
                .put("doctorId","3acf2b08-d7a2-4937-80bf-82a4598f82bf")
                .put("patientId","c8303d2e-00c9-4fe8-b199-6efc6b842aae")
                .put("desc","test desc 2");
        JSONObject event3 = new JSONObject()
                .put("id", "c144e0e3-f9ae-46ac-bdd7-2fc6755d59c3")
                .put("date","2022-11-08")
                .put("doctorId","3acf2b08-d7a2-4937-80bf-82a4598f82bf")
                .put("patientId","c8303d2e-00c9-4fe8-b199-6efc6b842aae")
                .put("desc","test desc 3");
        eventList.add(event1);
        eventList.add(event2);
        eventList.add(event3);
    }
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response addEvent(String request) {
        System.out.println("ADD Request is " + request);
        JSONObject payload = new JSONObject(request);
        try {
            JSONObject event = payload.getJSONObject("event");
            String doctor = event.getString("doctorId");
            String patient = event.getString("patientId");
            String date = event.getString("date");
            event.put("id", UUID.randomUUID());
            eventList.add(event);
            return Response.status(200).entity(event.toString()).build();
        } catch (Exception e) {
            JSONObject errResponse = new JSONObject();
            errResponse.put("message", "Missing required field");
            return Response.status(400).entity(errResponse.toString()).build();
        }
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateEvent(String request) {
        System.out.println("UPDATE Request is " + request);
        JSONObject payload = new JSONObject(request);
        try {
            JSONObject event = payload.getJSONObject("event");
            String eventId = event.getString("id");
            for (JSONObject json : eventList) {
                if (json.get("id").equals(eventId)) {
                    json.put("doctorId", event.getString("doctorId") == null
                            ? json.get("doctorId") : event.getString("doctorId"));
                    json.put("doctorId", event.getString("doctorId") == null
                            ? json.get("doctorId") : event.getString("doctorId"));
                    json.put("desc", event.getString("desc") == null
                            ? json.get("desc") : event.getString("desc"));
                    return Response.status(200).entity(json.toString()).build();
                }
            }
            JSONObject errResponse = new JSONObject();
            errResponse.put("message", "No match event");
            return Response.status(400).entity(errResponse.toString()).build();
        } catch (Exception e) {
            JSONObject errResponse = new JSONObject();
            errResponse.put("message", "Missing required field");
            return Response.status(400).entity(errResponse.toString()).build();
        }
    }

    @DELETE
    @Consumes("application/json")
    @Produces("application/json")
    public Response deleteEvent(String request) {
        System.out.println("DELETE Request is " + request);
        JSONObject payload = new JSONObject(request);
        try {
            JSONObject event = payload.getJSONObject("event");
            String eventId = event.getString("id");
            for (JSONObject json : eventList) {
                if (json.get("id").equals(eventId)) {
                    eventList.remove(json);
                    JSONObject successResponse = new JSONObject();
                    successResponse.put("message", "Delete success");
                    return Response.status(200).entity(successResponse.toString()).build();
                }
            }
            JSONObject errResponse = new JSONObject();
            errResponse.put("message", "No match event");
            return Response.status(400).entity(errResponse.toString()).build();
        } catch (Exception e) {
            JSONObject errResponse = new JSONObject();
            errResponse.put("message", "Missing required field");
            return Response.status(400).entity(errResponse.toString()).build();
        }
    }

    @GET
    @Produces("application/json")
    @Path("/{date}")
    public Response getEventByDate(@PathParam("date") String date) {
//        System.out.print("date is " + date);
        try {
            DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
            df.setLenient(false);
            df.parse(date);
        } catch (ParseException e) {
            JSONObject errResponse = new JSONObject();
            errResponse.put("message", "Invalid date");
            return Response.status(400).entity(errResponse.toString()).build();
        }
        JSONObject response = new JSONObject();
        JSONArray arr = new JSONArray();
        for (JSONObject json : eventList) {
            if (json.get("date").equals(date)) {
                arr.put(json);
            }
        }
        response.put("events", arr);
        return Response.status(200).entity(response.toString()).build();
    }

}


