package jwp.service;

import jwp.dao.QuestionDao;
import jwp.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionDao questionDao;

    @Transactional
    public void createQuestion(Question question) {
        questionDao.insert(question);
    }

    public Question findQuestionById(Long questionId) {
        return questionDao.findByQuestionId(questionId);
    }

    public List<Question> findAllQuestions() {
        return questionDao.findAll();
    }

    @Transactional
    public void deleteQuestion(Long questionId, String requestUserId) {
        Question question = questionDao.findByQuestionId(questionId);
        if (question == null) {
            throw new IllegalArgumentException("Question not found");
        }
        if (!question.getWriter().equals(requestUserId)) {
            throw new IllegalStateException("Not authorized to delete this question");
        }
        questionDao.delete(question);
    }
}
