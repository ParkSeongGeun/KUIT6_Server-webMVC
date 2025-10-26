package jwp.dao;

import core.jdbc.TestConnectionManager;
import jwp.model.Question;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionDaoTest {

    private QuestionDao questionDao;
    private TestConnectionManager manager;

    @BeforeEach
    void setUp() throws SQLException {
        manager = new TestConnectionManager();
        initDatabase();
        questionDao = new QuestionDao(manager);
    }

    void initDatabase() throws SQLException {
        try (Connection conn = manager.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS QUESTIONS");
            stmt.execute("CREATE TABLE QUESTIONS (" +
                    "questionId BIGINT AUTO_INCREMENT, " +
                    "writer VARCHAR(30) NOT NULL, " +
                    "title VARCHAR(50) NOT NULL, " +
                    "contents VARCHAR(5000) NOT NULL, " +
                    "createdDate TIMESTAMP NOT NULL, " +
                    "countOfAnswer INT, " +
                    "PRIMARY KEY (questionId))");
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        cleanUpDatabase();
    }

    private void cleanUpDatabase() throws SQLException {
        try (Connection conn = manager.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM QUESTIONS");
        }
    }

    /**
     * 현재는 sql에 insert해주는 게 없으므로 해당 테스트에서 직접 insert후에 진행
     * @throws SQLException
     */
    @Test
    void findAll() throws SQLException {
        // given
        insertQuestion("박성근", "서버 공부 힘들어요", "근데 해야되는걸요!", 0);
        insertQuestion("김기훈", "서버 공부 재밌어요", "얼른 하세요.", 0);
        insertQuestion("조하상", "킹갓제너럴조하상", "^^", 0);

        // when
        List<Question> questions = questionDao.findAll();

        // then
        assertEquals(3, questions.size());
        assertEquals("박성근", questions.get(0).getWriter());
        assertEquals("서버 공부 힘들어요", questions.get(0).getTitle());
        assertEquals("김기훈", questions.get(1).getWriter());
        assertEquals("서버 공부 재밌어요", questions.get(1).getTitle());
        assertEquals("조하상", questions.get(2).getWriter());
        assertEquals("킹갓제너럴조하상", questions.get(2).getTitle());
    }

    private void insertQuestion(String writer,
                                String title,
                                String contents,
                                int countOfAnswer) throws SQLException {
        String sql = "INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfAnswer) VALUES (?, ?, ?, CURRENT_TIMESTAMP(), ?)";
        try (Connection conn = manager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, writer);
            pstmt.setString(2, title);
            pstmt.setString(3, contents);
            pstmt.setInt(4, countOfAnswer);
            pstmt.executeUpdate();
        }
    }
}
