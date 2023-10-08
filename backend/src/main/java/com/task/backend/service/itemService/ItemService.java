package com.task.backend.service.itemService;

import com.task.backend.model.Item;

import java.util.List;

public interface ItemService {
    List<Item> getAllItems();
    Item getItemById(Long id);
    Item createItem(Item item);
    Item updateItem(Long id, Item updatedItem);
    void deleteItem(Long id);
}
