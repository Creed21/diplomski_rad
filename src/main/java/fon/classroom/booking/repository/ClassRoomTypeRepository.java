package fon.classroom.booking.repository;

import fon.classroom.booking.model.ClassRoom;
import fon.classroom.booking.model.ClassRoomType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRoomTypeRepository extends CrudRepository<ClassRoomType, Integer> {
    List<ClassRoomType> findAll();
}
