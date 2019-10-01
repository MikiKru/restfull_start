package net.atos.restfull_start.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long message_id;
    @Type(type = "text")
    private String content;
    private LocalDateTime dateAdded = LocalDateTime.now();

    public Message(String content) {
        this.content = content;
    }
    @JsonIgnore                     // zignorowanie pola w api
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;



}
