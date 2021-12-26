package netology.cloud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
@OptimisticLocking(type = OptimisticLockType.VERSION)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Length(max = 255)
    private String login;

    @NotBlank
    @Length(min = 3, max = 255)
    private String password;

    @Version
    private long version;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
