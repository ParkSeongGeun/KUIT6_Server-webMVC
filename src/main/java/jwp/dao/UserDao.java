package jwp.dao;

import jwp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserDao {

    private final EntityManager em;

    /**
     * insert, update를 한번에
     * @param user
     */
    public void insert(User user) {
        em.persist(user);
    }

    public void update(User user) {
        em.merge(user);
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    public User findByUserId(String userId) {
        return em.find(User.class, userId);
    }
}
