import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ItemOrderTest {

    Item item1;
    Item item2;

    @BeforeEach
    void setUp() {
        item1 = new Item("Monitor", "987654");
        item1.setValue(new BigDecimal(200));

        item2 = new Item("Laptop", "456789");
        item2.setValue(new BigDecimal(2000));
    }

    @Test
    void addItem() {
        ItemOrder itemOrder = new ItemOrder(item1, 2);

        assertEquals("987654", itemOrder.getItem().getSku(), "SKU not set correctly");
        assertEquals(2, itemOrder.getQuantity(), "Item quantity should be 2");

        itemOrder.addItem(item1);
        assertEquals(3, itemOrder.getQuantity(), "Item quantity should be 3");
        assertEquals(0, itemOrder.getTotal().compareTo(new BigDecimal("600")),
                "Total amount should be $600");

        itemOrder.addItem(item1);
        itemOrder.addItem(item1);
        assertEquals(0, itemOrder.getTotal().compareTo(new BigDecimal("1000")),
                "Total amount should be $600");

        assertThrows(IllegalArgumentException.class, ()->{
           itemOrder.addItem(item2);
        }, "Should thrown an exception because SKU is incompatible");
    }

    @Test
    void getTotal() {
        ItemOrder itemOrder = new ItemOrder(item1, 2);

        assertEquals(0, itemOrder.getTotal().compareTo(new BigDecimal("400")));
    }

    @DisplayName("Should give 10% discount for laptops")
    @Test
    void addCouponForLaptops() {
        Coupon coupon = new Coupon("10% off on any laptop", "10%LAPTOP",10);
        coupon.setSku("456789");

        ItemOrder itemOrder = new ItemOrder(item2, 1);
        itemOrder.addCoupon(coupon);

        BigDecimal total = itemOrder.getTotal();
        assertEquals(0, total.compareTo(new BigDecimal("1800.00")));
    }

    @DisplayName("Should NOT give 10% discount because item is not a laptop")
    @Test
    void addCouponForLaptopsOnWrongProduct() {
        Coupon coupon = new Coupon("10% off on any laptop", "10%LAPTOP", 10);
        coupon.setSku("987654");

        ItemOrder itemOrder = new ItemOrder(item2, 1);

        assertThrows(IllegalArgumentException.class,
                () -> {
                    itemOrder.addCoupon(coupon);
                },
                "Should not give 10 discount for monitors");
    }

}
