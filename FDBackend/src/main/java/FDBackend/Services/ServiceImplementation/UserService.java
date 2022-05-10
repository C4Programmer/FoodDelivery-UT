package FDBackend.Services.ServiceImplementation;

import FDBackend.Entities.Administrator;
import FDBackend.Entities.Customer;
import FDBackend.Entities.User;
import FDBackend.Exceptions.UserExceptions.UserEmailExist;
import FDBackend.Exceptions.UserExceptions.UserNullObject;
import FDBackend.Exceptions.UserExceptions.UserUsernameExist;
import FDBackend.Repositories.IUserRepository;
import FDBackend.Security.AuthenticationUser;
import FDBackend.Services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
@Transactional
@Qualifier("UserDetailsService")
/**
 * This is the service class for the use
 */
public class UserService implements IUserService, UserDetailsService {
    /**
     * This is the name of the repository
     */
    private final IUserRepository userRepository;
    /**
     * This is the name of the encoder
     */
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    public UserService(IUserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * This method loads the username
     * @param username to load the specific user
     * @return an object UserDetails for authentication
     * @throws UsernameNotFoundException in case the username does not exist
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("Load user by username-service");
        User user = userRepository.findUserByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("No user found by username " + username);
        }else {
            userRepository.save(user);
            return new AuthenticationUser(user);
        }
    }

    /**
     *
     * @param user the user to be saved
     * @return the confirmation of registration
     * @throws UserUsernameExist in case the username was already taken
     * @throws UserEmailExist in case the email was already taken
     */
    @Override
    public User register(User user) throws UserUsernameExist, UserEmailExist {
        LOGGER.info("Register user-service");
        validateNewUsernameAndEmail(user.getUsername(),user.getEmail());
        user.setPassword(encodePassword(user.getPassword()));
        System.out.println("Here");
        if(user.getUserType().getId() == 1){
            System.out.println("Here");
            user.setAdministrator(new Administrator());
            user.getAdministrator().setUser(user);
        }else{
            user.setCustomer(new Customer());
            user.getCustomer().setUser(user);
        }
        return userRepository.save(user);
    }

    /**
     * This way we get all the users
     * @return a list of all the users
     */
    @Override
    public List<User> getUsers() {
        LOGGER.info("Get all user-service");
        return userRepository.findAll();
    }

    /**
     *
     * @param username to find a user by a username
     * @return an object of type User
     * @throws UserNullObject in case it does not exist
     */
    @Override
    public User findUserByUsername(String username) throws UserNullObject {
        LOGGER.info("Find by username of user-service");
        var result = userRepository.findUserByUsername(username);
        if(result == null){
            throw new UserNullObject("User does not exist!");
        }
        return result;
    }

    /**
     *
     * @param password the password to be encoded
     * @return the encoded password
     */
    private String encodePassword(String password) {
        LOGGER.info("Encode password-service");
        return bCryptPasswordEncoder.encode(password);
    }

    private void validateNewUsernameAndEmail(String username, String email) throws UserUsernameExist, UserEmailExist {
        User userByUsername = userRepository.findUserByUsername(username);
        if(userByUsername != null){
            throw new UserUsernameExist("Username already exist!");
        }
        User userByEmail = userRepository.findUserByEmail(email);
        if(userByEmail != null){
            throw new UserEmailExist("Email already exist!");
        }
    }
}
