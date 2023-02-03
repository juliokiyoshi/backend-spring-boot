package br.com.dea.management.student.repository;

import br.com.dea.management.student.domain.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * Query que retorna todos os estudantes utilizando paginação
     */
    @Query("SELECT s FROM Student s")
    public Page<Student> findAllPaginated(Pageable pageable);
}
