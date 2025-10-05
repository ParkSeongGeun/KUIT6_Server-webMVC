package jwp.controller;

import core.db.MemoryUserRepository;
import jwp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUserControllerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    private LoginUserController controller;

    @BeforeEach
    void setUp() {
        controller = new LoginUserController();
    }

    @Test
    @DisplayName("로그인 성공 시 세션에 사용자 정보를 저장하고 메인 페이지로 리다이렉트한다")
    void loginSuccess() throws ServletException, IOException {
        // given
        String userId = "gildong";
        String password = "password";
        User user = new User(userId, password, "홍길동", "gildong@example.com");

        // 사용자를 저장소에 추가
        MemoryUserRepository.getInstance().addUser(user);

        when(request.getParameter("userId")).thenReturn(userId);
        when(request.getParameter("password")).thenReturn(password);
        when(request.getSession()).thenReturn(session);

        // when
        controller.doPost(request, response);

        // then
        verify(session).setAttribute("user", user);
        verify(response).sendRedirect("/");
    }

    @Test
    @DisplayName("로그인 실패 시 로그인 실패 페이지로 리다이렉트한다 - 잘못된 비밀번호")
    void loginFailWithWrongPassword() throws ServletException, IOException {
        // given
        String userId = "gildong";
        String correctPassword = "password";
        String wrongPassword = "wrongPassword";
        User user = new User(userId, correctPassword, "홍길동", "gildong@example.com");

        // 사용자를 저장소에 추가
        MemoryUserRepository.getInstance().addUser(user);

        when(request.getParameter("userId")).thenReturn(userId);
        when(request.getParameter("password")).thenReturn(wrongPassword);

        // when
        controller.doPost(request, response);

        // then
        verify(response).sendRedirect("/user/loginFailed.jsp");
        verify(request, never()).getSession();
    }

    @Test
    @DisplayName("로그인 실패 시 로그인 실패 페이지로 리다이렉트한다 - 존재하지 않는 사용자")
    void loginFailWithNonExistentUser() throws ServletException, IOException {
        // given
        String userId = "nonexistent";
        String password = "password";

        when(request.getParameter("userId")).thenReturn(userId);
        when(request.getParameter("password")).thenReturn(password);

        // when
        controller.doPost(request, response);

        // then
        verify(response).sendRedirect("/user/loginFailed.jsp");
        verify(request, never()).getSession();
    }
}