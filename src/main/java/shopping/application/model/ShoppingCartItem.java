package shopping.application.model;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCartItem that = (ShoppingCartItem) o;
        return amount == that.amount && Double.compare(that.totalPrice, totalPrice) == 0 && Objects.equals(id, that.id) && Objects.equals(item, that.item) && Objects.equals(cart, that.cart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, totalPrice, item, cart);
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
