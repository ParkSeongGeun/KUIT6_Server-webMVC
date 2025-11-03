package jwp.controller;

import jwp.model.Question;
import jwp.model.User;
import jwp.service.QuestionService;
import jwp.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/form")
    public String createQuestionForm(HttpSession session) {
        if (UserSessionUtils.isLogined(session)) {
            return "qna/form";
        }
        return "redirect:/user/loginForm";
    }

    @PostMapping("/create")
    public String createQuestion(@RequestParam String writer,
                                 @RequestParam String title,
                                 @RequestParam String contents,
                                 HttpSession session) {
        if (!UserSessionUtils.isLogined(session)) {
            return "redirect:/user/loginForm";
        }

        if (writer == null || writer.trim().isEmpty() ||
                title == null || title.trim().isEmpty() ||
                contents == null || contents.trim().isEmpty()) {
            return "redirect:/qna/form";
        }

        Question question = new Question(writer, title, contents);
        questionService.createQuestion(question);
        return "redirect:/";
    }

    @GetMapping("/show")
    public String showQuestion(@RequestParam Long questionId,
                               Model model) {
        Question question = questionService.findQuestionById(questionId);
        model.addAttribute("question", question);
        return "qna/show";
    }

    @GetMapping("/delete")
    public String deleteQuestion(@RequestParam Long questionId, HttpSession session) {
        if (!UserSessionUtils.isLogined(session)) {
            return "redirect:/user/loginForm";
        }

        User loginUser = (User) session.getAttribute("user");
        questionService.deleteQuestion(questionId, loginUser.getUserId());
        return "redirect:/";
    }
}
