package jwp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogoutUserControllerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    private LogoutUserController controller;

    @BeforeEach
    void setUp() {
        controller = new LogoutUserController();
    }

    @Test
    @DisplayName("로그아웃 시 세션에서 사용자 정보를 삭제하고 메인 페이지로 리다이렉트한다")
    void logoutSuccess() throws Exception {
        // given
        when(request.getSession()).thenReturn(session);

        // when
        String viewName = controller.service(request, response);

        // then
        assertEquals("redirect:/", viewName);
        verify(session).removeAttribute("user");
    }
}