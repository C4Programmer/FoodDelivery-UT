package FDBackend.Services.ServiceImplementation;

import FDBackend.Entities.Menu;
import FDBackend.Exceptions.MenuExceptions.MenuRestaurantNotExist;
import FDBackend.Repositories.IMenuRepository;
import FDBackend.Services.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//
@Service
@Transactional
/**
 * This is the menu service class
 * @author Denis Somfalean
 */
public class MenuService implements IMenuService {

    /**
     * This name of the repository
     */
    private final IMenuRepository menuRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Autowired
    public MenuService(IMenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    /**
     * This method is used to get the menu of a restaurant
     * @author Denis Somfalean
     */
    public List<Menu> findAllByRestaurantId(Long id) throws MenuRestaurantNotExist {
        LOGGER.info("Get menus of restaurant-service");
        var result = menuRepository.findAllByRestaurantId(id);
        if(result.size() < 1){
            throw new MenuRestaurantNotExist("The restaurant does not exist!");
        }
        result.sort(Comparator.comparing(o -> o.getCategory().getName()));
        return result;
    }

    @Override
    /**
     * This method is used to get the menu of a restaurant by a specific category
     * @author Denis Somfalean
     */
    public List<Menu> findAllByRestaurantIdAndCategoryName(Long id, String categoryName) throws MenuRestaurantNotExist {
        LOGGER.info("Get menus by category of restaurant-service");
        var result = menuRepository.findAllByRestaurantIdAndAndCategoryName(id,categoryName);
        if(result.size() < 1){
            throw new MenuRestaurantNotExist("The restaurant or category does not exist!");
        }
        return result;
    }
    @Override
    /**
     * This method is used to get all the menus
     * @author Denis Somfalean
     */
    public List<Menu> findAll() {
        LOGGER.info("Get all menus of restaurant-service");
        var result = menuRepository.findAll();
        result.sort(Comparator.comparing(o -> o.getRestaurant().getName()));
        return result;
    }

}
