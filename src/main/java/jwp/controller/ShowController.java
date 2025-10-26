package jwp.controller;

import core.mvc.Controller;
import jwp.dao.QuestionDao;
import jwp.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowController implements Controller {

    private final QuestionDao questionDao;

    public ShowController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // req로부터 questionId를 받아온다.
        Long questionId = Long.parseLong(req.getParameter("questionId"));
        // resp에 받아온 것들을 설정
        Question question = questionDao.findByQuestionId(questionId);
        req.setAttribute("question", question);
        return "/qna/show.jsp";
    }
}
