package jwp.service;

import jwp.dao.UserDao;
import jwp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    @Transactional
    public void createUser(User user) {
        userDao.insert(user);
    }

    @Transactional
    public void updateUser(User user) {
        userDao.update(user);
    }

    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    public User findUserById(String userId) {
        return userDao.findByUserId(userId);
    }

    public User login(String userId, String password) {
        User user = userDao.findByUserId(userId);
        if (user != null && user.isSameUser(userId, password)) {
            return user;
        }
        return null;
    }
}
