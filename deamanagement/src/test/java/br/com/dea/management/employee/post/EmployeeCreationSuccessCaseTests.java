package br.com.dea.management.employee.post;

import br.com.dea.management.employee.EmployeeType;
import br.com.dea.management.employee.domain.Employee;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmployeeCreationSuccessCaseTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    void whenRequestingEmployeeCreationWithAValidPayload_thenCreateAEmployeeSuccessfully() throws Exception {
        this.employeeRepository.deleteAll();

        String payload = "{" +
                "\"name\": \"julio\"," +
                "\"email\": \"julio@gmail.ocm\"," +
                "\"linkedin\": \"julio.matsoui\"," +
                "\"employeeType\": \"DEVELOPER\"," +
                "\"description\": \"mobile developer\"," +
                "\"password\": \"password\"," +
                "\"seniority\": \"JR\"" +
                "}";
        mockMvc.perform(post("/employee")
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isOk());

        Employee employee = this.employeeRepository.findAll().get(0);

        assertThat(employee.getUser().getName()).isEqualTo("julio");
        assertThat(employee.getUser().getEmail()).isEqualTo("julio@gmail.ocm");
        assertThat(employee.getUser().getLinkedin()).isEqualTo("julio.matsoui");
        assertThat(employee.getUser().getPassword()).isEqualTo("password");
        assertThat(employee.getEmployeeType()).isEqualTo(EmployeeType.DEVELOPER);
        assertThat(employee.getPosition().getDescription()).isEqualTo("mobile developer");
        assertThat(employee.getPosition().getSeniority()).isEqualTo("JR");
    }

}
