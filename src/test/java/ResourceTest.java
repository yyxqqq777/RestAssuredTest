import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class ResourceTest {
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;
    private static final PrintStream originalErr = System.err;

    @BeforeAll
    static void setup() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterAll
    static void teardown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void test() {
        get("/Assignment1-1.0-SNAPSHOT/api/test").then().body("code", equalTo(200));
    }

    @Test
    void testDay() {
        String[] dayOfWeek = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int num = c.get(Calendar.DAY_OF_WEEK);
        get("/Assignment1-1.0-SNAPSHOT/api/day").then().body("day", equalTo(dayOfWeek[num-1]));
    }

}