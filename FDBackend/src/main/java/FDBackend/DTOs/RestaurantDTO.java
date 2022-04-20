package FDBackend.DTOs;

import FDBackend.Entities.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {
    private String name;
    private String location;
    private List<Menu> menus;
    private List<DeliveryZoneDTO> deliveryZoneDTOS = new ArrayList<>();
}
