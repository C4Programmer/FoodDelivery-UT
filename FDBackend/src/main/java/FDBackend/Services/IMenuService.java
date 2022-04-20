package FDBackend.Services;

import FDBackend.Entities.Menu;
import FDBackend.Exceptions.MenuExceptions.MenuRestaurantNotExist;

import java.util.List;

public interface IMenuService {
    List<Menu> findAllByRestaurantId(Long id) throws MenuRestaurantNotExist;
    List<Menu> findAllByRestaurantIdAndCategoryName(Long id, String categoryName) throws MenuRestaurantNotExist;
    List<Menu> findAll();
}
