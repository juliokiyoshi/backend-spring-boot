package br.com.dea.management.user.controller;

import br.com.dea.management.student.domain.Student;
import br.com.dea.management.student.dto.StudentDto;
import br.com.dea.management.student.service.StudentService;
import br.com.dea.management.user.domain.User;
import br.com.dea.management.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Tag(name = "User", description = "The User API")
public class UserController {
    @Autowired
    UserService userService;

    @Operation(summary = "Load the list of Users with all attributes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "500", description = "Error fetching user list"),
    })
    @Deprecated
    @RequestMapping(value = "/user/all", method = RequestMethod.GET)
    public List<User> getUserAllRaw() {
        return this.userService.findAllUsers();
    }

    @Operation(summary = "Load the user by email.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Student Id invalid"),
            @ApiResponse(responseCode = "404", description = "Student Not found"),
            @ApiResponse(responseCode = "500", description = "Error fetching student list"),
    })
    @GetMapping("/user/{email}")
    public User getUsers(@PathVariable String email) {

        log.info(String.format("Fetching student by id : Id : %s", email));

        User user = this.userService.findUserByEmail(email);

        log.info(String.format("Student loaded successfully : Student : %s", user));

        return user;

    }

}

