package br.com.dea.management.academyClass.post;

import br.com.dea.management.academyClass.AcademyTestUtils;
import br.com.dea.management.academyclass.ClassType;
import br.com.dea.management.academyclass.domain.AcademyClass;
import br.com.dea.management.academyclass.repository.AcademyClassRepository;
import br.com.dea.management.employee.EmployeeTestUtils;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AcademyClassSuccessCaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeTestUtils employeeTestUtils;

    @Autowired
    private AcademyTestUtils academyTestUtils;

    @Autowired
    private AcademyClassRepository academyClassRepository;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    void whenRequestingAcademyClassCreationWithAValidPayload_then_CreateAAcademyClassSuccessfuly() throws Exception {
        this.employeeRepository.deleteAll();
        this.academyClassRepository.deleteAll();
        this.employeeTestUtils.createFakeEmployees(10);
        Employee employee = this.employeeRepository.findAll().get(0);

        String payload = "{" +
                "\"start_date\": \"2023-03-28\"," +
                "\"end_date\": \"2024-03-28\"," +
                "\"class_type\": \"DEVELOPER\"," +
                "\"instructor_id\": " + employee.getId() +
                "}";


        mockMvc.perform(post("/academy-class").contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isOk());

        AcademyClass academyClass = this.academyClassRepository.findAll().get(0);
        assertThat(academyClass.getEndDate()).isEqualTo(LocalDate.parse("2024-03-28"));
        assertThat(academyClass.getStartDate()).isEqualTo(LocalDate.parse("2023-03-28"));
        assertThat(academyClass.getClassType()).isEqualTo(ClassType.DEVELOPER);
        assertThat(academyClass.getInstructor().getEmployeeType()).isEqualTo(employee.getEmployeeType());
        assertThat(academyClass.getInstructor().getPosition().getSeniority()).isEqualTo(employee.getPosition().getSeniority());






    }
}
