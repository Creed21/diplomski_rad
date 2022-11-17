package fon.classroom.booking.repository;

import fon.classroom.booking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*
@Repository stereotype annotation is used to add a bean of this class
type to the Spring context and indicate that given Bean is used to perform
DB related operations and
* */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User readByUserNameAndPassword(String userName, String password);
//    User findByUserName(String userName);
    Optional<User> findByUserName(String userName);
    User findByEmail(String email);
    User readByUserName(String userName);

    @Query(value = "select * from classroom_booking.user_app where role_id = 1", nativeQuery = true)
    List<User> findUsersByRole(String admin);
}
