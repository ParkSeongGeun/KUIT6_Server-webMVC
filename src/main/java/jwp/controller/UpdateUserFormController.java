package jwp.controller;

import jwp.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 사용자 정보 수정 폼 요청을 처리하는 컨트롤러
 * - userId 파라미터를 받아 권한을 검증
 * - 세션 사용자와 요청된 userId가 동일하면 수정 폼으로 포워드
 * - 그렇지 않으면 메인 페이지로 리다이렉트
 * Request Param: userId(String)
 * Response: authorized -> ("/user/updateForm.jsp") forward, unauthorized -> ("/") redirect
 */
public class UpdateUserFormController implements Controller {

    @Override
    public String service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("user");
        String requestUserId = request.getParameter("userId");

        // 세션에 사용자 정보가 없거나, 세션 사용자와 요청 userId가 다른 경우
        if (sessionUser == null || !sessionUser.getUserId().equals(requestUserId)) {
            return "redirect:/";
        }

        // 권한이 있는 경우 updateForm.jsp로 포워드
        return "/user/updateForm.jsp";
    }
}