package br.com.dea.management.student.controller;

import br.com.dea.management.student.domain.Student;
import br.com.dea.management.student.dto.StudentDto;
import br.com.dea.management.student.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import br.com.dea.management.student.dto.UpdateStudentRequestDto;
import br.com.dea.management.student.dto.CreateStudentRequestDto;


import java.util.List;

@RestController
@Slf4j
@Tag(name = "Student", description = "The Student API")
public class StudentController {
    @Autowired
    StudentService studentService;

    /**
     * This request will return all Students include Users ( 1 -1 relationship )
     * include password and email as well stay aware
     */
    @Operation(summary = "Load the list of students with all attributes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "500", description = "Error fetching student list"),
    })
    @Deprecated
    @RequestMapping(value = "/student/all", method = RequestMethod.GET)
    public List<Student> getStudentsAllRaw() {
        return this.studentService.findAllStudents();
    }

    /**
     * this request will return all students but filtered by the DTO ( data transfer object)
     * go to DTO package for more information about this filtering
     */
    @Operation(summary = "Load all students.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "500", description = "Error fetching student list"),
    })
    @Deprecated
    @RequestMapping(value = "/student/without-pagination", method = RequestMethod.GET)
    public List<StudentDto> getStudentsWithOutPagination() {
        List<Student> students = this.studentService.findAllStudents();
        return StudentDto.fromStudents(students);
    }

    /**
     * this request will return all students but filtered by the DTO ( data transfer object)
     * and use paged for give the result
     */
    @Operation(summary = "Load the list of students paginated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Page or Page Size params not valid"),
            @ApiResponse(responseCode = "500", description = "Error fetching student list"),
    })
    @GetMapping("/student")
    public Page<StudentDto> getStudents(@RequestParam Integer page,
                                        @RequestParam Integer pageSize) {

        Page<Student> studentsPaged = this.studentService.findAllStudentsPaginated(page, pageSize);
        Page<StudentDto> students = studentsPaged.map(student -> StudentDto.fromStudent(student));
        return students;
    }

    @Operation(summary = "Load the student by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Student Id invalid"),
            @ApiResponse(responseCode = "404", description = "Student Not found"),
            @ApiResponse(responseCode = "500", description = "Error fetching student list"),
    })
    @GetMapping("/student/{id}")
    public StudentDto getStudents(@PathVariable Long id) {

        log.info(String.format("Fetching student by id : Id : %s", id));

        StudentDto student = StudentDto.fromStudent(this.studentService.findStudentById(id));

        log.info(String.format("Student loaded successfully : Student : %s", student));

        return student;

    }

    /**
     * Rotas Post
     * Obs Lembrar que a notation @Valid serve para validar da biblioteca jakarta
     */

    @Operation(summary = "Create a new Student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Payload not valid"),
            @ApiResponse(responseCode = "500", description = "Error creating student"),
    })
    @PostMapping("/student")
    public void createStudent(@Valid @RequestBody CreateStudentRequestDto createStudentRequestDto) {
        log.info(String.format("Creating Student : Payload : %s", createStudentRequestDto));

        Student student = studentService.createStudent(createStudentRequestDto);

        log.info(String.format("Student created successfully : id : %s", student.getId()));
    }

    @Operation(summary = "Update a Student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Payload not valid"),
            @ApiResponse(responseCode = "404", description = "Student not found"),
            @ApiResponse(responseCode = "500", description = "Error updating student"),
    })
    @PutMapping("/student/{studentId}")
    public void updateStudent(@PathVariable Long studentId, @Valid @RequestBody UpdateStudentRequestDto updateStudentRequestDto) {
        log.info(String.format("Updating Student : Payload : %s", updateStudentRequestDto));

        Student student = studentService.updateStudent(studentId, updateStudentRequestDto);

        log.info(String.format("Student created successfully : id : %s", student.getId()));
    }


    @Operation(summary = "Delete a Student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Payload not valid"),
            @ApiResponse(responseCode = "404", description = "Student not found"),
            @ApiResponse(responseCode = "500", description = "Error deleting student"),
    })
    @DeleteMapping("/student/{studentId}")
    public void deleteStudent(@PathVariable Long studentId) {
        log.info(String.format("Removing Student : Id : %s", studentId));

        studentService.deleteStudent(studentId);

        log.info(String.format("Student removed successfully : id : %s", studentId));
    }
}
