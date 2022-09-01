package shopping.application.data.dao;

import shopping.application.model.ShoppingCartItem;

import java.util.List;

public interface ShoppingCartItemDAO extends GenericCrud<ShoppingCartItem, Integer>{
     List<ShoppingCartItem> findByCartId(Integer cartId);

    List<ShoppingCartItem> findByProductId(Integer productId);
}
