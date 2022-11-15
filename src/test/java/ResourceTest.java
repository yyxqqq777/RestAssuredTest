import cmu.edu.assignment1.HelloApplication;
import cmu.edu.assignment1.Main;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.util.concurrent.TimeUnit;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ResourceTest {
    @BeforeAll
    public static void startInstance() throws Exception {
        Main.main(null);
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
                .contentType("application/json")
                .body(testObj.toString())
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
                .contentType("application/json")
                .body(testObj.toString())
                .when()
                .post("/Assignment1-1.0-SNAPSHOT/api/event")
                .then()
                .assertThat()
                .body(equalTo("{\"message\":\"Missing required field\"}"));
    }

    @Test
    void testPutEventSuccess() {
        JSONObject event = new JSONObject()
                .put("id", "d80355fd-3a7b-4faa-b775-dbd671d4924c")
                .put("date","2022-11-07")
                .put("doctorId","3acf2b08-d7a2-4937-80bf-82a4598f82bf")
                .put("patientId","c8303d2e-00c9-4fe8-b199-6efc6b842aae")
                .put("desc","test desc put api");
        JSONObject testObj = new JSONObject()
                .put("event", event);

        given()
                .contentType("application/json")
                .body(testObj.toString())
                .when()
                .post("/Assignment1-1.0-SNAPSHOT/api/event")
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("event-schema.json"));
    }

    @Test
    void testPutEventFailNoId() {
        JSONObject event = new JSONObject()
                .put("date","2022-11-07")
                .put("doctorId","3acf2b08-d7a2-4937-80bf-82a4598f82bf");
        JSONObject testObj = new JSONObject()
                .put("event", event);

        given()
                .contentType("application/json")
                .body(testObj.toString())
                .when()
                .put("/Assignment1-1.0-SNAPSHOT/api/event")
                .then()
                .assertThat()
                .body(equalTo("{\"message\":\"Missing required field\"}"));
    }

    @Test
    void testPutEventFailNoMatchId() {
        JSONObject event = new JSONObject()
                .put("doctorId","3acf2b08-d7a2-4937-80bf-82a4598f82bf")
                .put("id","c8303d2e-00c9-4fe8-b199-6efc6b842aae");
        JSONObject testObj = new JSONObject()
                .put("event", event);

        given()
                .contentType("application/json")
                .body(testObj.toString())   // use jsonObj toString method
                .when()
                .put("/Assignment1-1.0-SNAPSHOT/api/event")
                .then()
                .assertThat()
                .body(equalTo("{\"message\":\"No match event\"}"));
    }

    @Test
    void testDeleteEventSuccess() {
        JSONObject event = new JSONObject()
                .put("id", "d80355fd-3a7b-4faa-b775-dbd671d4924c");
        JSONObject testObj = new JSONObject()
                .put("event", event);

        given()
                .contentType("application/json")  //another way to specify content type
                .body(testObj.toString())   // use jsonObj toString method
                .when()
                .delete("/Assignment1-1.0-SNAPSHOT/api/event")
                .then()
                .assertThat()
                .body(equalTo("{\"message\":\"Delete success\"}"));
    }

    @Test
    void testDeleteEventFailNoId() {
        JSONObject event = new JSONObject()
                .put("doctorId","3acf2b08-d7a2-4937-80bf-82a4598f82bf");
        JSONObject testObj = new JSONObject()
                .put("event", event);

        given()
                .contentType("application/json")  //another way to specify content type
                .body(testObj.toString())   // use jsonObj toString method
                .when()
                .delete("/Assignment1-1.0-SNAPSHOT/api/event")
                .then()
                .assertThat()
                .body(equalTo("{\"message\":\"Missing required field\"}"));
    }

    @Test
    void testDeleteEventFailNoMatchId() {
        JSONObject event = new JSONObject()
                .put("id","c8303d2e-00c9-4fe8-b199-6efc6b842aae");
        JSONObject testObj = new JSONObject()
                .put("event", event);

        given()
                .contentType("application/json")  //another way to specify content type
                .body(testObj.toString())   // use jsonObj toString method
                .when()
                .delete("/Assignment1-1.0-SNAPSHOT/api/event")
                .then()
                .assertThat()
                .body(equalTo("{\"message\":\"No match event\"}"));
    }

    @Test
    void testGetEventByDateSuccess() {
        given()
                .get("/Assignment1-1.0-SNAPSHOT/api/event/2022-11-07")
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("events-schema.json"));
    }

}