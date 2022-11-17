package fon.classroom.booking.services;

import fon.classroom.booking.model.ReservationType;
import fon.classroom.booking.repository.ReservationTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ReservationTypeService {

    @Autowired
    ReservationTypeRepository reservationTypeRepository;

    public List<ReservationType> findAll() {
        return reservationTypeRepository.findAll();
    }
}
