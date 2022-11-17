package fon.classroom.booking.services;

import fon.classroom.booking.model.ClassRoom;
import fon.classroom.booking.repository.ClassRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ClassRoomService {

    @Autowired
    private ClassRoomRepository classRoomRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public ClassRoom saveClassRoom(ClassRoom classRoom, String usc) {
        classRoom.setUsc(usc);
        classRoom.setDtc(LocalDateTime.now());
        if(classRoom != null && (classRoom.getPeopleCapacity() <= 0 || classRoom.getComputerCapacity() < 0))
            throw new IllegalArgumentException("Proverite podatke pre cuvanja!");
        ClassRoom savedClassRoom = classRoomRepository.save(classRoom);
        if(savedClassRoom == null)
            throw new IllegalArgumentException("Ucionica nije sacuvana");
        return savedClassRoom;
    }

    public Optional<ClassRoom> findById(Integer id) {
        Optional<ClassRoom> classRoom = classRoomRepository.findById(id);
        if(classRoom == null)
            throw new IllegalArgumentException("Nema rezultata pretrage");
        return classRoom;
    }

    public List<ClassRoom> findAll() {
        List<ClassRoom> classRooms = classRoomRepository.findAll(Sort.by("id").ascending());
        if(classRooms == null)
            throw new IllegalArgumentException("Nema rezultata pretrage");
        return classRooms;
    }

    public List<ClassRoom> findAll(ClassRoom classRoom) {
        if(classRoom == null)
            throw new IllegalArgumentException("Invalid input");
        Session session = entityManager.unwrap(Session.class);
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<ClassRoom> cr = cb.createQuery(ClassRoom.class);
        Root<ClassRoom> root = cr.from(ClassRoom.class);
        cr.select(root);
//        Criteria criteria = session.createCriteria(ClassRoom.class);
//        criteria.createCriteria()
//        cr.orderBy()

        Query<ClassRoom> query = session.createQuery(cr);
        List<ClassRoom> classRooms = query.getResultList();
        if(classRooms == null)
            throw new IllegalArgumentException("Nema rezultata pretrage");
        return classRooms;
    }
}
