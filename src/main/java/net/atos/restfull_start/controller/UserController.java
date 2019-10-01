package net.atos.restfull_start.controller;

import lombok.AllArgsConstructor;
import net.atos.restfull_start.model.User;
import net.atos.restfull_start.model.dtos.UserDto;
import net.atos.restfull_start.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api")
@RestController
@AllArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add/user")
    public User addUser(
            @RequestParam String login,
            @RequestParam String password){
        return userService.addUser(new UserDto(login,password));
    }
    @PutMapping("/add/role")
    public User addRoleToUser(
            @RequestParam Long user_id,
            @RequestParam Long role_id){
        return userService.addRoleToUserById(user_id,role_id);
    }

}
