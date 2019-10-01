package net.atos.restfull_start.controller;

import lombok.AllArgsConstructor;
import net.atos.restfull_start.model.User;
import net.atos.restfull_start.model.dtos.UserDto;
import net.atos.restfull_start.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RequestMapping(value = "/api")
@RestController
@AllArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;
    /*
    1. DELETE -> usuń użytkownika
    2. Wypisz wszystkich użytkowników posortowanych po loginie
    --------------------------------------------------------------
    3. Wypisz użytkowników posiadających role ROLE_ADMIN
    4. Wypisz użytkowników aktywnych i posiadających rolę ROLE_USER
     */
    @GetMapping("/users")
    public List<User> getAllUsersSortedByLogin(){
        return userService.getAllUsersSortedByLogin();
    }

    @DeleteMapping("/delete/user/{user_id}")
    public ResponseEntity deleteUserById(@PathVariable Long user_id){
        userService.deleteUserById(user_id);
        return ResponseEntity.ok(200);
    }

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
