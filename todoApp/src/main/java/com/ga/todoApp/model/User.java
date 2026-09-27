package com.ga.todoApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Table(name="users")
@ToString(exclude = {"password", "userProfile", "recipeList", "categoryList"}) //itll exclude these when using the toString method
public class User {

    @Column
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String emailAddress;

    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //req is sent with password(user sends it), res is returned without the password
    private String password;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="profile_id", referencedColumnName = "id")
    private UserProfile userProfile;

}