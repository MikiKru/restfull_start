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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RequestMapping(value = "/api/v2/", produces = MediaType.TEXT_PLAIN_VALUE)
@RestController("userControllerV2")
@AllArgsConstructor
public class UserControllerV2 {
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
    // produkujemy format plaintext
    @GetMapping(value = "/user/{user_id}")
    public String getUserByIdTEXT(@PathVariable Long user_id){
        User user = userService.getUserById(user_id);
        String output =user.getLogin() + ";" + user.getPassword() + ";" + user.getStatus();
        return output;
    }
}
