package jwp;

import jwp.controller.Controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *   1. URL 추출
 *   2. RequestMapper에게 Controller 찾아달라고 요청
 *   3. Controller 실행
 *   4. 반환된 viewName을 redirect 또는 forward 처리
 */
@WebServlet("/")
public class DispatcherServlet extends HttpServlet {
    // 로그 확인용
    private static final Logger log = Logger.getLogger(DispatcherServlet.class.getName());

    @Override
    protected void service(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        log.log(Level.INFO, "Request URI: " + requestURI);

        // RequestMapper -> URL: Controller 매핑
        RequestMapper mapper = RequestMapper.getInstance();
        Controller controller = mapper.getController(requestURI);

        if (controller == null) {
            log.log(Level.WARNING, "Controller not found for URI: " + requestURI);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try {
            // Controller -> viewName 받기
            String viewName = controller.process(req, resp);

            // redirect / forward 처리
            move(viewName, req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void move(
            String viewName,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        if (viewName.startsWith("redirect:")) {
            // redirect
            String redirectUrl = viewName.substring("redirect:".length());
            response.sendRedirect(redirectUrl);
        } else {
            // forward
            RequestDispatcher dispatcher = request.getRequestDispatcher(viewName);
            dispatcher.forward(request, response);
        }
    }
}
