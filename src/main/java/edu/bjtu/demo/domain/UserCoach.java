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

    private String relation;

    @Version
    private Integer version;

    @ManyToOne
    @JoinColumn(name = "userId",insertable = false, updatable = false)
    protected User user;
    @ManyToOne
    @JoinColumn(name = "coachId",insertable = false, updatable = false)
    protected Coach  coach;

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

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
