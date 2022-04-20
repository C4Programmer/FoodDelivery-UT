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

@Service
@Transactional
@Qualifier("UserDetailsService")
public class UserService implements IUserService, UserDetailsService {

    private final IUserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(IUserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("No user found by username " + username);
        }else {
            userRepository.save(user);
            return new AuthenticationUser(user);
        }
    }

    @Override
    public User register(User user) throws UserUsernameExist, UserEmailExist {
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

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByUsername(String username) throws UserNullObject {
        var result = userRepository.findUserByUsername(username);
        if(result == null){
            throw new UserNullObject("User does not exist!");
        }
        return result;
    }


    private String encodePassword(String password) {
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
