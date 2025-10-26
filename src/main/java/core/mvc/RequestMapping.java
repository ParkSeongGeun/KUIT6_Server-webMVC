package core.mvc;

import core.jdbc.ConnectionManager;
import core.jdbc.ConnectionProvider;
import jwp.controller.*;
import jwp.dao.UserDao;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {

    private static final Map<String, Controller> controllers = new HashMap<>();

    static {
        ConnectionProvider connectionProvider = new ConnectionManager();
        UserDao userDao = new UserDao(connectionProvider);

        controllers.put("/", new HomeController());
        controllers.put("/user/signup", new CreateUserController(userDao));
        controllers.put("/user/list", new ListUserController());
        controllers.put("/user/login", new LoginController());
        controllers.put("/user/logout", new LogoutController());
        controllers.put("/user/update", new UpdateUserController());
        controllers.put("/user/updateForm", new UpdateUserFormController());

        controllers.put("/user/form", new ForwardController("/user/form.jsp"));
        controllers.put("/user/loginForm", new ForwardController("/user/login.jsp"));
        controllers.put("/user/loginFailed", new ForwardController("/user/loginFailed.jsp"));

        controllers.put("/qna/form", new ForwardController("/qna/form.jsp"));
        controllers.put("/qna/show", new ForwardController("/qna/show.jsp"));

    }

    public Controller getController(String url) {
        return controllers.get(url);
    }
}
