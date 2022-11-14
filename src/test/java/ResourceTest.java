import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.restassured.RestAssured.*;
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
    void testCurrent() {
        get("/Assignment1-1.0-SNAPSHOT/api/current").then().
                assertThat().body(matchesJsonSchemaInClasspath("current-schema.json"));
    }
    @Test
    void testAddEventSuccess() {
        JSONObject event = new JSONObject()
                .put("date","2022-11-07")
                .put("doctorId","3acf2b08-d7a2-4937-80bf-82a4598f82bf")
                .put("patientId","c8303d2e-00c9-4fe8-b199-6efc6b842aae")
                .put("desc","test desc");
        JSONObject testObj = new JSONObject()
                .put("event", event);

        given()
                .contentType("application/json")  //another way to specify content type
                .body(testObj.toString())   // use jsonObj toString method
                .when()
                .post("/Assignment1-1.0-SNAPSHOT/api/event")
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("event-schema.json"));
    }

    @Test
    void testAddEventFail() {
        JSONObject event = new JSONObject()
                .put("date","2022-11-07")
                .put("doctorId","3acf2b08-d7a2-4937-80bf-82a4598f82bf");
        JSONObject testObj = new JSONObject()
                .put("event", event);

        given()
                .contentType("application/json")  //another way to specify content type
                .body(testObj.toString())   // use jsonObj toString method
                .when()
                .post("/Assignment1-1.0-SNAPSHOT/api/event")
                .then()
                .assertThat()
                .body(equalTo("{\"message\":\"Missing required field\"}"));
    }

}