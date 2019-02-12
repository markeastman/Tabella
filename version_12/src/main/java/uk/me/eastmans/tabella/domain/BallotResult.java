package uk.me.eastmans.tabella.domain;

import javax.persistence.*;

/**
 * Created by meastman on 03/12/15.
 */
@Entity
@Table(name = "BALLOT_RESULT")
public class BallotResult
{
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Ballot ballot;

    private Integer answerIndex = -1;

    private float longitude;

    private float latitude;

    public BallotResult() {}

    public BallotResult(User u, Ballot b, int answer)
    {
        this.user = u;
        this.ballot = b;
        this.answerIndex = answer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Ballot getBallot() {
        return ballot;
    }

    public void setBallot(Ballot ballot) {
        this.ballot = ballot;
    }

    public Integer getAnswerIndex() {
        return answerIndex;
    }

    public void setAnswerIndex(Integer answerIndex) {
        this.answerIndex = answerIndex;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        BallotResult ballot = (BallotResult) o;

        if (id == null || ballot.id == null)
            return false;
        return id.equals(ballot.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : -1;
    }

    public String toString() {
        return getUser() + ":" + getBallot() + ":" + answerIndex;
    }

}
