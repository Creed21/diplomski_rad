package fon.classroom.booking.services;

import fon.classroom.booking.model.ClassRoomType;
import fon.classroom.booking.repository.ClassRoomTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ClassRoomTypeService {
    @Autowired
    private ClassRoomTypeRepository classRoomTypeRepository;

    public Optional<ClassRoomType> findById(ClassRoomType classRoomType) {
        if(classRoomType == null || classRoomType.getId() == null)
            return null;
        return classRoomTypeRepository.findById(classRoomType.getId().intValue());
    }

    public boolean saveClassRoomType(String dsc, String userCreated) {
        boolean isSaved = false;
        ClassRoomType classRoomType = new ClassRoomType();
        classRoomType.setDsc(dsc);
        classRoomType.setUsc(userCreated);
        classRoomType.setDtc(LocalDateTime.now());
        ClassRoomType saved_clrt = classRoomTypeRepository.save(classRoomType);
        if(saved_clrt != null && !saved_clrt.getUsc().isEmpty())
            isSaved = true;
        return isSaved;
    }

    public List<ClassRoomType> findAll() {
        List<ClassRoomType> classRoomTypes = classRoomTypeRepository.findAll();
        if(classRoomTypes != null && !classRoomTypes.isEmpty())
            return classRoomTypes;
        throw new IllegalArgumentException("Nema rezultata pretrage");
    }
}
