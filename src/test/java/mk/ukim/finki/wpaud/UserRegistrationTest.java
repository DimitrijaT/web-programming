package mk.ukim.finki.wpaud;

import mk.ukim.finki.wpaud.model.Role;
import mk.ukim.finki.wpaud.model.User;
import mk.ukim.finki.wpaud.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.wpaud.model.exceptions.InvalidUsernameOrPasswordException;
import mk.ukim.finki.wpaud.model.exceptions.PasswordsDoNotMatchException;
import mk.ukim.finki.wpaud.model.exceptions.UsernameAlreadyExistsException;
import mk.ukim.finki.wpaud.repository.jpa.UserRepository;
import mk.ukim.finki.wpaud.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserRegistrationTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private UserServiceImpl service;


    // Иницијализација на самиот тест
    @Before
    public void init() {
        // Kaжува да ги испроцесира сите mock анотации и да се симулираат
        MockitoAnnotations.initMocks(this);
        // Помошен user Објект кој ќе го користиме како резултат од снимањето
        User user = new User("username", "password", "name", "surename", Role.ROLE_USER);

        // Кога ќе се повика userRepository.save кај што аргументот од save е било која инстанца од user класата тогаш ќе вратиме user Објект - односно ќе го вратиме тој user објект од претходно (погоре).
        Mockito.when(this.userRepository.save(Mockito.any(User.class))).thenReturn(user);

        // Кога ќе се повика encode Методот од било кој стринг тогаш ние да вратиме password како резултат
        Mockito.when(this.passwordEncoder.encode(Mockito.anyString())).thenReturn("password");

        // Иницијализација на UserServiceImpl обвиткан со proxy околу него од мокито (spy)
        // Со ова proxy може да верифицираме дали некој метод е повикан и дали е повикан со соодвентите аргументи со соодвентиот повик
        service = Mockito.spy(new UserServiceImpl(this.userRepository, this.passwordEncoder));
    }

    @Test
    public void testSuccessRegister() {
        User user = this.service.register("username", "password", "password", "name", "surename", Role.ROLE_USER);

        // Верификуваме дали е навистина повикан овој метод.
        Mockito.verify(this.service).register("username", "password", "password", "name", "surename", Role.ROLE_USER);

        Assert.assertNotNull("User is null", user);
        Assert.assertEquals("name does not match", "name", user.getName());
//        Assert.assertEquals("role does not match", Role.ROLE_USER, user.getRole());
        Assert.assertEquals("surename does not match", "surename", user.getSurname());
        Assert.assertEquals("password does not match", "password", user.getPassword());
        Assert.assertEquals("username does not match", "username", user.getUsername());
    }

    @Test
    public void testNullUsername() {
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidUsernameOrPasswordException.class,
                () -> this.service.register(null, "password", "password", "name", "surename", Role.ROLE_USER));

        Mockito.verify(this.service).register(null, "password", "password", "name", "surename", Role.ROLE_USER);
    }

    @Test
    public void testEmptyUsername() {
        String username = "";
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidUsernameOrPasswordException.class,
                () -> this.service.register(username, "password", "password", "name", "surename", Role.ROLE_USER));

        Mockito.verify(this.service).register(username, "password", "password", "name", "surename", Role.ROLE_USER);
    }

    @Test
    public void testEmptyPassword() {
        String username = "username";
        String password = "";
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidUsernameOrPasswordException.class,
                () -> this.service.register(username, password, "password", "name", "surename", Role.ROLE_USER));

        Mockito.verify(this.service).register(username, password, "password", "name", "surename", Role.ROLE_USER);
    }

    @Test
    public void testNullPassword() {
        String username = "username";
        String password = null;
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidUsernameOrPasswordException.class,
                () -> this.service.register(username, password, "password", "name", "surename", Role.ROLE_USER));

        Mockito.verify(this.service).register(username, password, "password", "name", "surename", Role.ROLE_USER);
    }


    @Test
    public void testPasswordMismatch() {
        String username = "username";
        String password = "password";
        String confirmPassword = "otherPassword";
        Assert.assertThrows("PasswordsDoNotMatchException expected",
                PasswordsDoNotMatchException.class,
                () -> this.service.register(username, password, confirmPassword, "name", "surename", Role.ROLE_USER));
        Mockito.verify(this.service).register(username, password, confirmPassword, "name", "surename", Role.ROLE_USER);
    }

    @Test
    public void testDuplicateUsername() {
        User user = new User("username", "password", "name", "surename", Role.ROLE_USER);
        Mockito.when(this.userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(user));
        String username = "username";
        Assert.assertThrows("UsernameAlreadyExistsException expected",
                UsernameAlreadyExistsException.class,
                () -> this.service.register(username, "password", "password", "name", "surename", Role.ROLE_USER));

        Mockito.verify(this.service).register(username, "password", "password", "name", "surename", Role.ROLE_USER);
    }

}
