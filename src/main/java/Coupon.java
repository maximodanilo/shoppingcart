public class Coupon {
    private String code;
    private String name;
    private String sku;
    private float discountPercentage;

    public Coupon(String name, String code, int discountPercentage) {
        this.name = name;
        this.discountPercentage = discountPercentage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public float getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(float discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getCode() {
        return code;
    }
}
