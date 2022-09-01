package shopping.application.data.dao;

import shopping.application.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartDAO extends GenericCrud<ShoppingCart, Integer>{
    List<ShoppingCart> findByOrderStatus(String status);

    List<ShoppingCart> findByReference(String customer);
}
