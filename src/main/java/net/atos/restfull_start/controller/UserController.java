package net.atos.restfull_start.controller;

import lombok.AllArgsConstructor;
import net.atos.restfull_start.model.Message;
import net.atos.restfull_start.model.User;
import net.atos.restfull_start.model.dtos.UserDto;
import net.atos.restfull_start.service.RoleService;
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
    @Autowired
    private RoleService roleService;
    /*
    1. DELETE -> usuń użytkownika
    2. Wypisz wszystkich użytkowników posortowanych po loginie
    --------------------------------------------------------------
    3. Wypisz użytkowników posiadających role ROLE_ADMIN
    4. Wypisz użytkowników aktywnych posotrotwany po dacie rejestracji
    --------------------------------------------------------------
    4*. Wyszukaj wiadomości dla użytkownika po user_id
        -> wyszukaj usera i wykonaj getMessages()
        -> wyszukaj listę messages gdy pole User należy do szukanejgo user_id
    5. Wyszukaj użytkownika po id
    6. Wyszukaj rolę po id
    7. Wyszukaj wiadomość po id
    ---------------------------------------------------------------
    8. Wyszukaj zbiór ról dla użytkownika o danym id
    9. Wyszukaj listę wiadomości użytkownika
    ---------------------------------------------------------------
    10. Wyszukaj wiadomości stronicowane tj,  druga strona i stronicowanie co 2 szt
    11. Zmodyfikuj content wiadomości wybranej po id
    12. Sprawdź ile jest wszystkich wiadomości
    13. Sprawdź ile wiadomości napisał każdy z userów
     */
    @GetMapping("/messages/count")
    public Long countMessages(){
        return userService.countAllMessages();
    }
    @GetMapping("/messages/group")
    public Map<String, Integer> groupMessages(){
        return userService.groupMessagesByUsers();
    }

    @GetMapping("/messages")
    public List<Message> getAllMessages(){
        return userService.getAllMessages();
    }
    @GetMapping("/messages/page")
    public List<Message> getPagingMessages(
            @RequestParam int page, @RequestParam int size){
        return userService.getPagingMessages(page,size);
    }

    @GetMapping("/user/messages/{user_id}")
    public List<Message> getMessagesForUser(@PathVariable Long user_id){
        return userService.getUserById(user_id).getMessages();
    }
    @PostMapping("/user/message/")
    public ResponseEntity addMessageByUser(
            @RequestParam Long user_id,
            @RequestParam String content){
        userService.addMessageByUser(user_id, content);
        return ResponseEntity.ok(200);
    }
    @PostMapping("/add/message/{user_id}")
    public Message addMessage(@PathVariable Long user_id,@RequestParam String content){
        return userService.addMessage(content, user_id);
    }

    @GetMapping("/userswithrole")
    public List<User> getUsersWithRole(@RequestParam Long role_id){
        // szukamy roli zidentyfikowanej po id, która jest dodana do listy ról
        return userService.getUsersWithRole(
                new ArrayList<>(Arrays.asList(roleService.getRoleById(role_id)))
        );
    }
    @GetMapping("/users/ordered")
    public List<User> getUsersOrderedByLogin(){
        return userService.getAllUsersOrderByLogin();
    }
    @GetMapping("/users/active")
    public List<User> getActiveUsers(){
        return userService.getUsersWithStatusOrderedByregisterDate(true);
    }
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
