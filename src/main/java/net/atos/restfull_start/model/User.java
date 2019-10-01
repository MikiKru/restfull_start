package net.atos.restfull_start.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.atos.restfull_start.model.enums.RoleEnum;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity                     // mapowanie ORM
@Table(name = "user_table") // określenie nazwy tabeli w SQL
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id                                                 // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long user_id;
    @Column(name = "user_login")                        // zmiana nazwy kolumny
    private String login;
    @Column(name = "user_password")
    private String password;
    // przypisanie aktualnej daty i czasu
    private LocalDateTime registerDate = LocalDateTime.now();
    private Boolean status = false;
//    private RoleEnum role = RoleEnum.ROLE_USER;
    @Transient                                          // wykluczenie w mapowaniu pola
    private String token = "secret token";

    @ManyToMany                                                     // utworzenie relacji n : m
    @JoinTable(
            name = "user_role",                                     // nazwa tabeli
            joinColumns = @JoinColumn(name = "user_id"),            // nazwa klucza obcego user_id
            inverseJoinColumns =  @JoinColumn(name = "role_id")     // nazwa klucza obcego role_id
    )      // utworzenie tabelki relacyjnej o określonej nazwie i kolumnach
    private Set<Role> roles = new HashSet<>();

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
