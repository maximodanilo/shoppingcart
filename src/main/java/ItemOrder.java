import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ItemOrder {

    private Item item;
    private int quantity;
    private BigDecimal total;
    private List<Coupon> coupons;

    public ItemOrder(Item item, int quantity) {
        this.item     = item;
        this.quantity = quantity;
        total = new BigDecimal(0);
        calculateTotal();
    }

    private void calculateTotal(){
        this.total = item.getValue().multiply(new BigDecimal(quantity));

        if(coupons == null) return;

        for (Coupon coupon: coupons) {
            calculateDiscount(coupon);
        }
    }

    public BigDecimal getTotal(){
        return this.total;
    }

    public void addItem(Item item){
        validateSKU(item);
        this.quantity++;
        calculateTotal();
    }

    private void validateSKU(Item item) {
        if(!this.getItem().equals(item))
            throw new IllegalArgumentException("This order is for SKU " + this.item.getSku()
                    + " but got SKU " + item.getSku());

    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addCoupon(Coupon coupon) throws IllegalArgumentException{
        if(coupons == null)
            coupons = new ArrayList<>();

        coupons.add(coupon);
        calculateDiscount(coupon);
    }

    private void calculateDiscount(Coupon coupon) throws IllegalArgumentException{
        BigDecimal discountAmount = calculateDiscountAmount(coupon);
        validateCouponSKU(coupon);
        applyDiscount(discountAmount.setScale(2, RoundingMode.HALF_EVEN));
    }

    private void applyDiscount(BigDecimal discountAmount) {
        this.total = this.total.subtract(discountAmount);
    }

    private void validateCouponSKU(Coupon coupon) {
        if(coupon.getSku() != null && this.getItem().getSku() != coupon.getSku())
            throw new IllegalArgumentException("This coupon is not valid for " + item.getName());
    }

    private BigDecimal calculateDiscountAmount(Coupon coupon) {
        BigDecimal discPercent = new BigDecimal(coupon.getDiscountPercentage()/100);
        discPercent.setScale(2, RoundingMode.HALF_DOWN);
        return this.total.multiply(discPercent);
    }
}
