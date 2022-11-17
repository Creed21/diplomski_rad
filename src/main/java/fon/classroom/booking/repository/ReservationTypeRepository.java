package fon.classroom.booking.repository;

import fon.classroom.booking.model.ReservationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationTypeRepository extends JpaRepository<ReservationType, Long> {
}
