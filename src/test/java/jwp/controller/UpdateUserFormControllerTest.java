package jwp.controller;

import jwp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserFormControllerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    private UpdateUserFormController controller;

    @BeforeEach
    void setUp() {
        controller = new UpdateUserFormController();
    }

    @Test
    @DisplayName("세션 사용자와 요청 userId가 동일하면 updateForm.jsp로 포워드한다")
    void updateFormWithAuthorizedUser() throws ServletException, IOException {
        // given
        String userId = "gildong";
        User sessionUser = new User(userId, "password", "홍길동", "gildong@example.com");

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(sessionUser);
        when(request.getParameter("userId")).thenReturn(userId);
        // when
        String view = controller.process(request, response);

        // then
        assertEquals("/user/updateForm.jsp", view);
    }

    @Test
    @DisplayName("세션 사용자와 요청 userId가 다르면 메인 페이지로 리다이렉트한다")
    void updateFormWithUnauthorizedUser() throws ServletException, IOException {
        // given
        String sessionUserId = "gildong";
        String requestUserId = "other";
        User sessionUser = new User(sessionUserId, "password", "홍길동", "gildong@example.com");

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(sessionUser);
        when(request.getParameter("userId")).thenReturn(requestUserId);

        // when
        String view = controller.process(request, response);

        // then
        assertEquals("redirect:/", view);
    }

    @Test
    @DisplayName("세션에 사용자 정보가 없으면 메인 페이지로 리다이렉트한다")
    void updateFormWithoutSession() throws ServletException, IOException {
        // given
        String requestUserId = "gildong";

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);
        when(request.getParameter("userId")).thenReturn(requestUserId);

        // when
        String view = controller.process(request, response);

        // then
        assertEquals("redirect:/", view);
    }
}
