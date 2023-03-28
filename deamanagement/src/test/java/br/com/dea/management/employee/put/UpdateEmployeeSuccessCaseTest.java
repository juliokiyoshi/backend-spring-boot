package br.com.dea.management.employee.put;

import br.com.dea.management.employee.EmployeeTestUtils;
import br.com.dea.management.employee.EmployeeType;
import br.com.dea.management.employee.domain.Employee;
import br.com.dea.management.employee.repository.EmployeeRepository;
import br.com.dea.management.position.domain.Position;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UpdateEmployeeSuccessCaseTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeTestUtils employeeTestUtils;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    void whenRequestingEmployeeCreationWithAValidPayload_thenCreateAEmployeeSuccessfully() throws Exception {
        this.employeeRepository.deleteAll();

        this.employeeTestUtils.createFakeEmployees(10);
        Position position = this.employeeTestUtils.createFakePosition("mobile developer", "JR");


        Employee employeeBase = this.employeeRepository.findAll().get(0);

        String payload = "{" +
                "\"name\": \"julio\"," +
                "\"email\": \"julio@gmail.com\"," +
                "\"linkedin\": \"julio.matsoui\"," +
                "\"employeeType\": \"DEVELOPER\"," +
                "\"position\": " + position.getId() + "," +
                "\"password\": \"password\"" +
                "}";
        mockMvc.perform(put("/employee/" + employeeBase.getId())
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isOk());

        Employee employee = this.employeeRepository.findAll().get(0);

        assertThat(employee.getUser().getName()).isEqualTo("julio");
        assertThat(employee.getUser().getEmail()).isEqualTo("julio@gmail.com");
        assertThat(employee.getUser().getLinkedin()).isEqualTo("julio.matsoui");
        assertThat(employee.getUser().getPassword()).isEqualTo("password");
        assertThat(employee.getEmployeeType()).isEqualTo(EmployeeType.DEVELOPER);
        assertThat(employee.getPosition().getDescription()).isEqualTo("mobile developer");
        assertThat(employee.getPosition().getSeniority()).isEqualTo("JR");
    }
}
