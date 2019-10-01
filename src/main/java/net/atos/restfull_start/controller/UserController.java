package net.atos.restfull_start.controller;

import lombok.AllArgsConstructor;
import net.atos.restfull_start.model.User;
import net.atos.restfull_start.model.dtos.UserDto;
import net.atos.restfull_start.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api")
@RestController
@AllArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add/user")
    public User addUser(@RequestParam String login, @RequestParam String password){
        return userService.addUser(new UserDto(login,password));
    }
}
