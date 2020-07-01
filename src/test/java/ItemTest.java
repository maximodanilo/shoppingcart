import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    Item item;

    @BeforeEach
    void setUp(){
        item = new Item("Product", "123456");
        item.setValue(new BigDecimal("20"));
    }

    @DisplayName("Create Item test")
    @Test
    void createItem(){
        assertTrue("Product".equals(item.getName()), "Item name should be Product");
        assertEquals(item.getValue().compareTo(new BigDecimal("20")), 0, "Item value should be 20");
    }

    @DisplayName("Activate item")
    @Test
    void activateItem(){
        item.setValue(null);
        assertThrows(IllegalStateException.class, () -> {
            item.activate();
        }, "Should throw exception since value is null");

        item.setValue(new BigDecimal(10));
        item.activate();
        assertTrue(item.isActive(), "Should be activated since there is value and activation was required.");
    }

    @Test
    void isSKUSet(){
        assertEquals(item.getSku(), "123456");
    }
}