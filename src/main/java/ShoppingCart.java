import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    List<ItemOrder> itemOrderList;

    public void addItem(Item item){
        if(itemOrderList == null)
            itemOrderList = new ArrayList<>();

        getItemOrder(item);
    }

    private void getItemOrder(Item item) {
        for(ItemOrder itemOr : itemOrderList) {
            if (item.equals(itemOr.getItem())) {
                itemOr.addItem(item);
                return;
            }
        }

        ItemOrder itemOrder = new ItemOrder(item, 1);
        itemOrderList.add(itemOrder);
    }

    public BigDecimal calculateSubTotal(){
        BigDecimal subtotal = new BigDecimal(0);
        for(ItemOrder itemOrder : itemOrderList){
            subtotal = subtotal.add(itemOrder.getTotal());
        }
        return subtotal;
    }

    public BigDecimal addCoupon(Coupon coupon){
        for(ItemOrder itemOr : itemOrderList) {
            try{
                itemOr.addCoupon(coupon);
            }catch(IllegalArgumentException illegalArgumentException){
                System.out.println("Coupon not applicable to SKU " + itemOr.getItem().getSku());
            }
        }

        return calculateSubTotal();
    }
}

