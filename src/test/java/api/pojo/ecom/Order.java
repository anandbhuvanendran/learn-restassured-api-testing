package api.pojo.ecom;

import java.util.List;

public class Order {
    public List<OrderDetails> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDetails> orders) {
        this.orders = orders;
    }

    private List<OrderDetails> orders;
}
