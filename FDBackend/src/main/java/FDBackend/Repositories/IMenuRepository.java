package FDBackend.Repositories;

import FDBackend.Entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMenuRepository extends JpaRepository<Menu,Long> {
    List<Menu> findAllByRestaurantId(Long id);
    List<Menu> findAllByRestaurantIdAndAndCategoryName(Long id, String categoryName);
}
