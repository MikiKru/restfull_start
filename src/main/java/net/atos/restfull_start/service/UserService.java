package net.atos.restfull_start.service;

import lombok.AllArgsConstructor;
import net.atos.restfull_start.model.User;
import net.atos.restfull_start.model.dtos.UserDto;
import net.atos.restfull_start.repository.RoleRepository;
import net.atos.restfull_start.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private RoleRepository roleRepository;


    public User addUser(UserDto userDto){
        // save() -> insert into user values (?,?)
        return userRepository.save(new User(userDto.getLogin(), userDto.getPassword()));
    }
}
