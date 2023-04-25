package br.com.dea.management.project.dto;

import br.com.dea.management.academyclass.domain.AcademyClass;
import br.com.dea.management.employee.dto.EmployeeDto;
import br.com.dea.management.project.domain.Project;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProjectReturnDto {
    private Long id;
    private String name;
    private String client;
    private String externalProductManager;
    private LocalDate startDate;
    private LocalDate endDate;
    private EmployeeDto productOwner;
    private EmployeeDto scrumMaster;

    public static List<ProjectReturnDto> fromProject(List<Project> projects) {
        return projects.stream().map(project -> {
            ProjectReturnDto projectDto = ProjectReturnDto.fromProject(project);
            return projectDto;
        }).collect(Collectors.toList());
    }

    public static ProjectReturnDto fromProject(Project project) {
        ProjectReturnDto projectDto = new ProjectReturnDto();
        projectDto.setId(project.getId());
        projectDto.setStartDate(project.getStartDate());
        projectDto.setEndDate(project.getEndDate());
        projectDto.setClient(project.getClient());
        projectDto.setName(project.getName());
        projectDto.setExternalProductManager(project.getExternalProductManager());

        projectDto.setScrumMaster(EmployeeDto.fromEmployee(project.getScrumMaster()));
        projectDto.setProductOwner(EmployeeDto.fromEmployee(project.getProductOwner()));

        return projectDto;
    }

}
