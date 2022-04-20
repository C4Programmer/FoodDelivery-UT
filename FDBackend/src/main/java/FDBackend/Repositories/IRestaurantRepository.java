package FDBackend.Repositories;

import FDBackend.Entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRestaurantRepository extends JpaRepository<Restaurant,Long> {
    Restaurant findRestaurantByName(String name);
    List<Restaurant> findAll();
    Restaurant findRestaurantByAdministrator_Id(Long id);
}
