package net.atos.restfull_start.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

// obiekt DTO - data transfer object
// służy do przekazania parametrów od użytkownika do modelu
// adnotacje do walidacji formularzy
@Data
@AllArgsConstructor
public class UserDto {
    private String login;
    private String password;
}
