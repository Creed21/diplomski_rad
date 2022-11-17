package fon.classroom.booking.services;

import fon.classroom.booking.model.User;
import fon.classroom.booking.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User saveUser(User user, String createdBy){
        if(user == null)
            throw new IllegalArgumentException("Invalid input");

        if( !user.getPassword().equals(user.getConfirmPassword()) )
            throw new IllegalArgumentException("Passwords do not match!");

        if(userRepository.findByUserName(user.getUserName()) != null
            || userRepository.findByEmail(user.getEmail()) != null)
            throw new IllegalArgumentException("User already exists!");

//        if(userRepository.findByEmail(user.getUserName()) != null)
//            throw new IllegalArgumentException("Email je zauzet!");

        user.setUsc(createdBy);
        user.setDtc(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User newUser = userRepository.saveAndFlush(user);
        if(newUser != null && !newUser.getUserName().isEmpty())
            return newUser;
        throw new IllegalArgumentException("Korisnik nije sacuvan!");
    }

    @Transactional
    public User updateUser(User user, String modifiedBy){
        if(user == null)
            throw new IllegalArgumentException("Invalid input");

        if( !user.getPassword().equals(user.getConfirmPassword()) )
            throw new IllegalArgumentException("Passwords do not match!");

        User check_user = userRepository.findByUserName(user.getUserName()).get();
        if( check_user == null)
            throw new IllegalArgumentException("Korisnik ne postoji!");

        user.setUsm(modifiedBy);
        user.setDtm(LocalDateTime.now());
        User newUser = userRepository.save(user);

        if(newUser != null && !newUser.getUserName().isEmpty())
            return newUser;
        throw new IllegalArgumentException("Korisnik nije sacuvan!");
    }

    public User findByUserName(String userName) {
        if(userName == null || userName.isEmpty() )
            throw new IllegalArgumentException("Nema rezultata pretrage!");
        return userRepository.findByUserName(userName).get();
    }
}
