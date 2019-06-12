package edu.bjtu.demo.domain;

// lombok autogenerates getters, setters, toString() and a builder (see https://projectlombok.org/):
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Data
@Document(collection = "orders")
@Getter @Setter @ToString @Builder
public class Orders implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private long timestamp;
    private UserCoach userCoach;
    private String method;
    private Boolean delete;
}
