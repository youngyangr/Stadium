package edu.bjtu.demo.domain;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;

@Entity
@ApiModel("UserCoach entity")
@IdClass(UserCoachPK.class)
public class UserCoach extends Auditable<String>{
    @Id
    private Integer userId;

    @Id
    private Integer coachId;

    @Version
    private Integer version;

    public UserCoach(){
    }

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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
