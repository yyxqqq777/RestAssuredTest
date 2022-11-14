package cmu.edu.assignment1;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

@Path("/current")
public class currentResource {
    @GET
    @Produces("application/json")
    public Response current() {
        // generate a response
        String[] dayOfWeek = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int num = c.get(Calendar.DAY_OF_WEEK);

        JSONObject response = new JSONObject();
        response.put("date", LocalDate.now());
        response.put("day", dayOfWeek[num-1]);
        response.put("month", LocalDate.now().getMonthValue());
        response.put("year", Year.now().getValue());
        return Response.status(200).entity(response.toString()).build();
    }
}
