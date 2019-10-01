package net.atos.restfull_start.service;

import lombok.AllArgsConstructor;
import net.atos.restfull_start.model.Role;
import net.atos.restfull_start.model.dtos.RoleDto;
import net.atos.restfull_start.model.enums.RoleEnum;
import net.atos.restfull_start.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role addRole(RoleDto roleDto){
        return roleRepository.save(new Role(roleDto.getRole()));
    }
}
