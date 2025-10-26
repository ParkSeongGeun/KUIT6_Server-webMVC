package jwp.dao;

import core.jdbc.*;
import jwp.model.Question;

import java.util.List;

public class QuestionDao {

    private final JdbcTemplate<Question> jdbcTemplate;

    public QuestionDao(ConnectionProvider connectionProvider) {
        this.jdbcTemplate = new JdbcTemplate<>(connectionProvider);
    }

    public Question insert(String writer, String title, String contents) {
        String sql = "INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfAnswer) VALUES (?, ?, ?, CURRENT_TIMESTAMP(), 0)";
        KeyHolder keyHolder = new KeyHolder();

        PreparedStatementSetter setter = ps -> {
            ps.setString(1, writer);
            ps.setString(2, title);
            ps.setString(3, contents);
        };

        jdbcTemplate.update(sql, setter, keyHolder);

        Long questionId = keyHolder.getId();
        return findByQuestionId(questionId);
    }
    public List<Question> findAll() {
        String sql = "SELECT * FROM QUESTIONS";
        RowMapper<Question> rowMapper = rs -> new Question(
                rs.getLong("questionId"),
                rs.getString("writer"),
                rs.getString("title"),
                rs.getString("contents"),
                rs.getTimestamp("createdDate").toLocalDateTime(),
                rs.getInt("countOfAnswer")
        );
        return jdbcTemplate.query(sql, rowMapper);
    }
}
