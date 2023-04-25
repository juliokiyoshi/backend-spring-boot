package br.com.dea.management.project.service;

import br.com.dea.management.employee.domain.Employee;
import br.com.dea.management.employee.repository.EmployeeRepository;
import br.com.dea.management.exceptions.GlobalExceptionHandler;
import br.com.dea.management.exceptions.InvalidParameterException;
import br.com.dea.management.exceptions.NotFoundException;
import br.com.dea.management.project.domain.Project;
import br.com.dea.management.project.dto.CreateProjectRequestDto;
import br.com.dea.management.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Page<Project> findAllProjectPaginated(Integer page, Integer size) {
        return this.projectRepository.findAllPaginated(PageRequest.of(page, size, Sort.by("name").ascending()));
    }

    public Project findProjectById(Long id) {
        return this.projectRepository.findById(id).orElseThrow(() -> new NotFoundException(Project.class, id));
    }

    public Project createProject(CreateProjectRequestDto createProjectRequestDto) {
        Employee scrumMaster = this.employeeRepository.findById(createProjectRequestDto.getScrumMasterId())
                .orElseThrow(() -> new NotFoundException(Employee.class, createProjectRequestDto.getScrumMasterId()));

        Employee productOwner = this.employeeRepository.findById(createProjectRequestDto.getProductOwnerId())
                .orElseThrow(() -> new NotFoundException(Employee.class, createProjectRequestDto.getProductOwnerId()));

        if (scrumMaster == productOwner){
            throw new InvalidParameterException(Project.class,createProjectRequestDto.getProductOwnerId(),createProjectRequestDto.getProductOwnerId() );
        }

        Project project = Project.builder()
                .name(createProjectRequestDto.getName())
                .client(createProjectRequestDto.getClient())
                .externalProductManager(createProjectRequestDto.getExternalProductManager())
                .startDate(createProjectRequestDto.getStartDate())
                .endDate(createProjectRequestDto.getEndDate())
                .scrumMaster(scrumMaster)
                .productOwner(productOwner)
                .build();

        return this.projectRepository.save(project);
    }
}
