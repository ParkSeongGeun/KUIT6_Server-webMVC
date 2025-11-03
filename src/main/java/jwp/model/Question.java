package jwp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "QUESTIONS")
@Getter
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    private String writer;

    private String title;

    @Lob
    private String contents;

    private LocalDateTime createdDate;

    private Integer countOfAnswer;

    public Question(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = LocalDateTime.now();
        this.countOfAnswer = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return questionId == question.questionId && countOfAnswer == question.countOfAnswer && Objects.equals(writer, question.writer) && Objects.equals(title, question.title) && Objects.equals(contents, question.contents) && Objects.equals(createdDate, question.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, writer, title, contents, createdDate, countOfAnswer);
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createdDate=" + createdDate +
                ", countOfAnswer=" + countOfAnswer +
                '}';
    }
}
