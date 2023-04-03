package br.com.dea.management.academyClass.post;

import br.com.dea.management.academyclass.repository.AcademyClassRepository;
import br.com.dea.management.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.nio.charset.Charset;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AcademyClassCreationPayloadValidation {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AcademyClassRepository academyClassRepository;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));


    @Test
    void whenRequestingAcademyClassCreationWithInvalidPayload_thenCreateAEmployeeGetsError400() throws Exception {
        String payload = "{" +
                "\"start_date\": \"\"," +
                "\"end_date\": \"\"," +
                "\"class_type\": \"DEVELOPER\"," +
                "\"instructor_id\": \"1\"" +
                "}";


        mockMvc.perform(post("/academy-class")
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(2)))
                .andExpect(jsonPath("$.details[*].field", hasItem("start_date")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Start date could not be null")))
                .andExpect(jsonPath("$.details[*].field", hasItem("end_date")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("End Date could not be null")));
    }

    @Test
    void whenPayloadHasRequiredFieldsAreMissing_thenReturn400AndTheErrors() throws Exception {
        String payload = "{}";
        mockMvc.perform(post("/academy-class")
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(2)))
                .andExpect(jsonPath("$.details[*].field", hasItem("start_date")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("Start date could not be null")))
                .andExpect(jsonPath("$.details[*].field", hasItem("end_date")))
                .andExpect(jsonPath("$.details[*].errorMessage", hasItem("End Date could not be null")));
    }

}
