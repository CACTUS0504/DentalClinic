package org.example.service;

import org.example.model.Role;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createEntity(User user) {
        userRepository.save(user);
    }

    public List<User> readAllEntity() {
        return userRepository.findAll();
    }

    public User readOneEntity(long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) return userOptional.get();
        else throw new IllegalArgumentException("User with id = " + id + " doesn't exist");
    }

    public boolean updateEntity(User user, long id) {
        user.setId(id);
        userRepository.save(user);
        return true;
    }

    public boolean deleteEntity(long id) {
        if (userRepository.findById(id).isPresent()){
            userRepository.deleteById(id);
            return true;
        } else return false;
    }

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User applicationUser = userRepository.findByUsername(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(String.format("Username: %s not found", username));
        } else if (applicationUser.getIsBanned()) {
            throw new UsernameNotFoundException(String.format("Username: %s is banned", username));
        }
        return new org.springframework.security.core.userdetails.User(applicationUser.getUsername(), applicationUser.getPassword(),
                mapRolesToAuthorities(applicationUser.getRoles()));
    }

    // Приводим коллекцию ролей к коллекции GrantedAuthorities
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return  roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    public String signUpUser(User user) {
        boolean userExists = userRepository.findByUsername(user.getUsername()) != null;
        if (userExists) {
            throw new IllegalStateException(String.format("User with username %s exists", user.getUsername()));
        }

        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);

        return "/api/authorization/login";
    }

    public User getCurrentUser(){
        User user = null;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(SecurityContextHolder.getContext());
            System.out.println(auth);
            user = userRepository.findByUsername(auth.getName());
            //user = userRepository.findByUsername("patient1");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return user;
    }
}
