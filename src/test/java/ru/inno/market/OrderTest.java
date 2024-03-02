package ru.inno.market;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.inno.market.core.Catalog;
import ru.inno.market.model.Client;
import ru.inno.market.model.Item;
import ru.inno.market.model.Order;

import java.util.HashMap;
import java.util.Map;

import static ru.inno.market.model.Category.LAPTOPS;
import static org.junit.jupiter.api.Assertions.*;


public class OrderTest {
    Client client = new Client(1, "Masha");
    Order order = new Order(0, client);
    Catalog catalog = new Catalog();
    Item item = new Item(1, "Ноутбук Лупа и Пупа", LAPTOPS, 99000);



    @ParameterizedTest
    @ValueSource(ints = {2, 5})
    @DisplayName("Добавление товара из каталога")
    public void itemOrder(int id){
        //Добавить товар в корзину
        order.addItem(catalog.getItemById(id));
        Map<Item, Integer> cart = new HashMap<>();
        assertNotSame(order.getItems(), cart);
    }



    @Test
    @Tag("Cart")
    @DisplayName("Проверка на пустоту корзины")
    public void CartNull(){
        Map<Item, Integer> cart = new HashMap<>();
        assertEquals(order.getCart(), cart);
    }

    @Test
    @Tag("Cart")
    @DisplayName("Проверка на заполненность корзины")
    public void CartNotNull(){
        Map<Item, Integer> cart = new HashMap<>();
        order.addItem(item);
        assertNotSame(order.getCart(), cart);
    }


    @Test
    @Tag("Cart")
    @DisplayName("Скидка к пустой корзине")
    public void DiscountNullOrder() {
        assertFalse(order.isDiscountApplied());
        order.applyDiscount(20);
        assertTrue(order.isDiscountApplied());
    }

    @Test
    @Tag("Discount")
    @DisplayName("Повторная скидка")
    public void DiscountDouble(){
        order.addItem(item);
        order.applyDiscount(0.2);
        var firstDiscountPrice = order.getTotalPrice();
        order.applyDiscount(0.2);
        var secondDiscountPrice = order.getTotalPrice();
        assertEquals(firstDiscountPrice, secondDiscountPrice);
    }

    @Test
    @Tag("Discount")
    @DisplayName("Проверка работы скидки")
    public void isDiscount(){
        order.addItem(item);
        var firstDiscountPrice = order.getTotalPrice();
        order.applyDiscount(0.2);
        var secondDiscountPrice = order.getTotalPrice();
        assertNotSame(firstDiscountPrice, secondDiscountPrice);
    }



}
