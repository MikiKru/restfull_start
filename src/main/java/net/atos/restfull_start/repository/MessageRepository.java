package net.atos.restfull_start.repository;

import net.atos.restfull_start.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    @Query(value = "SELECT * FROM message", nativeQuery = true)
    List<Message> getAllMessagesQuery();

    @Query(value = "SELECT user_id, count(*) FROM message GROUP BY user_id", nativeQuery = true)
    List<String> getAggregatedValues();

}
