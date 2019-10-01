package net.atos.restfull_start.repository;

import net.atos.restfull_start.model.Role;
import net.atos.restfull_start.model.User;
import net.atos.restfull_start.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository                             // <nazwa encji, typ klucza głównego>
public interface UserRepository extends JpaRepository<User, Long> {
    // SELECT * FROM user WHERE role = 'ROLE_ADMIN'
    List<User> findAllByRolesIn(List<Role> role);
    // SELECT * FROM user ORDER BY login
    List<User> findAllByLoginLikeOrderByLogin(String login);
    // SELECT * FROM user WHERE status = true ORDER BY registerDate
    List<User> findAllByStatusOrderByRegisterDateAsc(Boolean status);

}
