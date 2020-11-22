package net.atos.restfull_start;

import net.atos.restfull_start.model.Role;
import net.atos.restfull_start.model.User;
import net.atos.restfull_start.repository.RoleRepository;
import net.atos.restfull_start.repository.UserRepository;
import net.atos.restfull_start.service.RoleService;
import net.atos.restfull_start.service.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

public class RoleServiceUnitTest {
    @InjectMocks   // wstrzykuje instancję klasy UserService
    private UserService userService;

    @Mock // wstryknięcie zależności do obiektu userService
    private UserRepository userRepository;

//    @After    - wykonywane po każdym teście
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void givenMovieServiceWhenQueriedWithAnIdThenGetExpectedMovie() {
        User user = new User("test@test.pl", "Test123");
        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.ofNullable(user));
        User result = userService.getUserById(1L);
        System.out.println(result);
        Assert.assertEquals(user.getLogin(),result.getLogin());
    }
}
