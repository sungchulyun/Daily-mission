package dailymissionproject.demo.domain.user.repository;

import dailymissionproject.demo.domain.user.dto.request.UserReqDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepository {
    private final EntityManager em;

    public void save(User user){
        em.persist(user);
    }

    public User findOne(Long id){
        return em.find(User.class, id);
    }

    public List<User> findAll(){
        return em.createQuery("select m from User u", User.class)
                .getResultList();
    }

    public List<User> findByMail(String email){
        return em.createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();
    }
}