package br.com.dea.management.user.controller;

import br.com.dea.management.student.domain.Student;
import br.com.dea.management.student.dto.StudentDto;
import br.com.dea.management.student.service.StudentService;
import br.com.dea.management.user.domain.User;
import br.com.dea.management.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/user/all", method = RequestMethod.GET)
    public List<User> getUserAllRaw() {
        return this.userService.findAllUsers();
    }

    @GetMapping("/user/{email}")
    public User getUsers(@PathVariable String email) {

        log.info(String.format("Fetching student by id : Id : %s", email));

        User user = this.userService.findUserByEmail(email);

        log.info(String.format("Student loaded successfully : Student : %s", user));

        return user;

    }

}

