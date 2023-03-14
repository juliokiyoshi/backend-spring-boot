package br.com.dea.management.employee.repository;
import br.com.dea.management.employee.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e")
    @EntityGraph(attributePaths = {"position", "user"}) // usado para evitar o problema do N+1 assim evitando queries desnecessarias(busca na tabela de usuario e posição  para cada employee encontrado), podendo apenas fazer um join das tabelas para obter os dados
    public Page<Employee> findAllPaginated(Pageable pageable);

}
