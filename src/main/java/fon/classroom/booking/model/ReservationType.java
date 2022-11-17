package fon.classroom.booking.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "reservation_type")
public class ReservationType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "reservation_type_sequ", strategy = "native")
    private Long id;
    @NotEmpty(message = "Reservation Type description must not be blank")
    private String dsc;
}
