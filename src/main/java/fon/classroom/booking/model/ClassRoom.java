package fon.classroom.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Entity
@Table(name = "classroom")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
public class ClassRoom  extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 2228784815938588107L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "classroom_sequ", strategy = "native")
    private Integer id;

    @NotBlank(message="ClassRoom name must not be blank")
    private String dsc;

//    @NotBlank(message="People capacity number must not be blank")
    private Long peopleCapacity;

//    @NotBlank(message="Computer capacity number must not be blank")
    private Long computerCapacity;

    @ManyToOne(fetch = FetchType.LAZY, optional = true) //, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "class_room_type", referencedColumnName = "id", nullable = false)
//    @Column(columnDefinition = "numeric")
//    @NotBlank(message="ClassRoom Type must not be blank")
    private ClassRoomType classRoomType;

}
