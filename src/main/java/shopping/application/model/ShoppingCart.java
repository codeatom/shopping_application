package shopping.application.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class ShoppingCart {
    private Integer id;
    private LocalDateTime lastUpdate;
    private String orderStatus;
    private String deliveryAddress;
    private String customerReference;
    private boolean paymentApproved;

    public ShoppingCart() {
    }

    public ShoppingCart(Integer id, LocalDateTime lastUpdate, String orderStatus,
                        String deliveryAddress, String customerReference, boolean paymentApproved) {
        this(id, lastUpdate, orderStatus, deliveryAddress, customerReference);
        this.paymentApproved = paymentApproved;
    }

    public ShoppingCart(Integer id, LocalDateTime lastUpdate, String orderStatus,
                        String deliveryAddress, String customerReference) {
        this.id = id;
        this.lastUpdate = lastUpdate;
        this.orderStatus = orderStatus;
        this.deliveryAddress = deliveryAddress;
        this.customerReference = customerReference;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getCustomerReference() {
        return customerReference;
    }

    public boolean isPaymentApproved() {
        return paymentApproved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCart that = (ShoppingCart) o;
        return paymentApproved == that.paymentApproved && Objects.equals(id, that.id) && Objects.equals(lastUpdate, that.lastUpdate) && Objects.equals(orderStatus, that.orderStatus) && Objects.equals(deliveryAddress, that.deliveryAddress) && Objects.equals(customerReference, that.customerReference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastUpdate, orderStatus, deliveryAddress, customerReference, paymentApproved);
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "id=" + id +
                ", lastUpdate=" + lastUpdate +
                ", orderStatus='" + orderStatus + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", customerReference='" + customerReference + '\'' +
                ", paymentApproved=" + paymentApproved +
                '}';
    }
}
