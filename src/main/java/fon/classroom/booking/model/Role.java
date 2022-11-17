package fon.classroom.booking.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "roles_sequ", strategy = "native")
    private Integer id;
    @NotEmpty(message = "Role description must not be blank")
    private String dsc;
}
