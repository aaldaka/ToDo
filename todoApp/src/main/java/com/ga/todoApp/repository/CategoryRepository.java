package com.ga.todoApp.repository;

import com.ga.todoApp.model.Category;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@SpringBootApplication
public interface CategoryRepository extends JpaRepository<Category, Long> {
	Category findByName(String categoryName);
	Category findByNameAndDescription(String name, String desc);
	List<Category> findByUserId(Long userId);
	Category findByUserIdAndName(Long userId, String categoryName);
	Category findByIdAndUserId(Long categoryId, Long userId);
}
