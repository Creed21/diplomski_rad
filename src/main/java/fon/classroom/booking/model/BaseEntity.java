package fon.classroom.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass // indicates to Spring JPA that this class is used in other classes
public class BaseEntity {

//    @JsonIgnore
    private String usc;
    private LocalDateTime dtc;
    private String usm;
    private LocalDateTime dtm;
}
