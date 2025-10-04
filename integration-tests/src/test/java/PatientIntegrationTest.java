import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;

public class PatientIntegrationTest {
    @BeforeAll
    static void setUp() {
        System.out.println("Setting up test environment...");
        RestAssured.baseURI = "http://localhost:4004";
    }
    @Test
    public void shouldReturnPatientsWithValidToken() {
        System.out.println("Running test: shouldReturnPatientsWithValidToken");

        System.out.println("Attempting to log in...");
        String loginPayload = """
      {
        "email": "testuser@test.com",
        "password": "password123"
      }
    """;

        System.out.println("Getting authentication token...");
        String token = given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("token");

        System.out.println("Successfully obtained token, fetching patients...");

        // Get the response as a list of maps directly
        List<Map<String, Object>> patients = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/patients")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("$");  // Changed from "patients" to "$" to get the root array

        System.out.println("\n=== PATIENT LIST ===");
        System.out.println("Total patients: " + patients.size());
        System.out.println("---------------------");

        if (patients.isEmpty()) {
            System.out.println("No patients found in the system.");
        } else {
            int count = 1;
            for (Map<String, Object> patient : patients) {
                System.out.println("Patient #" + count++);
                System.out.println("ID: " + patient.get("id"));
                System.out.println("Name: " + patient.get("name"));
                System.out.println("Email: " + patient.get("email"));
                System.out.println("Date of Birth: " + patient.get("dateOfBirth"));
                System.out.println("Address: " + patient.get("address"));
                System.out.println("---------------------");
            }
        }

        System.out.println("Test completed");
    }

    }
