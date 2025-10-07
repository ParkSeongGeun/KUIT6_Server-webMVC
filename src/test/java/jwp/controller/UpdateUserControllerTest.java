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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserControllerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private UpdateUserController controller;

    @BeforeEach
    void setUp() {
        controller = new UpdateUserController();
    }

    @Test
    @DisplayName("사용자 정보를 수정하고 사용자 목록 페이지로 리다이렉트한다")
    void updateUserSuccess() throws ServletException, IOException {
        // given
        String userId = "gildong";
        String originalPassword = "password";
        String newPassword = "newPassword";
        String newName = "홍길동수정";
        String newEmail = "updated@example.com";

        // 기존 사용자를 저장소에 추가
        User originalUser = new User(userId, originalPassword, "홍길동", "gildong@example.com");
        MemoryUserRepository.getInstance().addUser(originalUser);

        when(request.getParameter("userId")).thenReturn(userId);
        when(request.getParameter("password")).thenReturn(newPassword);
        when(request.getParameter("name")).thenReturn(newName);
        when(request.getParameter("email")).thenReturn(newEmail);

        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(originalUser);

        // when
        String view = controller.process(request, response);

        // then
        assertEquals("redirect:/user/list", view);

        // 저장소에서 업데이트된 사용자 정보 확인
        User updatedUser = MemoryUserRepository.getInstance().findUserById(userId);
        assertNotNull(updatedUser);
        assertEquals(newPassword, updatedUser.getPassword());
        assertEquals(newName, updatedUser.getName());
        assertEquals(newEmail, updatedUser.getEmail());
    }

    @Test
    @DisplayName("존재하지 않는 사용자는 업데이트하지 않고 리다이렉트한다")
    void updateNonExistentUser() throws ServletException, IOException {
        // given
        String userId = "nonexistent";

        when(request.getParameter("userId")).thenReturn(userId);

        HttpSession session = mock(HttpSession.class);
        User sessionUser = new User("sessionUser", "password", "name", "email@email.com");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(sessionUser);

        // when
        String view = controller.process(request, response);

        // then
        assertEquals("redirect:/user/list", view);

        // 사용자가 여전히 존재하지 않음을 확인
        User user = MemoryUserRepository.getInstance().findUserById(userId);
        assertNull(user);
    }
}
