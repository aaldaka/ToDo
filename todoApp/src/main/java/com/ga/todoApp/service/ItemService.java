package com.ga.todoApp.service;

import com.ga.todoApp.exception.InformationNotFoundException;
import com.ga.todoApp.model.Category;
import com.ga.todoApp.model.Item;
import com.ga.todoApp.repository.CategoryRepository;
import com.ga.todoApp.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Item createItem(Long categoryId, Item itemObj){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new InformationNotFoundException("Category with that ID doesnt exist"));

        Item item = new Item();
        item.setName(itemObj.getName());
        item.setDescription(itemObj.getDescription());
        item.setDueDate(itemObj.getDueDate());
        item.setCategory(category);

        return itemRepository.save(item);
    }

    public List<Item> getItems(Long categoryId){
        System.out.println("Calling Service getItems");
        Category cat = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new InformationNotFoundException("A category with that ID doesnt exist"));
        return cat.getItemList();
    }

    public Item getItem(Long categoryId, Long itemId){
        System.out.println("Calling Service getItem");
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new InformationNotFoundException("A category with that ID doesnt exist"));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new InformationNotFoundException("Item doesnt exist."));

        if (!item.getCategory().getId().equals(categoryId)){
            throw new InformationNotFoundException("The Item doesnt belong to said Category");
        }
        return item;
    }

    public Item updateItem(Long categoryId, Long itemId, Item itemObj){
        Item item = getItem(categoryId, itemId); //does the checks, no need to repeat the ID checking

        item.setName(itemObj.getName());
        item.setDescription(itemObj.getDescription());
        item.setDueDate(itemObj.getDueDate());

        return itemRepository.save(item);
    }

    public Item deleteItem(Long categoryId, Long itemId){
        System.out.println("Calling Service deleteItem"); // had to do checks in order to save and return the deleted item
        categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new InformationNotFoundException("Category with that ID doesnt exist"));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new InformationNotFoundException("Item doesnt exist."));

        itemRepository.deleteById(itemId);
        return item;
    }

}
