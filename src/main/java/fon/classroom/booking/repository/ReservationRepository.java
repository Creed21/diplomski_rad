package fon.classroom.booking.repository;

import fon.classroom.booking.model.ClassRoom;
import fon.classroom.booking.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
//    @Query(value = "select * from classroom_booking.reservation where classroom = :classRoomID and beginning_approved >= ?2 and end_approved <= ?3"
    @Query(value = "select * from classroom_booking.reservation where classroom = ?1 and beginning_approved >= ?2 and end_approved <= ?3"
            , nativeQuery = true)
    List<Reservation> findByClassRoomAndBeginningApprovedAfterAndEndApprovedBefore(Integer classRoomID, Date dateFrom, Date dateTo);
    List<Reservation> findByReservationForUserAndBeginningApprovedAfterAndEndApprovedBefore(String reservationForUser, Date dateFrom, Date dateTo);
    List<Reservation> findByClassRoomAndBeginningApprovedAfterAndEndApprovedBefore(ClassRoom classRoom, Date beginningApproved, Date endApproved);

    @Query(value = "select * from classroom_booking.reservation where classroom = ?1"
            , nativeQuery = true)
    List<Reservation> findByClassRoomID(Integer classRoomID);

    @Query(value = "select * from classroom_booking.reservation where classroom = ?1 and beginning_approved is not null"
            , nativeQuery = true)
    List<Reservation> findByClassRoomIDApproved(Integer classRoomID);

    @Query(value = "select * from classroom_booking.reservation where reservation_for_user = ?1 and beginning_approved is not null"
            , nativeQuery = true)
    List<Reservation> findByUserApproved(String reservationForUser);
}
