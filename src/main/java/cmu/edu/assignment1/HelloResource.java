package cmu.edu.assignment1;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.json.JSONObject;


@Path("/test")
public class HelloResource {
    @GET
    @Produces("application/json")
    public Response test() {
        String defaultString = "result";
        // generate a response
        JSONObject result = new JSONObject();

        result.put("message", defaultString);
        result.put("code", 200);

        return Response.status(200).entity(result.toString()).build();
    }
}
