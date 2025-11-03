package jwp.dao;

import jwp.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionDao {

    private final EntityManager em;

    @Transactional
    public void insert(Question question) {
        em.persist(question);
    }

    public Question findByQuestionId(Long questionId) {
        return em.find(Question.class, questionId);
    }

    public List<Question> findAll() {
        return em.createQuery("select q from Question q", Question.class).getResultList();
    }

    public void delete(Question question) {
        em.remove(question);
    }
}
