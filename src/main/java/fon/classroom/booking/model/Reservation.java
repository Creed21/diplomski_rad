package fon.classroom.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "reservation")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
public class Reservation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "reservation_sequ", strategy = "native")
    private Integer id;

    private String dsc;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "classroom", referencedColumnName = "id", nullable = false)
    private ClassRoom classRoom;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "reservation_type", referencedColumnName = "id")
    private ReservationType reservationType;

    @Column(name = "beginning_asked")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date beginningAsked;

    @Column(name = "end_asked")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date endAsked;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation_for_user", referencedColumnName = "userName", nullable = false)
    private User reservationForUser;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation_status", referencedColumnName = "id")
    private ReservationStatus reservationStatus;

    @Column(name = "beginning_approved")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date beginningApproved;

    @Column(name = "end_approved")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date endApproved;
}
