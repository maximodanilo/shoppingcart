import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShoppingCartTest {

    Item laptop;
    Item desk;
    Item mouse;
    ShoppingCart shoppingCart;

    @BeforeEach
    void createItem() {
        laptop = new Item("Laptop", "1");
        mouse = new Item("Mouse", "2");
        desk = new Item("Desk", "3");

        laptop.setValue(new BigDecimal("3600.95"));
        desk.setValue(new BigDecimal("560.99"));
        mouse.setValue(new BigDecimal("25.99"));

        laptop.activate();
        desk.activate();
        mouse.activate();

        shoppingCart = new ShoppingCart();
    }

    @Test
    void addItemToCart() {
        //ItemOrder itemOrder = new ItemOrder(laptop, 1);
        shoppingCart.addItem(laptop);
        shoppingCart.addItem(laptop);
        shoppingCart.addItem(laptop);
        assertEquals(0, shoppingCart.calculateSubTotal()
                        .compareTo(new BigDecimal("3600.95")
                                .multiply(new BigDecimal(3)))
                , "Should return item value multiplied by 3");
    }

    @Test
    void addDifferentItemsToCart() {
        //ItemOrder itemOrder = new ItemOrder(laptop, 1);
        shoppingCart.addItem(laptop);
        shoppingCart.addItem(laptop);
        shoppingCart.addItem(desk);
        shoppingCart.addItem(desk);
        shoppingCart.addItem(mouse);
        assertEquals(0, shoppingCart.calculateSubTotal()
                        .compareTo(new BigDecimal("3600.95")
                                .multiply(new BigDecimal(2))
                                .add(desk.getValue().multiply(new BigDecimal(2)))
                                .add(mouse.getValue()))
                , "Should return value of off 5  items");
    }

    @Test
    void addCoupon() {
        shoppingCart.addItem(laptop);
        shoppingCart.addItem(laptop);
        shoppingCart.addItem(laptop);
        Coupon coupon = new Coupon("Whole site 20% off!", "20%SITE", 20);
        shoppingCart.addCoupon(coupon);

        assertEquals(0, shoppingCart.calculateSubTotal().compareTo(new BigDecimal("8642.28")));

    }

    @Test
    void addLaptopCoupon() {
        shoppingCart.addItem(laptop);
        shoppingCart.addItem(laptop);
        shoppingCart.addItem(desk);
        shoppingCart.addItem(desk);
        shoppingCart.addItem(mouse);

        Coupon coupon = new Coupon("5% on laptops", "5%LAPTOPS", 5);
        coupon.setSku("1");

        Coupon coupon2 = new Coupon("5% on desk", "5%DESKS", 5);
        coupon2.setSku("3");

        shoppingCart.addCoupon(coupon);
        shoppingCart.addCoupon(coupon2);

        assertEquals(0, shoppingCart.calculateSubTotal()
                        .compareTo(new BigDecimal("6841.80")
                                .add(new BigDecimal("1065.88"))
                                .add(mouse.getValue())
                        )
                , "Should return value of off 5  items");

    }

}