package jwp.dao;

import core.jdbc.ConnectionProvider;
import core.jdbc.DataAccessException;
import core.jdbc.TestConnectionManager;
import jwp.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    private UserDao userDao;
    private TestConnectionManager manager;

    @BeforeEach
    void setUp() throws SQLException {
        manager = new TestConnectionManager();
        initDatabase();
        userDao = new UserDao(manager);
    }

    void initDatabase() throws SQLException {
        try (Connection conn = manager.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS USERS");
            stmt.execute("CREATE TABLE USERS (" +
                    "userId VARCHAR(12) NOT NULL, " +
                    "password VARCHAR(12) NOT NULL, " +
                    "name VARCHAR(20) NOT NULL, " +
                    "email VARCHAR(50), " +
                    "PRIMARY KEY (userId))");
        }
    }

    @AfterEach
    void tearDown() {
        cleanUpDatabase();
    }

    private void cleanUpDatabase() {
        List<User> users = userDao.findAll();
        for (User user : users) {
            userDao.delete(user);
        }
    }

    @Test
    void insert() {
        // given
        User user = new User("testId", "password123", "Test User", "test@example.com");

        // when
        userDao.insert(user);

        // then
        User foundUser = userDao.findUserById("testId");
        assertNotNull(foundUser);
        assertEquals("testId", foundUser.getUserId());
        assertEquals("password123", foundUser.getPassword());
        assertEquals("Test User", foundUser.getName());
        assertEquals("test@example.com", foundUser.getEmail());
    }

    @Test
    void update() {
        // given
        User user = new User("testId", "password123", "Test User", "test@example.com");
        userDao.insert(user);

        // when
        User updateUser = new User("testId", "newPassword", "Updated User", "updated@example.com");
        userDao.update(updateUser);

        // then
        User foundUser = userDao.findUserById("testId");
        assertEquals("newPassword", foundUser.getPassword());
        assertEquals("Updated User", foundUser.getName());
        assertEquals("updated@example.com", foundUser.getEmail());
    }

    @Test
    void delete() {
        // given
        User user = new User("testId", "password123", "Test User", "test@example.com");
        userDao.insert(user);

        // when
        userDao.delete(user);

        // then
        assertNull(userDao.findUserById("testId"));
    }

    @Test
    void findAll() {
        // given
        User user1 = new User("testId1", "password1", "User 1", "user1@example.com");
        User user2 = new User("testId2", "password2", "User 2", "user2@example.com");
        User user3 = new User("testId3", "password3", "User 3", "user3@example.com");

        userDao.insert(user1);
        userDao.insert(user2);
        userDao.insert(user3);

        // when
        List<User> users = userDao.findAll();

        // then
        assertEquals(3, users.size());
    }

    @Test
    void findUserById() {
        // given
        User user = new User("testId", "password123", "Test User", "test@example.com");
        userDao.insert(user);

        // when
        User foundUser = userDao.findUserById("testId");

        // then
        assertNotNull(foundUser);
        assertEquals("testId", foundUser.getUserId());
        assertEquals("password123", foundUser.getPassword());
        assertEquals("Test User", foundUser.getName());
        assertEquals("test@example.com", foundUser.getEmail());
    }

    @Test
    void insertDuplicateUserId() {
        // given
        User user1 = new User("testId", "password123", "Test User", "test@example.com");
        userDao.insert(user1);

        // when & then
        User user2 = new User("testId", "password456", "Another User", "another@example.com");
        assertThrows(DataAccessException.class, () -> userDao.insert(user2));
    }

    @Test
    void findUserByIdNotFound() {
        // given
        String nonExistentUserId = "nonExistentUser";

        // when & then
        assertNull(userDao.findUserById(nonExistentUserId));
    }
}