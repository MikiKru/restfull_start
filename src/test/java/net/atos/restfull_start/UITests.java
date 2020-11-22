package net.atos.restfull_start;

import net.atos.restfull_start.controller.UserController;
import net.atos.restfull_start.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UITests {

    @Autowired
    private UserController userController;

    @LocalServerPort
    private int port;

//    @Test
//    public void portUITests() throws URISyntaxException {
//        User user = new User("test@test.pl", "Test123");
//        Mockito.when(
//                requestTo(new URI(String.format("http://localhost:%s/api/v1/user/1", port))))
//                .thenReturn(Optional.ofNullable(user));
//
//    }
}
