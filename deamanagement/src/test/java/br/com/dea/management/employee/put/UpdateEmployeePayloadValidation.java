package br.com.dea.management.employee.put;

import br.com.dea.management.employee.EmployeeTestUtils;
import br.com.dea.management.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UpdateEmployeePayloadValidation {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeTestUtils employeeTestUtils;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    void whenRequestingEmployeeCreationWithInvalidPayload_thenCreateAEmployeeGetsError400() throws Exception {
        String payload = "{" +
                "\"name\": \"julio\"," +
                "\"email\": \"julio@\"," +
                "\"linkedin\": \"julio.matsoui\"," +
                "\"employeeType\": \"DEVELOPER\"," +
                "\"description\": \"mobile developer\"," +
                "\"password\": \"password\"," +
                "\"seniority\": \"JR\"" +
                "}";


        mockMvc.perform(put("/employee/1")
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)))
                .andExpect(jsonPath("$.details[*].field", hasItem("email")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Email passed is not valid!")));
    }

    @Test
    void whenPayloadHasRequiredFieldsAreMissing_thenReturn400AndTheErrors() throws Exception {

        String payload = "{" +
                "\"name\": \"\"," +
                "\"email\": \"\"," +
                "\"linkedin\": \"\"," +
                "\"employeeType\": \"DEVELOPER\"," +
                "\"description\": \"\"," +
                "\"password\": \"\"," +
                "\"seniority\": \"\"" +
                "}";
        mockMvc.perform(put("/employee/1")
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)))
                .andExpect(jsonPath("$.details[*].field", hasItem("email")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Email could not be empty")));
    }
}
