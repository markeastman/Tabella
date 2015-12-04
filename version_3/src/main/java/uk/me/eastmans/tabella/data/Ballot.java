package uk.me.eastmans.tabella.data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by meastman on 03/12/15.
 */
public class Ballot
{
    private Long id;

    private String question;

    private List<String> answers = new ArrayList<>();

    public Ballot() {}

    public Ballot( String question, String... answers)
    {
        this.question = question;
        this.answers.addAll(Arrays.asList(answers));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Ballot person = (Ballot) o;

        if (id == null || person.id == null)
            return false;
        return id.equals(person.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : -1;
    }

}
