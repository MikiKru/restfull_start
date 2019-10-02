package net.atos.restfull_start.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.*;

@Entity                     // mapowanie ORM
@Table(name = "user_table") // określenie nazwy tabeli w SQL
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends ResourceSupport {             // klasa rozszerzająca dane o hiperłącza
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
    //-----------------------------------------------------------------------
    @ManyToMany                                                     // utworzenie relacji n : m
    @JoinTable(
            name = "user_role",                                     // nazwa tabeli
            joinColumns = @JoinColumn(name = "user_id"),            // nazwa klucza obcego user_id
            inverseJoinColumns =  @JoinColumn(name = "role_id")     // nazwa klucza obcego role_id
    )      // utworzenie tabelki relacyjnej o określonej nazwie i kolumnach
    private Set<Role> roles = new HashSet<>();
    // -----------------------------------------------------------------------
    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            // mapowanie pola w klasie Message -> nazwa pola
            mappedBy = "user"
    )
    private List<Message> messages = new ArrayList<>();

    public void addMessage(Message message){
        this.messages. add(message);
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public void addRole(Role role){
        this.roles.add(role);
    }
}
