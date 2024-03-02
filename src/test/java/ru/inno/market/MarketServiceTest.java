package ru.inno.market;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.inno.market.core.Catalog;
import ru.inno.market.core.MarketService;
import ru.inno.market.model.Client;
import ru.inno.market.model.Item;


import static org.junit.jupiter.api.Assertions.*;
import static ru.inno.market.model.Category.LAPTOPS;
import static ru.inno.market.model.PromoCodes.LOVE_DAY;


public class MarketServiceTest {

    Client client = new Client(1, "Masha");
    Catalog catalog = new Catalog();
    MarketService service = new MarketService();
    Item item = new Item(1, "Ноутбук Лупа и Пупа", LAPTOPS, 99000);


    //Скидка не применяется к товару, который добавлен в корзину после применения промокода,
    //хотя должна применятся ко всей корзине (не знаю как реализовать)
    @Test
    @Tag("Cart") @Tag("Discount")
    @DisplayName("Добавление товара после скидки к корзине")
    public void CartNull(){
        int id = service.createOrderFor(client);
        service.addItemToOrder(catalog.getItemById(4), id);
        var serviceprice = service.applyDiscountForOrder(id, LOVE_DAY);
        service.addItemToOrder(catalog.getItemById(5), id);
        var truePrice = (catalog.getItemById(4).getPrice() + catalog.getItemById(5).getPrice())*(1-LOVE_DAY.getDiscount());
        assertTrue(truePrice == service.getOrderInfo(id).getTotalPrice());
    }









}
