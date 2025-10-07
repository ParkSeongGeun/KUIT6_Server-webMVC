package jwp;

import jwp.controller.Controller;

import java.util.HashMap;
import java.util.Map;

public class RequestMapper {
    private static final RequestMapper instance = new RequestMapper();

    private final Map<String, Controller> controllerMap = new HashMap<>();

    private RequestMapper() {
        // todo: URL-Controller 매핑을 등록할 예정
    }

    public static RequestMapper getInstance() {
        return instance;
    }

    public Controller getController(String requestURI) {
        return controllerMap.get(requestURI);
    }
}
