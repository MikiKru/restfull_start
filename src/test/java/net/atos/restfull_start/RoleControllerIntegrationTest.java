package net.atos.restfull_start;

import net.atos.restfull_start.controller.RoleController;
import net.atos.restfull_start.controller.UserController;
import net.atos.restfull_start.model.Role;
import net.atos.restfull_start.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleControllerIntegrationTest {

    @Autowired
    private RoleController roleController;

    @Autowired
    private UserController userController;

    @Test
    public void givenMovieControllerWhenQueriedWithAnIdThenGetExpectedMovie() {
        User user = new User("test@test.pl", "Test123");
        userController.addUser("test@test.pl", "Test123");
        User result = userController.getUserById(1L);

        Assert.assertEquals(user.getLogin(),result.getLogin());
    }
}
