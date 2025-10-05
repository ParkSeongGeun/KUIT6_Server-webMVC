package jwp.controller;

import core.db.MemoryUserRepository;
import jwp.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

/**
 * 사용자 목록을 조회하는 컨트롤러
 * - 로그인한 사용자만 접근 가능
 * - 로그인하지 않은 경우 로그인 페이지로 리다이렉트
 */
@WebServlet("/user/list")
public class ListUserController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 세션에서 로그인 정보 확인
        HttpSession session = req.getSession();
        Object value = session.getAttribute("user");

        // 로그인하지 않은 경우 로그인 페이지로 리다이렉트
        if (value == null) {
            resp.sendRedirect("/user/login.html");
            return;
        }

        // 로그인한 경우 사용자 목록 조회
        Collection<User> users = MemoryUserRepository.getInstance().findAll();
        req.setAttribute("users", users);
        RequestDispatcher rd = req.getRequestDispatcher("/user/list.jsp");
        rd.forward(req, resp);
    }
}
