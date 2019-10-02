package net.atos.restfull_start.controller;

import lombok.AllArgsConstructor;
import net.atos.restfull_start.model.Role;
import net.atos.restfull_start.model.dtos.RoleDto;
import net.atos.restfull_start.model.enums.RoleEnum;
import net.atos.restfull_start.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/roles")
@RestController
@AllArgsConstructor
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping("/add/role")
    public Role addRole(@RequestParam String role){
        return roleService.addRole(new RoleDto(role));
    }
    @GetMapping("/role/{role_id}")
    public Role getRoleById(@PathVariable Long role_id){
        return roleService.getRoleById(role_id);
    }
}
