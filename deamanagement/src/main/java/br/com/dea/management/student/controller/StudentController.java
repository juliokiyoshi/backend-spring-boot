package br.com.dea.management.student.controller;

import br.com.dea.management.student.domain.Student;
import br.com.dea.management.student.dto.StudentDto;
import br.com.dea.management.student.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class StudentController {
    @Autowired
    StudentService studentService;

    /**
     * This request will return all Students include Users ( 1 -1 relationship )
     * include password and email as well stay aware
     */
    @RequestMapping(value = "/student/all", method = RequestMethod.GET)
    public List<Student> getStudentsAllRaw() {
        return this.studentService.findAllStudents();
    }

    /**
     * this request will return all students but filtered by the DTO ( data transfer object)
     * go to DTO package for more information about this filtering
     */
    @RequestMapping(value = "/student/without-pagination", method = RequestMethod.GET)
    public List<StudentDto> getStudentsWithOutPagination() {
        List<Student> students = this.studentService.findAllStudents();
        return StudentDto.fromStudents(students);
    }

    /**
     * this request will return all students but filtered by the DTO ( data transfer object)
     * and use paged for give the result
     */
    @GetMapping("/student")
    public Page<StudentDto> getStudents(@RequestParam Integer page,
                                        @RequestParam Integer pageSize) {

        Page<Student> studentsPaged = this.studentService.findAllStudentsPaginated(page, pageSize);
        Page<StudentDto> students = studentsPaged.map(student -> StudentDto.fromStudent(student));
        return students;
    }

    @GetMapping("/student/{id}")
    public StudentDto getStudents(@PathVariable Long id) {

        log.info(String.format("Fetching student by id : Id : %s", id));

        StudentDto student = StudentDto.fromStudent(this.studentService.findStudentById(id));

        log.info(String.format("Student loaded successfully : Student : %s", student));

        return student;

    }
}
