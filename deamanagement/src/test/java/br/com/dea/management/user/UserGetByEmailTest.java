package br.com.dea.management.user;

import br.com.dea.management.student.domain.Student;
import br.com.dea.management.user.domain.User;
import br.com.dea.management.user.repository.UserRepository;
import br.com.dea.management.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class UserGetByEmailTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    void whenRequestingAnExistentUserByEmail_thenReturnTheStudentSuccessfully() throws Exception {
        this.userRepository.deleteAll();
        this.createMockedUser(10);


        mockMvc.perform(get("/user/email 0" ))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("name 0")))
                .andExpect(jsonPath("$.email", is("email 0")))
                .andExpect(jsonPath("$.linkedin", is("linkedin 0")));
    }

    @Test
    void whenRequestingAnNonExistentUserByEmail_thenReturnTheNotFound() throws Exception {

        mockMvc.perform(get("/user/50000"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));
    }



    private void createMockedUser(int amount) {
        for (int i = 0; i < amount; i++) {
            User u = new User();
            u.setEmail("email " + i);
            u.setName("name " + i);
            u.setLinkedin("linkedin " + i);
            u.setPassword("pwd " + i);

            this.userRepository.save(u);
        }
    }
}
