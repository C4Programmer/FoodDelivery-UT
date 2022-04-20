package FDBackend.Services.ServiceImplementation;

import FDBackend.Entities.Restaurant;
import FDBackend.Exceptions.RestaurantExceptions.RestaurantAdminAlreadyAssigned;
import FDBackend.Exceptions.RestaurantExceptions.RestaurantNameExist;
import FDBackend.Repositories.IRestaurantRepository;
import FDBackend.Services.IRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RestaurantService implements IRestaurantService {

    private final IRestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(IRestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Restaurant save(Restaurant restaurant) throws RestaurantNameExist, RestaurantAdminAlreadyAssigned {
        validateRestaurant(restaurant);
        restaurant.getDeliveryZones().forEach(l -> l.setRestaurant(restaurant));
        restaurant.getMenus().forEach(l -> l.setRestaurant(restaurant));
        return restaurantRepository.save(restaurant);
    }

    @Override
    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant findByAdministratorId(Long id) {
        return restaurantRepository.findRestaurantByAdministrator_Id(id);
    }


    private void validateRestaurant(Restaurant restaurant) throws RestaurantNameExist, RestaurantAdminAlreadyAssigned {
        if(restaurantRepository.findRestaurantByName(restaurant.getName()) != null){
            throw new RestaurantNameExist("Restaurant already exist!");
        }
        if(restaurantRepository.findRestaurantByAdministrator_Id(restaurant.getAdministrator().getId()) != null){
            throw new RestaurantAdminAlreadyAssigned("You cannot have the same administrator at different restaurants!");
        }

    }
}
