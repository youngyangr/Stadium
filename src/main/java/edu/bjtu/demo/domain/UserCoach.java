package edu.bjtu.demo.domain;

import javax.persistence.*;

@Entity
@IdClass(UserCoachPK.class)
public class UserCoach extends Auditable<String>{
    @Id
    private Integer userId;

    @Id
    private Integer coachId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCoachId() {
        return coachId;
    }

    public void setCoachId(Integer coachId) {
        this.coachId = coachId;
    }
}
