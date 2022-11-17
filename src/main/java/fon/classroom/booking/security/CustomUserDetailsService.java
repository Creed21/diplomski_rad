package fon.classroom.booking.security;

import fon.classroom.booking.model.Role;
import fon.classroom.booking.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<fon.classroom.booking.model.User> optionalUser = userRepository.findByUserName(userName);
        if(optionalUser.isPresent()) {
            fon.classroom.booking.model.User user = optionalUser.get();

            List<String> roleList = new ArrayList<String>();
            roleList.add(user.getRole() != null ? user.getRole().getDsc() : "USER");

            return User.builder()
                    .username(user.getUserName())
                    //change here to store encoded password in db
                    .password( user.getPassword() )
                    .disabled(false)
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .roles(roleList.toArray(new String[0]))
                    .build();
        } else {
            throw new UsernameNotFoundException("User Name is not Found");
        }
    }
}
