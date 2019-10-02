package net.atos.restfull_start.repository;

import net.atos.restfull_start.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Long> {
}
