package FDBackend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "deliveryZones")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryZone implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    @Column
    private String name;
    @Column
    private String location;

    @ManyToOne
    @JoinColumn(name = "restaurantId")
    @JsonIgnore
    private Restaurant restaurant;
}
