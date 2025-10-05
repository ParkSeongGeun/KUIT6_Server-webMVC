package jwp.controller;

import core.db.MemoryUserRepository;
import jwp.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 사용자 정보 수정을 처리하는 컨트롤러
 * - userId, password, name, email 파라미터를 받아서 사용자 정보를 업데이트
 * - 업데이트 후 사용자 목록 페이지로 리다이렉트
 * Request Param: userId(String), password(String), name(String), email(String)
 * Response: "/user/list" 페이지로 리다이렉트
 */
@WebServlet("/user/update")
public class UpdateUserController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        User user = MemoryUserRepository.getInstance().findUserById(userId);

        if (user != null) {
            User updateUser = new User(userId, password, name, email);
            user.update(updateUser);
        }

        response.sendRedirect("/user/list");
    }
}
