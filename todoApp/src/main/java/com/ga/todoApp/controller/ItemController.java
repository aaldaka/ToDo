package com.ga.todoApp.controller;

import com.ga.todoApp.model.Item;
import com.ga.todoApp.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo/categories") //instead of api
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping("/{categoryId}/items") //create an item in a category
    public Item createItem(@PathVariable Long categoryId, @RequestBody Item item){
        System.out.println("Calling Controller createItem");
        return itemService.createItem(categoryId, item);
    }

    @GetMapping("/{categoryId}/items") //get all items in category
    public List<Item> getItems(@PathVariable("categoryId")Long categoryId){
        System.out.println("Calling Controller getItems");
        return itemService.getItems(categoryId);
    }

    @GetMapping("/{categoryId}/items/{itemId}") //get a specific item from a category
    public Item getItem(@PathVariable Long categoryId, @PathVariable Long itemId){
        System.out.println("Calling Controller getItem");
        return itemService.getItem(categoryId, itemId);
    }

    @PutMapping("/{categoryId}/items/{itemId}")
    public Item updateItem(@PathVariable Long categoryId, @PathVariable Long itemId, @RequestBody Item item){
        System.out.println("Calling Controller updateItem");
        return itemService.updateItem(categoryId, itemId, item);
    }

    @DeleteMapping("/{categoryId}/items/{itemId}")
    public Item deleteItem(@PathVariable Long categoryId, @PathVariable Long itemId){
        System.out.println("Calling Controller deleteItem");
        return itemService.deleteItem(categoryId, itemId);
    }

}
