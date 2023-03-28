package br.com.dea.management.employee.dto;

import br.com.dea.management.employee.EmployeeType;
import br.com.dea.management.position.dto.PositionDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateEmployeeRequestDto {

    private String name;
    @NotNull(message = "Email could not be null")
    @NotEmpty(message = "Email could not be empty")
    @Email(message = "Email passed is not valid!")
    private String email;
    private String linkedin;
    private EmployeeType employeeType;
    private Long position;
    @NotNull(message = "Password could not be null")
    private String password;
}
