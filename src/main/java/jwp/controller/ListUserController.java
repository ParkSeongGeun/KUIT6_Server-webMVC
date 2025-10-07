package jwp.controller;

import core.db.MemoryUserRepository;
import jwp.model.User;

import javax.servlet.ServletException;
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
public class ListUserController implements Controller {
    @Override
    public String service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 세션에서 로그인 정보 확인
        HttpSession session = request.getSession();
        Object value = session.getAttribute("user");

        // 로그인하지 않은 경우 로그인 페이지로 리다이렉트
        if (value == null) {
            return "redirect:/user/login.html";
        }

        // 로그인한 경우 사용자 목록 조회
        Collection<User> users = MemoryUserRepository.getInstance().findAll();
        request.setAttribute("users", users);
        return "/user/list.jsp";  // forward
    }
}