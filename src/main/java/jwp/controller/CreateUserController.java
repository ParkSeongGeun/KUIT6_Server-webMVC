package jwp.controller;

import core.db.MemoryUserRepository;
import jwp.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateUserController implements Controller {

    @Override
    public String service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );

        MemoryUserRepository.getInstance().addUser(user);
        System.out.println("user 회원가입 완료");
        return "redirect:/user/list";
    }
}
