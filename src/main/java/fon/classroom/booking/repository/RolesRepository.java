package fon.classroom.booking.repository;

import fon.classroom.booking.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Role, Integer> {

    Role getByDsc(String roleDsc);

}
