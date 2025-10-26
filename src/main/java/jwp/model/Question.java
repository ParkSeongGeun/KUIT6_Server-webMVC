package jwp.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Question {
     private final Long questionId;
     private final String writer;
     private final String title;
     private final String contents;
     private final LocalDateTime createdDate;
     private final int countOfAnswer;

     public Question(Long questionId, String writer, String title, String contents, LocalDateTime createdDate, int countOfAnswer) {
         this.questionId = questionId;
         this.writer = writer;
         this.title = title;
         this.contents = contents;
         this.createdDate = createdDate;
         this.countOfAnswer = countOfAnswer;
     }

    public Long getQuestionId() {
        return questionId;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public int getCountOfAnswer() {
        return countOfAnswer;
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
