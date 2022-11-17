package fon.classroom.booking.repository;

import fon.classroom.booking.model.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoom, Integer> {
    Optional<ClassRoom> findById(Integer id);
    List<ClassRoom> findAll();

}
