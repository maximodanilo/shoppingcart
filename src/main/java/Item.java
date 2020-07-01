import java.math.BigDecimal;
import java.util.Objects;

public class Item {

    private String name;
    private BigDecimal value;
    private boolean isActive;
    private String sku;

    public Item(String name, String sku){
        this.name = name;
        this.sku = sku;
    }

    public String getSku(){
        return this.sku;
    }

    public String getName() {
        return name;
    }

    public void setValue(BigDecimal v) {
        this.value = v;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void activate() throws IllegalStateException{
        if( this.value == null)
            throw new IllegalStateException("Please set a value before activating an item.");

        this.isActive = true;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void deactivate() {
        this.isActive = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return sku.equals(item.sku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sku);
    }
}
