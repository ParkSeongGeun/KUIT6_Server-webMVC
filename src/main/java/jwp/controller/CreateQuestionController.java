package jwp.controller;

import core.mvc.Controller;
import jwp.dao.QuestionDao;
import jwp.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateQuestionController implements Controller {

    private final QuestionDao questionDao;

    public CreateQuestionController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String writer = req.getParameter("writer");
        String title = req.getParameter("title");
        String contents = req.getParameter("contents");

        Question question = questionDao.insert(writer, title, contents);

        return "redirect:/";
    }
}
