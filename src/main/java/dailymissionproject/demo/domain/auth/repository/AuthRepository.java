package dailymissionproject.demo.domain.auth.repository;

import dailymissionproject.demo.domain.user.repository.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<User, Long> {

    //@Query("select u from User u where u.username = :username")
    User findByUsername(String username);

    Boolean existsByUsername(String username);
}