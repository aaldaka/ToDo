package com.ga.todoApp.controller;

import com.ga.todoApp.model.Category;
import com.ga.todoApp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todo") //instead of api
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/hello")
    public String hello(){
        return "Hello World!";
    }

    @GetMapping("/categories")
    public List<Category> getCategories(){
        System.out.println("Calling getCategories");
        return categoryService.getCategories();
    }

    @PostMapping("/categories")
    public Category createCategory(@RequestBody Category categoryObj){
        System.out.println("Calling create category");
        return categoryService.createCategory(categoryObj);
    }

    @GetMapping("/category")
    public Optional<Category> getCategory(@RequestParam("id") long id){
        System.out.println("Service calling getCategory");
        return categoryService.getCategory(id);
    }

// update attempt from previous lab
//    @PutMapping("/categories/{categoryId}")
//    public Category updateCategory(@PathVariable("categoryId")long id, @RequestParam(value = "name") String name,
//                                   @RequestParam(value = "description") String desc){
//        System.out.println("Service calling updateCategory");
//        return categoryService.updateCategory(id, name, desc);
//    }

}
