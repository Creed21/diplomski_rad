package fon.classroom.booking.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
//@Entity
//@Table(name = "reservation_status_history")
public class ReservationStatusHistory {
    @Column(name = "reservation_id")
    private Reservation reservationId;
    @Column(name = "reservation_status")
    private ReservationStatus reservationStatus;
    @Column(name = "status_changed_by")
    private User statusChangedBy;
    @Column(name = "dt_change")
    private Timestamp dtChange;
}
