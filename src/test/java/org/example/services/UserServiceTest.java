package org.example.services;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService underTest;

    @BeforeEach
    public void setUp() {
        underTest = new UserService(userRepository, new BCryptPasswordEncoder(10));
    }

    @Test
    public void createEntityTest(){
        User user = new User();
        user.setUsername("Matvey");
        user.setId(1L);
        user.setPassword("12345");

        underTest.createEntity(user);

        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void readAllEntityTest() {
        User user1 = new User();
        user1.setUsername("Matvey");
        user1.setId(1L);
        user1.setPassword("12345");

        User user2 = new User();
        user2.setUsername("Yuriy");
        user2.setId(2L);
        user2.setPassword("54321");

        Mockito.when(userRepository.findAll()).thenReturn(List.of(user1, user2));
        List<User> expected = underTest.readAllEntity();

        Mockito.verify(userRepository, Mockito.times(1)).findAll();
        assertThat(expected).isNotNull();
        assertThat(expected.size()).isEqualTo(2);
        assertThat(expected.get(0).getUsername()).isEqualTo("Matvey");
        assertThat(expected.get(1).getUsername()).isEqualTo("Yuriy");

    }

    @Test
    void readOneEntityTest() {
        User user = new User();
        user.setUsername("Matvey");
        user.setId(1L);
        user.setPassword("12345");

        Mockito.when(userRepository.getById(1L)).thenReturn(user);
        User expected = underTest.readOneEntity(1L);

        Mockito.verify(userRepository, Mockito.times(1)).getById(1L);
        assertThat(expected.getUsername()).isEqualTo("Matvey");

    }

    @Test
    void updateEntityTest() {
        User user = new User();
        user.setUsername("Matvey");
        user.setId(1L);
        underTest.updateEntity(user, user.getId());

        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void deleteEntity(){
        underTest.deleteEntity(1L);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void signUpUserTest(){
        User user = new User();
        user.setUsername("Matvey");
        user.setId(1L);
        user.setPassword("12345");

        underTest.signUpUser(user);
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(user.getUsername());
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }
}
