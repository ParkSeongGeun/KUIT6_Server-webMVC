package core.mvc;

import core.jdbc.ConnectionManager;
import core.jdbc.ConnectionProvider;
import jwp.controller.*;
import jwp.dao.QuestionDao;
import jwp.dao.UserDao;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {

    private static final Map<String, Controller> controllers = new HashMap<>();

    static {
        ConnectionProvider connectionProvider = new ConnectionManager();
        UserDao userDao = new UserDao(connectionProvider);
        QuestionDao questionDao = new QuestionDao(connectionProvider);

        controllers.put("/", new HomeController(questionDao));
        controllers.put("/user/signup", new CreateUserController(userDao));
        controllers.put("/user/list", new ListUserController());
        controllers.put("/user/login", new LoginController(userDao));
        controllers.put("/user/logout", new LogoutController());
        controllers.put("/user/update", new UpdateUserController());
        controllers.put("/user/updateForm", new UpdateUserFormController());

        controllers.put("/user/form", new ForwardController("/user/form.jsp"));
        controllers.put("/user/loginForm", new ForwardController("/user/login.jsp"));
        controllers.put("/user/loginFailed", new ForwardController("/user/loginFailed.jsp"));

        controllers.put("/qna/form", new CreateQuestionFormController());
        controllers.put("/qna/create", new CreateQuestionController(questionDao));
        controllers.put("/qna/show", new ShowController(questionDao));

    }

    public Controller getController(String url) {
        return controllers.get(url);
    }
}
