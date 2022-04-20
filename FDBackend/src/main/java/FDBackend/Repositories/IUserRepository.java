package FDBackend.Repositories;

import FDBackend.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User,Long> {
    User findUserByUsername(String username);
    User findUserByEmail(String email);
}
