package jwp.controller;

import core.db.MemoryUserRepository;
import jwp.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 사용자 정보 수정을 처리하는 컨트롤러
 * - userId, password, name, email 파라미터를 받아서 사용자 정보를 업데이트
 * - 자신의 정보만 수정 가능 (세션 사용자와 동일한지 검증)
 * - 업데이트 후 사용자 목록 페이지로 리다이렉트
 * Request Param: userId(String), password(String), name(String), email(String)
 * Response: "/user/list" 페이지로 리다이렉트
 */
public class UpdateUserController implements Controller {

    @Override
    public String service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 세션에서 로그인 정보 확인
        HttpSession session = request.getSession();
        Object value = session.getAttribute("user");

        // 로그인하지 않은 경우 로그인 페이지로 리다이렉트
        if (value == null) {
            return "redirect:/user/login.html";
        }

        User sessionUser = (User) value;
        String userId = request.getParameter("userId");

        // 자신의 정보가 아닌 경우 사용자 목록 페이지로 리다이렉트
        if (!sessionUser.getUserId().equals(userId)) {
            return "redirect:/user/list";
        }

        // 자신의 정보 수정
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        User user = MemoryUserRepository.getInstance().findUserById(userId);

        if (user != null) {
            User updateUser = new User(userId, password, name, email);
            user.update(updateUser);
        }

        return "redirect:/user/list";
    }
}