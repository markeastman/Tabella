package uk.me.eastmans.tabella.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by meastman on 03/12/15.
 */
@Entity
@Table(name = "BALLOT")
public class Ballot
{
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    private String question;

    @ElementCollection(fetch =  FetchType.EAGER)
    @OrderColumn(name = "answer_sequence")
    private List<String> answers = new ArrayList<>();

    private Integer answerIndex = -1;

    public Ballot() {}

    public Ballot(String question, String... answers)
    {
        this.question = question;
        this.answers.addAll(Arrays.asList(answers));
    }

    public Ballot(String question, List<String> answers)
    {
        this.question = question;
        this.answers.addAll(answers);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public Integer getAnswerIndex() {
        return answerIndex;
    }

    public void setAnswerIndex(Integer answerIndex) {
        this.answerIndex = answerIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Ballot ballot = (Ballot) o;

        if (id == null || ballot.id == null)
            return false;
        return id.equals(ballot.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : -1;
    }

    public String toString() {
        return getQuestion() + ":" + getAnswers();
    }

}
