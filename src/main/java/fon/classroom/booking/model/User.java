package fon.classroom.booking.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "user_app")
public class User extends BaseEntity {

    @Id
    @NotBlank(message="Username must not be blank")
//    @Column(name = "user_name")
    private String userName;
    @NotBlank(message="Password must not be blank")
    private String password;
    private transient String confirmPassword;
    private String active;

    @NotBlank(message="Firstname must not be blank")
    private String firstName;
    @NotBlank(message="Lastname must not be blank")
    private String lastName;
    @NotBlank(message="Email must not be blank")
    @Email(message="Enter a valid email address")
//    @Column(unique = true)
    private String email;
    @NotBlank(message="Telephone number must not be blank")
//    @Pattern(regexp="(^$|[0-9]{9,10})",message = "Telephone number must be between 9 and 10 digits")
    private String telephone;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, targetEntity = Role.class)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = true)
    private Role role;
//    private String role;
}
