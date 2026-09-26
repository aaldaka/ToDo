package com.ga.todoApp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data //has all of setters getters etc
@Entity //theres an entity adn we wanna make a db for it
@Table(name="categories")
public class Category {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY) //has to be unique identifier (gen type means its serial ie 1,2,3,4)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column(nullable = true)
    private String imageUrl;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category", orphanRemoval = true) //when a cat is eleted, delete the recipes as well
    private List<Item> itemList;
}
