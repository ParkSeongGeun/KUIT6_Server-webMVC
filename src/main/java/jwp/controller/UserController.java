package jwp.controller;

import jwp.model.User;
import jwp.service.UserService;
import jwp.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public String createUser(@RequestParam String userId,
                             @RequestParam String password,
                             @RequestParam String name,
                             @RequestParam String email) {
        // TODO: 엔티티를 컨트롤러에서 노출시키는 것은 지양 -> DTO로 고고
        User user = new User(userId, password, name, email);
        userService.createUser(user);
        System.out.println("user 회원가입 완료");
        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String listUsers(HttpSession session, Model model) {
        if (UserSessionUtils.isLogined(session)) {
            model.addAttribute("users", userService.findAllUsers());
            return "user/list";
        }
        return "redirect:/user/loginForm";
    }

    @GetMapping("/form")
    public String signupForm() {
        return "user/form";
    }

    @GetMapping("/updateForm")
    public String updateForm(@RequestParam String userId, HttpSession session) {
        User user = userService.findUserById(userId);
        Object value = session.getAttribute("user");

        if (user != null && value != null) {
            if (user.equals(value)) {
                return "user/updateForm";
            }
        }
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam String userId,
                             @RequestParam String password,
                             @RequestParam String name,
                             @RequestParam String email) {
        // TODO: 엔티티를 컨트롤러에서 노출시키는 것은 지양 -> DTO로 고고
        User user = new User(userId, password, name, email);
        userService.updateUser(user);
        return "redirect:/user/list";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @PostMapping("/login")
    public String login(@RequestParam String userId,
                        @RequestParam String password,
                        HttpSession session) {
        User user = userService.login(userId, password);

        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

    @GetMapping("/loginFailed")
    public String loginFailed() {
        return "user/loginFailed";
    }
}
