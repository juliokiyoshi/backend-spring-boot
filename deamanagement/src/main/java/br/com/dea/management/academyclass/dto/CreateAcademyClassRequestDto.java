package br.com.dea.management.academyclass.dto;


import br.com.dea.management.academyclass.ClassType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateAcademyClassRequestDto {
    @NotNull(message = "Start date could not be null")
    private LocalDate start_date;

    @NotNull(message = "End Date could not be null")
    private LocalDate end_date;

    private ClassType class_type;

    private Long instructor_id;

}
