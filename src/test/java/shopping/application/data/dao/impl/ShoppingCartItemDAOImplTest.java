package shopping.application.data.dao.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import shopping.application.model.Product;
import shopping.application.model.ShoppingCart;
import shopping.application.model.ShoppingCartItem;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartItemDAOImplTest {

    private final ShoppingCartItemDAOImpl shoppingCartItemDAOImpl = new ShoppingCartItemDAOImpl();
    private static H2DatabaseUtil h2DatabaseUtil;

    @BeforeAll
    public static void beforeAll(){
        h2DatabaseUtil = H2DatabaseUtil.getInstance();
    }

    @BeforeEach
    public void beforeEach(){
        h2DatabaseUtil.createTables();
    }


    @org.junit.jupiter.api.Test
    void shouldSaveShoppingCartItemToDatabase() {
        //Arrange
        Product product = new Product(0, "product_1", 10);
        ShoppingCart shoppingCart = new ShoppingCart(0, LocalDateTime.parse("2022-09-04T10:11:30"), "status", "address", "reference");

        new ProductDAOImpl().save(product);
        new ShoppingCartDAOImpl().save(shoppingCart);

        ShoppingCartItem expected = new ShoppingCartItem(0, 1, 10, product, shoppingCart);

        //Act
        ShoppingCartItem actual = shoppingCartItemDAOImpl.save(expected);

        //Assert
        assertNotNull(actual);
        assertEquals(expected.hashCode(), actual.hashCode());
        assertTrue(expected.equals(actual) && actual.equals(expected));

        assertEquals(expected.getItem().hashCode(), actual.getItem().hashCode());
        assertTrue(expected.getItem().equals(actual.getItem()) && actual.getItem().equals(expected.getItem()));

        assertEquals(expected.getCart().hashCode(), actual.getCart().hashCode());
        assertTrue(expected.getCart().equals(actual.getCart()) && actual.getCart().equals(expected.getCart()));
    }
}