package net.atos.restfull_start.repository;

import net.atos.restfull_start.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository                             // <nazwa encji, typ klucza głównego>
public interface UserRepository extends JpaRepository<User, Long> {
}
