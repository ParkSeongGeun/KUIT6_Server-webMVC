package jwp.controller;

import core.mvc.Controller;
import jwp.dao.QuestionDao;
import jwp.model.Question;
import jwp.util.UserSessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateQuestionController implements Controller {

    private final QuestionDao questionDao;

    public CreateQuestionController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return "redirect:/user/loginForm";
        }

        String writer = req.getParameter("writer");
        String title = req.getParameter("title");
        String contents = req.getParameter("contents");

        if (writer == null || writer.trim().isEmpty() ||
                title == null || title.trim().isEmpty() ||
                contents == null || contents.trim().isEmpty()) {
            return "redirect:/qna/form";  // 빈 값이면 작성 폼으로 다시 고고
        }

        questionDao.insert(writer, title, contents);
        return "redirect:/";
    }
}
