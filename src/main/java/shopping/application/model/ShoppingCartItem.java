package shopping.application.model;

public class ShoppingCartItem {
    private Integer id;
    private int amount;
    private double totalPrice;
    private Product item;
    private ShoppingCart cart;

    public ShoppingCartItem() {
    }

    public ShoppingCartItem(Integer id, int amount, double totalPrice, Product item, ShoppingCart cart) {
        this.id = id;
        this.amount = amount;
        this.totalPrice = totalPrice;
        this.item = item;
        this.cart = cart;
    }

    public Integer getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Product getItem() {
        return item;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    @Override
    public String toString() {
        return "ShoppingCartItem{" +
                "id=" + id +
                ", amount=" + amount +
                ", totalPrice=" + totalPrice +
                ", item=" + item +
                ", cart=" + cart +
                '}';
    }
}
