package cmu.edu.assignment1;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;


@Path("/day")
public class DayResource {
    @GET
    @Produces("application/json")
    public Response day() {
        // generate a response
        String[] dayOfWeek = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int num = c.get(Calendar.DAY_OF_WEEK);

        JSONObject result = new JSONObject();
        result.put("day", dayOfWeek[num-1]);
        return Response.status(200).entity(result.toString()).build();
    }
}
