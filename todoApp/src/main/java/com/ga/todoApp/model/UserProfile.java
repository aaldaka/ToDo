package com.ga.todoApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="profiles")
public class UserProfile {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String profileDescription;

    @JsonIgnore
    @OneToOne(mappedBy = "userProfile", fetch = FetchType.LAZY)
    private User user;
}