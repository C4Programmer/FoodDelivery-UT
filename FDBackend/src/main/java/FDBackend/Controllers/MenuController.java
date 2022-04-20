package FDBackend.Controllers;

import FDBackend.Entities.Menu;
import FDBackend.Exceptions.ExceptionHandling;
import FDBackend.Exceptions.MenuExceptions.MenuRestaurantNotExist;
import FDBackend.Services.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/menus")
public class MenuController extends ExceptionHandling {

    public final IMenuService menuService;

    @Autowired
    public MenuController(IMenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<List<Menu>> findMenuByRestaurantId(@PathVariable Long id) throws MenuRestaurantNotExist {
        var result = menuService.findAllByRestaurantId(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @GetMapping(value = "/{id}/{categoryName}")
    public ResponseEntity<List<Menu>> findMenuByRestaurantId(@PathVariable Long id, @PathVariable String categoryName) throws MenuRestaurantNotExist {
        var result = menuService.findAllByRestaurantIdAndCategoryName(id,categoryName);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Menu>> findAll() throws MenuRestaurantNotExist {
        var result = menuService.findAll();
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
