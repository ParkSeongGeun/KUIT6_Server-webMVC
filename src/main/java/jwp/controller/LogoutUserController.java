package jwp.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 사용자 로그아웃을 처리하는 컨트롤러
 * - 세션에서 사용자 정보를 삭제하여 로그아웃 처리
 * - 메인 페이지로 리다이렉트
 * Response: "/" 페이지로 리다이렉트
 */
@WebServlet("/user/logout")
public class LogoutUserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        response.sendRedirect("/");
    }
}
