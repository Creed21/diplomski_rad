package fon.classroom.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Entity
@Table(name = "classroom_type")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
public class ClassRoomType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "classroom_type_sequ", strategy = "native")
    private Integer id;
    @NotEmpty(message = "ClassRoom Type description must not be blank")
    private String dsc;

//    @OneToMany(fetch = FetchType.LAZY, targetEntity = ClassRoomType.class)
//    @JoinColumn(name = "id", referencedColumnName = "class_room_type", nullable = true)
//    private List<ClassRoom> classRooms;
}
