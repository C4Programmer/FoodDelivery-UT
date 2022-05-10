package FDBackend;

import FDBackend.Entities.*;
import FDBackend.Exceptions.MenuExceptions.MenuRestaurantNotExist;
import FDBackend.Exceptions.RestaurantExceptions.RestaurantAdminAlreadyAssigned;
import FDBackend.Exceptions.RestaurantExceptions.RestaurantNameExist;
import FDBackend.Exceptions.UserExceptions.UserEmailExist;
import FDBackend.Exceptions.UserExceptions.UserNullObject;
import FDBackend.Exceptions.UserExceptions.UserUsernameExist;
import FDBackend.Repositories.IMenuRepository;
import FDBackend.Repositories.IRestaurantRepository;
import FDBackend.Repositories.IUserRepository;
import FDBackend.Services.IMenuService;
import FDBackend.Services.IRestaurantService;
import FDBackend.Services.IUserService;
import FDBackend.Services.ServiceImplementation.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@SpringBootTest
class FdBackendApplicationTests {

	@Autowired
	private IRestaurantService restaurantService;

	@Autowired
	private IMenuService menuService;

	@Autowired
	private IUserService userService;

	@MockBean
	private IUserRepository userRepository;

	@MockBean
	private IRestaurantRepository restaurantRepository;

	@MockBean
	private IMenuRepository menuRepository;

	@Test
	public void userRegisterTest() throws UserUsernameExist, UserEmailExist {
		User user = new User();
		user.setUsername("a");
		user.setPassword("a");
		user.setFirstName("a");
		user.setLastName("a");
		user.setEmail("a");
		user.setPhone("a");
		UserType userType = new UserType();
		userType.setId(1L);
		user.setUserType(userType);
		when(userRepository.save(user)).thenReturn(user);
		assert user.equals(userService.register(user));
	}

	@Test
	public void userFindUserByUsernameTest() throws UserNullObject {
		String username = "teddy";
		User user = new User();
		user.setUsername("teddy");
		when(userRepository.findUserByUsername(username)).thenReturn(user);
		assert userService.findUserByUsername(username).getUsername().equals(username);
	}

	@Test
	public void getUsersTest(){
		when(userRepository.findAll()).thenReturn(Stream.of(new User(),new User()).collect(Collectors.toList()));
		assert userService.getUsers().size() == 2;
	}

	@Test
	public void getRestaurantsTest(){
		when(restaurantRepository.findAll()).thenReturn(Stream.of(new Restaurant(),new Restaurant()).collect(Collectors.toList()));
		assert restaurantService.findAll().size() == 2;
	}

	@Test
	public void saveRestaurantTest() throws RestaurantNameExist, RestaurantAdminAlreadyAssigned {
		Restaurant restaurant = new Restaurant();
		restaurant.setName("a");
		restaurant.setLocation("a");
		Menu menu1 = new Menu();
		menu1.setName("m1");
		Menu menu2 = new Menu();
		menu2.setName("m2");
		restaurant.setMenus(new ArrayList<>());
		restaurant.setAdministrator(new Administrator());
		restaurant.getMenus().add(menu1);
		restaurant.getMenus().add(menu2);
		when(restaurantRepository.save(restaurant)).thenReturn(restaurant);
		assert restaurantService.save(restaurant).equals(restaurant);
	}

	@Test
	public void findRestaurantByAdministratorIdTest(){
		Restaurant restaurant = new Restaurant();
		restaurant.setName("a");
		restaurant.setLocation("a");
		Menu menu1 = new Menu();
		menu1.setName("m1");
		Menu menu2 = new Menu();
		menu2.setName("m2");
		restaurant.setMenus(new ArrayList<>());
		Administrator administrator = new Administrator();
		administrator.setId(1L);
		restaurant.setAdministrator(administrator);
		restaurant.getMenus().add(menu1);
		restaurant.getMenus().add(menu2);
		when(restaurantRepository.findRestaurantByAdministrator_Id(1L)).thenReturn(restaurant);
		assert restaurantService.findByAdministratorId(1L).equals(restaurant);
	}

	@Test
	public void findMenusByRestaurantId() throws MenuRestaurantNotExist {
		Restaurant restaurant = new Restaurant();
		restaurant.setId(1L);
		restaurant.setName("a");
		restaurant.setLocation("a");
		Administrator administrator = new Administrator();
		administrator.setId(1L);
		restaurant.setAdministrator(administrator);

		Category category = new Category();
		category.setName("a");

		Menu menu1 = new Menu();
		menu1.setName("m1");
		menu1.setRestaurant(restaurant);
		menu1.setCategory(category);
		Menu menu2 = new Menu();
		menu2.setName("m2");
		menu2.setRestaurant(restaurant);
		menu2.setCategory(category);

		when(menuRepository.findAllByRestaurantId(1L)).thenReturn(Stream.of(menu1,menu2).collect(Collectors.toList()));
		assert menuService.findAllByRestaurantId(1L).size() == 2;
	}
}
