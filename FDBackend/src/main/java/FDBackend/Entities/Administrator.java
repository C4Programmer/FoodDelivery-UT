package FDBackend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "administrators")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Administrator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    @OneToOne(mappedBy = "administrator")
    @JsonIgnore
    private Restaurant restaurant;
}
