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

@Service
@Transactional
public class MenuService implements IMenuService {

    private final IMenuRepository menuRepository;

    @Autowired
    public MenuService(IMenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public List<Menu> findAllByRestaurantId(Long id) throws MenuRestaurantNotExist {
        var result = menuRepository.findAllByRestaurantId(id);
        if(result.size() < 1){
            throw new MenuRestaurantNotExist("The restaurant does not exist!");
        }
        result.sort(Comparator.comparing(o -> o.getCategory().getName()));
        return result;
    }

    @Override
    public List<Menu> findAllByRestaurantIdAndCategoryName(Long id, String categoryName) throws MenuRestaurantNotExist {
        var result = menuRepository.findAllByRestaurantIdAndAndCategoryName(id,categoryName);
        if(result.size() < 1){
            throw new MenuRestaurantNotExist("The restaurant or category does not exist!");
        }
        return result;
    }
    @Override
    public List<Menu> findAll() {
        var result = menuRepository.findAll();
        result.sort(Comparator.comparing(o -> o.getRestaurant().getName()));
        return result;
    }

}
