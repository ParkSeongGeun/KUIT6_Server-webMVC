package jwp.controller;

import core.db.MemoryUserRepository;
import jwp.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateUserController implements Controller {

    private static final Logger log = Logger.getLogger(CreateUserController.class.getName());

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );

        MemoryUserRepository repository = MemoryUserRepository.getInstance();
        if (repository.findUserById(user.getUserId()) != null) {
            log.log(Level.WARNING, "User already exists: " + user.getUserId());
            return "redirect:/user/signup?error=duplicate";
        }

        repository.addUser(user);
        log.log(Level.INFO, "User created successfully: " + user.getUserId());
        return "redirect:/user/list";
    }
}
