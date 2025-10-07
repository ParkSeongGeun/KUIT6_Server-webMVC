package jwp.controller;

import core.db.MemoryUserRepository;
import jwp.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 사용자 로그인을 처리하는 컨트롤러
 * - userId와 password를 받아서 사용자 인증을 수행하고,
 * - 성공 시 세션에 사용자 정보를 저장한 후 메인 페이지로 리다이렉트
 * Request Param: userId(String), password(String)
 * Response : success -> ("/") redirect, fail -> ("/user/loginFailed.jsp")
 */
public class LoginUserController implements Controller {

    @Override
    public String service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        User user = MemoryUserRepository.getInstance().findUserById(userId);

        if (user != null && user.matchPassword(password)) {
            // 로그인 성공 - 세션에 사용자 정보 저장
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return "redirect:/";  // redirect로 변경
        } else {
            // 로그인 실패
            return "redirect:/user/loginFailed.jsp";  // redirect로 변경
        }
    }
}
