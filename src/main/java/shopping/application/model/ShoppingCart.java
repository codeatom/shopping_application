package shopping.application.model;

import java.time.LocalDateTime;

public class ShoppingCart {
    private Integer id;
    private LocalDateTime lastUpdate;
    private String orderStatus;
    private String deliveryAddress;
    private String customerReference;
    private boolean paymentApproved;

    public ShoppingCart(int id, long lastUpdate, String orderStatus, String deliveryAddress, String customerReference) {
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
