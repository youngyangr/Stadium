package edu.bjtu.demo.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
public class User extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Size(min = 2, message = "username must be at least 2 characters.")
    private String username;

    @Size(min = 2, message = "password must be at least 2 characters.")
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
