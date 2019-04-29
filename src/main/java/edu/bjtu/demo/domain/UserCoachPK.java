package edu.bjtu.demo.domain;

import java.io.Serializable;

public class UserCoachPK implements Serializable {

    private Integer userId;

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
