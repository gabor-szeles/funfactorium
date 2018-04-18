package org.funfactorium.user;

import org.funfactorium.security.email.EmailValidator;
import org.funfactorium.security.password.PasswordMatchesValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService{

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserName(username);

        if (user == null) {
            String message = "Username not found: " + username;
            log.info(message);
            throw new UsernameNotFoundException(message);
        }

        List<GrantedAuthority> authorities = user.getRole().getAuthorities()
                                                .stream().map(SimpleGrantedAuthority::new)
                                                    .collect(Collectors.toList());


        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                true,
                true, //Account Not Expired
                true, //Credentials Not Expired
                true,//Account Not Locked
                authorities
        ) {
        };

    }

    public void register(UserRegistrationDto registeringUser){
        System.out.println("running register method");
        String username = registeringUser.getUserName();
        String password = passwordEncoder.encode(registeringUser.getPassword());
        String email = registeringUser.getEmail();
        User user = new User(username, password, email);
        user.setRole(UserRole.USER);
        userRepository.save(user);
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public boolean userExistsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    public boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}


