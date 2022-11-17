package fon.classroom.booking.repository;

import fon.classroom.booking.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationStatusRepository extends JpaRepository<ReservationStatus, Integer> {
    @Override
    Optional<ReservationStatus> findById(Integer integer);
    Optional<ReservationStatus> findByDscIgnoreCase(String dsc);

}
