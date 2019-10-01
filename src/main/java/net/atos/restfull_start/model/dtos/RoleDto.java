package net.atos.restfull_start.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.atos.restfull_start.model.enums.RoleEnum;

@Data
@AllArgsConstructor
public class RoleDto {
    private String role;
}
