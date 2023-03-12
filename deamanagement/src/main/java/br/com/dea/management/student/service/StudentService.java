package br.com.dea.management.student.service;

import br.com.dea.management.student.domain.Student;
import br.com.dea.management.student.dto.CreateStudentRequestDto;
import br.com.dea.management.student.dto.UpdateStudentRequestDto;
import br.com.dea.management.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.dea.management.student.repository.StudentRepository;
import org.springframework.data.domain.Page;
import br.com.dea.management.exceptions.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    /**
     * @return warning this query will return all students in a list,
     *  be aware maybe this will be done slowly
     */
    public List<Student> findAllStudents(){
        return this.studentRepository.findAll();
    }

    /**
     *
     * @param page number of page
     * @param pageSize number of elements in a single page
     * @return all element paged
     */
    public Page<Student> findAllStudentsPaginated(Integer page, Integer pageSize) {
        return this.studentRepository.findAllPaginated(PageRequest.of(page, pageSize));
    }

    public Student findStudentById(Long id) {
        return this.studentRepository.findById(id).orElseThrow(() -> new NotFoundException(Student.class, id));
    }

    public Student createStudent(CreateStudentRequestDto createStudentRequestDto) {
        User user = User.builder()
                .name(createStudentRequestDto.getName())
                .email(createStudentRequestDto.getEmail())
                .password(createStudentRequestDto.getPassword())
                .linkedin(createStudentRequestDto.getLinkedin())
                .build();

        Student student = Student.builder()
                .user(user)
                .finishDate(createStudentRequestDto.getFinishDate())
                .graduation(createStudentRequestDto.getGraduation())
                .university(createStudentRequestDto.getUniversity())
                .build();

        return this.studentRepository.save(student);
    }

    public Student updateStudent(Long studentId, UpdateStudentRequestDto updateStudentRequestDto) {
        Student student = this.findStudentById(studentId);
        User user = student.getUser();

        user.setName(updateStudentRequestDto.getName());
        user.setEmail(updateStudentRequestDto.getEmail());
        user.setPassword(updateStudentRequestDto.getPassword());
        user.setLinkedin(updateStudentRequestDto.getLinkedin());

        student.setUser(user);
        student.setFinishDate(updateStudentRequestDto.getFinishDate());
        student.setGraduation(updateStudentRequestDto.getGraduation());
        student.setUniversity(updateStudentRequestDto.getUniversity());

        return this.studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        Student student = this.findStudentById(studentId);
        this.studentRepository.delete(student);
    }
}
