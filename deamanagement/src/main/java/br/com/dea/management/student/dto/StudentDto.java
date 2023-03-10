package br.com.dea.management.student.dto;

import br.com.dea.management.student.domain.Student;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class StudentDto {

    /**
     * All data that will be made available to be consumed in the frontEnd
     */
    private String name;
    private String email;
    private String linkedin;
    private String university;
    private String graduation;
    private LocalDate finishDate;

    // method static don`t need the constructor


    public static List<StudentDto> fromStudents(List<Student> students){
        return students.stream().map( student -> {
            return StudentDto.fromStudent(student);
        }).collect(Collectors.toList());
    }

    public static StudentDto fromStudent(Student student){
        StudentDto studentDto = new StudentDto();
        studentDto.setName(student.getUser().getName());
        studentDto.setEmail(student.getUser().getEmail());
        studentDto.setLinkedin(student.getUser().getLinkedin());
        studentDto.setGraduation(student.getGraduation());
        studentDto.setUniversity(student.getUniversity());
        studentDto.setFinishDate(student.getFinishDate());

        return studentDto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getGraduation() {
        return graduation;
    }

    public void setGraduation(String graduation) {
        this.graduation = graduation;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

}
