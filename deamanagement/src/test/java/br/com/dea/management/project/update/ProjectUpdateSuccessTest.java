package br.com.dea.management.project.update;


import br.com.dea.management.academyclass.repository.AcademyClassRepository;
import br.com.dea.management.employee.EmployeeTestUtils;
import br.com.dea.management.employee.domain.Employee;
import br.com.dea.management.employee.repository.EmployeeRepository;
import br.com.dea.management.project.ProjectTestUtils;
import br.com.dea.management.project.domain.Project;
import br.com.dea.management.project.repository.ProjectRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProjectUpdateSuccessTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AcademyClassRepository academyClassRepository;

    @Autowired
    private ProjectTestUtils projectTestUtils;

    @Autowired
    private EmployeeTestUtils employeeTestUtils;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    void whenRequestingProjectUpdateWithAValidPayload_thenUpdateTheProjectSuccessfully() throws Exception {
        this.academyClassRepository.deleteAll();
        this.projectRepository.deleteAll();
        this.employeeRepository.deleteAll();

        this.projectTestUtils.createFakeProject(1);
        Project fakeProject = this.projectRepository.findAll().get(0);

        Employee scrumMaster = this.employeeRepository.findAll().get(0);
        Employee productOwner = this.employeeRepository.findAll().get(1);

        String payload = "{" +
                "\"startDate\": \"2023-01-02\"," +
                "\"endDate\": \"2024-01-02\"," +
                "\"name\": \"wemob\"," +
                "\"client\": \"weg\"," +
                "\"externalProductManager\": \"weggerson\"," +
                "\"scrumMasterId\": " + scrumMaster.getId() + "," +
                "\"productOwnerId\":" + productOwner.getId() +
                "}";
        mockMvc.perform(put("/project/" + fakeProject.getId())
                        .contentType(APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isOk());

        Project project = this.projectRepository.findAll().get(0);

        assertThat(project.getStartDate()).isEqualTo("2023-01-02");
        assertThat(project.getEndDate()).isEqualTo("2024-01-02");
        assertThat(project.getName()).isEqualTo("wemob");
        assertThat(project.getClient()).isEqualTo("weg");
        assertThat(project.getExternalProductManager()).isEqualTo("weggerson");
        assertThat(project.getProductOwner().getId()).isEqualTo(productOwner.getId());
        assertThat(project.getScrumMaster().getId()).isEqualTo(scrumMaster.getId());

    }
}
