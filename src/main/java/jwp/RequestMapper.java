package jwp;

import jwp.controller.Controller;
import jwp.controller.HomeController;

import jwp.controller.*;

import java.util.HashMap;
import java.util.Map;

public class RequestMapper {
    private static final RequestMapper instance = new RequestMapper();

    private final Map<String, Controller> controllerMap = new HashMap<>();

    private RequestMapper() {
        controllerMap.put("/", new HomeController());
        controllerMap.put("/user/login", new LoginUserController());
        controllerMap.put("/user/logout", new LogoutUserController());
        controllerMap.put("/user/signup", new CreateUserController());
        controllerMap.put("/user/list", new ListUserController());
        controllerMap.put("/user/updateForm", new UpdateUserFormController());
        controllerMap.put("/user/update", new UpdateUserController());
    }

    public static RequestMapper getInstance() {
        return instance;
    }

    public Controller getController(String requestURI) {
        return controllerMap.get(requestURI);
    }
}
