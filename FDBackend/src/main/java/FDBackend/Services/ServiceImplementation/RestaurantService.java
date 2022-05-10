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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the restaurant service
 * @author Denis Somfalean
 */
@Service
@Transactional
public class RestaurantService implements IRestaurantService {

    /**
     * This is the name of the repository
     */
    private final IRestaurantRepository restaurantRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Autowired
    public RestaurantService(IRestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    /**
     * This way we can save the restaurant data
     * @param restaurant the data of the restaurant to be saved
     * @return the confirmation of saving
     * @throws RestaurantNameExist in case the name already exists
     * @throws RestaurantAdminAlreadyAssigned in case the restaurant has another admin
     */
    @Override
    public Restaurant save(Restaurant restaurant) throws RestaurantNameExist, RestaurantAdminAlreadyAssigned {
        LOGGER.info("Save restaurant-service");
        validateRestaurant(restaurant);
        restaurant.getDeliveryZones().forEach(l -> l.setRestaurant(restaurant));
        restaurant.getMenus().forEach(l -> l.setRestaurant(restaurant));
        return restaurantRepository.save(restaurant);
    }

    /**
     * This way we get all restaurants
     * @return a list of restaurants
     */
    @Override
    public List<Restaurant> findAll() {
        LOGGER.info("get all restaurants-service");
        return restaurantRepository.findAll();
    }

    /**
     * tHis method returns a specific administrator
     * @param id of the administrator
     * @return an object administrator
     */
    @Override
    public Restaurant findByAdministratorId(Long id) {
        LOGGER.info("get administrator by id-service");
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
