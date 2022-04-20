package FDBackend.Controllers;

import FDBackend.Entities.Menu;
import FDBackend.Entities.Restaurant;
import FDBackend.Exceptions.ExceptionHandling;
import FDBackend.Exceptions.RestaurantExceptions.*;
import FDBackend.Services.IRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurants")
public class RestaurantController extends ExceptionHandling {

    private final IRestaurantService restaurantService;

    @Autowired
    public RestaurantController(IRestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> findAll() throws RestaurantEmptyDatabase {
        var result = restaurantService.findAll();
        if(result.size() < 1){
            throw new RestaurantEmptyDatabase("No Restaurants have been saved into the database!");
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Restaurant> findByAdministrator(@PathVariable Long id) throws RestaurantEmptyDatabase {
        var result = restaurantService.findByAdministratorId(id);
        if(result == null){
            throw new RestaurantEmptyDatabase("No Restaurants have been saved into the database!");
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Restaurant> saveRestaurant(@RequestBody Restaurant restaurant) throws RestaurantAdministratorNullObject, RestaurantDeliveryZoneNullObject, RestaurantLocationNullObject, RestaurantNameNullObject, RestaurantNameExist, RestaurantAdminAlreadyAssigned, RestaurantMenuNullObject {
        if(restaurant.getAdministrator() == null){
            throw new RestaurantAdministratorNullObject("Restaurant's administrator data must be received!");
        }
        if(restaurant.getLocation() == null){
            throw new RestaurantLocationNullObject("Restaurant's location must be received!");
        }
        if(restaurant.getName() == null){
            throw new RestaurantNameNullObject("Restaurant's name must be received!");
        }
        if(restaurant.getDeliveryZones() == null){
            throw new RestaurantDeliveryZoneNullObject("Restaurant's available delivery zones must be received!");
        }
        if(restaurant.getDeliveryZones().size() < 1){
            throw new RestaurantDeliveryZoneNullObject("Restaurant's available delivery zones must be received!");
        }
        if(restaurant.getMenus() == null){
            throw new RestaurantMenuNullObject("Restaurant's Menu must be received!");
        }
        if(restaurant.getMenus().size() < 1){
            throw new RestaurantMenuNullObject("Restaurant's Menu must be received!");
        }
        Restaurant result = restaurantService.save(restaurant);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
