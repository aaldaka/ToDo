package com.ga.todoApp.repository;

import com.ga.todoApp.model.Category;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;

@SpringBootApplication
public interface CategoryRepository extends JpaRepository<Category, Long> {
	Category findByName(String name);
}
