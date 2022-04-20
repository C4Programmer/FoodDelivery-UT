package FDBackend.Services;

import FDBackend.Entities.User;
import FDBackend.Exceptions.UserExceptions.UserEmailExist;
import FDBackend.Exceptions.UserExceptions.UserNullObject;
import FDBackend.Exceptions.UserExceptions.UserUsernameExist;

import java.util.List;

public interface IUserService {
    User register(User user) throws UserUsernameExist, UserEmailExist;
    List<User> getUsers();
    User findUserByUsername(String username) throws UserNullObject;
}
