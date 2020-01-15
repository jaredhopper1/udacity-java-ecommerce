package com.example.demo.ControllerTests;

import com.example.demo.TestUtils;
import com.example.demo.controllers.ItemController;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ItemControllerTest {
    private ItemController itemController;

    private ItemRepository itemRepo = mock(ItemRepository.class);

    @Before
    public void setUp() throws Exception {
        itemController = new ItemController();
        TestUtils.injectObject(itemController, "itemRepository", itemRepo);
    }

    @Test
    public void testGetItems() throws Exception {

        Item item=new Item();
        item.setId(1l);
        item.setName("Apple");
        item.setDescription("Envy Apple");
        item.setPrice(BigDecimal.valueOf(1.39));

        Item item2=new Item();
        item2.setId(2l);
        item2.setName("Banana");
        item2.setDescription("Cavendish Banana");
        item2.setPrice(BigDecimal.valueOf(1.25));

        List<Item> listOfItems= new ArrayList<>();
        listOfItems.add(item);
        listOfItems.add(item2);

        doReturn(listOfItems).when(itemRepo).findAll();

        final ResponseEntity<List<Item>> response = itemController.getItems();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        List<Item> responseBody = response.getBody();
        Assert.assertEquals(2, responseBody.size());

    }

    @Test
    public void testGetItemById() {
        Item item=new Item();
        item.setId(1l);
        item.setName("Apple");
        item.setDescription("Envy Apple");
        item.setPrice(BigDecimal.valueOf(1.39));

        Item item2=new Item();
        item2.setId(2l);
        item2.setName("Banana");
        item2.setDescription("Cavendish Banana");
        item2.setPrice(BigDecimal.valueOf(1.25));

        doReturn(Optional.of(item)).when(itemRepo).findById(item.getId());


        final ResponseEntity<Item> response = itemController.getItemById(item.getId());

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        Assert.assertEquals("Apple", response.getBody().getName());
    }

}
