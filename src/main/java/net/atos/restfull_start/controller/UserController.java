package net.atos.restfull_start.controller;

import lombok.AllArgsConstructor;
import net.atos.restfull_start.model.Message;
import net.atos.restfull_start.model.Role;
import net.atos.restfull_start.model.User;
import net.atos.restfull_start.model.dtos.UserDto;
import net.atos.restfull_start.service.RoleService;
import net.atos.restfull_start.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RequestMapping(value = "/api/v1/", produces = "application/hal+json")
@RestController("userControllerV1")
@AllArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleController roleController;
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
    @GetMapping(value = "/nativeQuery/aggregated", produces = MediaType.APPLICATION_XML_VALUE)
    public List<String> getAggregatedValuesNativeQuery(){
        return userService.getAggregatedValuesNativeQuery();
    }

    @GetMapping("/nativeQuery/messages")
    public List<Message> getAllMessagesNativeQuery(){
        return userService.getAllMessagesCustomQuery();
    }

    @GetMapping(value = "/users/{user_id}", produces = "application/hal+xml")
    public User getUserByIdXML(@PathVariable Long user_id){
        return userService.getUserById(user_id);
    }
    // produkujemy format JSON
    @GetMapping(value = "/user/{user_id}", produces = "application/json")
    public User getUserById(@PathVariable Long user_id){
        return userService.getUserById(user_id);
    }
    // produkujemy format hal+json
    @GetMapping(value = "/user/{user_id}")
    public Resource<User> getUserByIdHATEOAS(@PathVariable Long user_id){
        User user = userService.getUserById(user_id);
        System.out.println("user" + user);
        // ręczna konfiguracja lików
//        Link userLink = new Link("localhost:8080/api/user/"+user_id+"/HATEOAS");
        Link userLink = linkTo(
                methodOn(UserController.class).getUserByIdHATEOAS(user_id))
                .withSelfRel();
        Link userLinkTemplate = linkTo(
                methodOn(UserController.class).getUserByIdHATEOAS(null))
                .withRel("userLinkTemplate");
        // user extends ResourceSupport może przyjąć obiekt klasy Link
        user.add(userLink);
        user.add(userLinkTemplate);
        // zwracam obiekt HATEOAS = data + href
        return new Resource<>(user);
    }
    @GetMapping("/users")
    public List<User> getAllUsersSortedByLogin(){
        return userService.getAllUsersSortedByLogin();
    }
    @GetMapping("/message/{message_id}")
    public Message getMessageById(@PathVariable Long message_id){
           return userService.getMessageById(message_id);
    }
    @GetMapping("/user/messages/{user_id}/HATEOAS")
    public List<Message> getMessagesForUserHATEOAS(@PathVariable Long user_id){
        return userService.getMessagesForUser(user_id);
    }
    @GetMapping("/users/HATEOAS")
    public Resources<User> getAllUsersSortedByLoginHATEOAS(){
        List<User> users = userService.getAllUsersSortedByLogin();
        for(User user : users){
            // link do użytkownika
            Link userLink = linkTo(methodOn(UserController.class)
                    .getUserByIdHATEOAS(user.getUser_id())).withSelfRel();
            user.add(userLink);
            if(user.getMessages().size() > 0){
                // link do wiadomości
                for (Message message : user.getMessages()){
                    Link messageLink = linkTo(methodOn(UserController.class).getMessageById(message.getMessage_id())).withSelfRel();
                    message.add(messageLink);
                }
                // link do listy wiadomości
                Link messageListLink = linkTo(methodOn(UserController.class).getMessagesForUser(user.getUser_id())).withRel("messageList");
                user.add(messageListLink);
            }
            if(user.getRoles().size() > 0){
                for(Role role : user.getRoles()){
                    Link rolesLink = linkTo(methodOn(RoleController.class).getRoleById(role.getRole_id())).withSelfRel();
                    if(!role.getLinks().contains(rolesLink))
                        role.add(rolesLink);
                }
            }
        }
        Link link = linkTo(methodOn(UserController.class).getAllUsersSortedByLoginHATEOAS()).withSelfRel();
        Resources<User> userResources = new Resources<>(users, link);
        Link userLink = linkTo(methodOn(UserController.class).getUserByIdHATEOAS(null)).withRel("userLinkTempl");
        Link messageLink = linkTo(methodOn(UserController.class).getMessageById(null)).withRel("messageLinkTempl");
        Link messageListLink = linkTo(methodOn(UserController.class).getMessagesForUser(null)).withRel("messageListTempl");
        Link roleLink = linkTo(methodOn(RoleController.class).getRoleById(null)).withRel("roleLinkTempl");
        userResources.add(userLink);
        userResources.add(messageLink);
        userResources.add(messageListLink);
        userResources.add(roleLink);
        return userResources;
    }



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
