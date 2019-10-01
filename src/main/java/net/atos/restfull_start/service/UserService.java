package net.atos.restfull_start.service;

import lombok.AllArgsConstructor;
import net.atos.restfull_start.model.Role;
import net.atos.restfull_start.model.User;
import net.atos.restfull_start.model.dtos.UserDto;
import net.atos.restfull_start.repository.RoleRepository;
import net.atos.restfull_start.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public User addUser(UserDto userDto){
        // save() -> insert into user values (?,?)
        return userRepository.save(new User(userDto.getLogin(), userDto.getPassword()));
    }
    // metoda dodająca wybraną po id rolę do użytkownika
    public User addRoleToUserById(Long user_id, Long role_id){
        // wyszukaj użytkownika po id
        Optional<User> userOptional = userRepository.findById(user_id);
        Optional<Role> roleOptional = roleRepository.findById(role_id);
        if(userOptional.isPresent() && roleOptional.isPresent()){
            // przypisanie roli do użytkownika -> dodanie roli do zbioru roles klasy user
            User user = userOptional.get();
            user.addRole(roleOptional.get());
            return userRepository.save(user);
        }
        return null;
    }
    // metoda usuwająca użytkownika po id
    public void deleteUserById(Long user_id){
        userRepository.deleteById(user_id);
    }
    // metoda pobierająca wszystkich użytkowników posortowanych po loginie
    public List<User> getAllUsersSortedByLogin(){
        return userRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(User::getLogin))
                .collect(Collectors.toList());
    }
    public List<User> getUsersWithRole(List<Role> roles){
        return userRepository.findAllByRolesIn(roles);
    }
    public List<User> getAllUsersOrderByLogin(){
        return userRepository.findAllByLoginLikeOrderByLogin("%");
    }
    public List<User> getUsersWithStatusOrderedByregisterDate(Boolean status){
        return userRepository.findAllByStatusOrderByRegisterDateAsc(status);
    }









}
