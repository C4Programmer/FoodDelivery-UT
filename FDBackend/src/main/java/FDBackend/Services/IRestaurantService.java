package FDBackend.Services;

import FDBackend.Entities.Restaurant;
import FDBackend.Exceptions.RestaurantExceptions.RestaurantAdminAlreadyAssigned;
import FDBackend.Exceptions.RestaurantExceptions.RestaurantNameExist;

import java.util.List;

public interface IRestaurantService {
    Restaurant save(Restaurant restaurant) throws RestaurantNameExist, RestaurantAdminAlreadyAssigned;
    List<Restaurant> findAll();
    Restaurant findByAdministratorId(Long id);
}
