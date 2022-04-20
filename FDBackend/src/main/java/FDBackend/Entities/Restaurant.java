package FDBackend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private String location;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "administratorId",unique = true)
    private Administrator administrator;

    @OneToMany(mappedBy = "restaurant",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private List<Menu> menus;

    @OneToMany(mappedBy = "restaurant",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private List<DeliveryZone> deliveryZones = new ArrayList<>();
}
