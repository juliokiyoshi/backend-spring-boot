package br.com.dea.management;

import br.com.dea.management.student.domain.Student;
import br.com.dea.management.student.repository.StudentRepository;
import br.com.dea.management.user.domain.User;
import br.com.dea.management.user.repository.UserRepository;
import br.com.dea.management.user.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@OpenAPIDefinition(
		info = @Info(title = "Dea Management", version = "1.0", description = "Dea Management API Description"),
		servers = {
				@Server(url = "http://localhost:8082${server.servlet.contextPath}", description = "Local environment URL"),
				@Server(url = "https://deamanagement.com.br${server.servlet.contextPath}", description = "Development environment URL")
		}
)

@SpringBootApplication
public class DeamanagementApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DeamanagementApplication.class, args);
	}

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StudentRepository studentRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void run(String... args) throws Exception {
		//Deleting all Users
		this.userRepository.deleteAll();

		//Creating some user
		for (int i = 0; i < 10; i++) {

			// METODO ANTIGO PARA CRIAR O OBJETO USER AGORA PODEMOS UTILIZAR O PADRAO BUILDER DO LAMBOK
//			User u = new User();
//			u.setEmail("julio+" + i+"@gmail.com");
//			u.setName("julio+" + i);
//			u.setLinkedin("linkedin " + i);
//			u.setPassword("pwd " + i);

			User u = User.builder()
					.email("julio+" + i+"@gmail.com")
					.name("julio" + i)
					.linkedin("linkedin " + i)
					.password("pwd $i")
					.build();
			u.getLinkedin(); // get customizado

			Student s = Student.builder()
					.university("UNICAMP")
					.finishDate(LocalDate.now())
					.graduation("grad $i")
					.user(u)
					.build();
			this.studentRepository.save(s);
		}

		//Loading all Users
		List<User> users = this.userService.findAllUsers();
		users.forEach(u -> System.out.println("Name: " + u.getName()));

		//Loading by @Query
		Optional<User> loadedUserByName = this.userRepository.findByName("julio1");
		System.out.println("User name 1 (From @Query) name: " + loadedUserByName.get().getName());

		TypedQuery<User> q = entityManager.createNamedQuery("myQuery", User.class);
		q.setParameter("name", "julio2");
		User userFromNamedQuery = q.getResultList().get(0);
		System.out.println("User name 2 (From NamedQuery) name: " + userFromNamedQuery.getName());

		//Loading user by email
		User loadedUser = this.userService.findUserByEmail("julio+1@gmail.com");
		System.out.println("User email 1 name: " + loadedUser.getName());

		//Updating user name 1 linkedin
		loadedUser.setLinkedin("new linkedin");
		this.userRepository.save(loadedUser);
	}
}
