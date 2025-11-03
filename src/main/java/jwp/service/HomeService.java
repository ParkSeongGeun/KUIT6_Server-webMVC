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
public class HomeService {

    private final QuestionDao questionDao;

    /**
     * question.findAll
     */
    public List<Question> findAll() {
        return questionDao.findAll();
    }
}
