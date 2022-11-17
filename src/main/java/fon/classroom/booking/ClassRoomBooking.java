package fon.classroom.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication()
@EnableJpaRepositories("fon.classroom.booking.repository")
@EntityScan("fon.classroom.booking.model")
public class ClassRoomBooking {

    public static void main(String[] args) {
        SpringApplication.run(ClassRoomBooking.class, args);
    }

}
