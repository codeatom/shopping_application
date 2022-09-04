package shopping.application.data.dao.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import shopping.application.model.ShoppingCart;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartDAOImplTest {

    private final ShoppingCartDAOImpl shoppingCartDAOImpl = new ShoppingCartDAOImpl();
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
    void shouldSaveShoppingCartToDatabase() {
        //Arrange
        ShoppingCart expected = new ShoppingCart(0, LocalDateTime.now(), "status", "address", "reference");

        //Act
        ShoppingCart actual= shoppingCartDAOImpl.save(expected);

        //Assert
        assertNotNull(actual);
        assertEquals(expected.hashCode(), actual.hashCode());
        assertTrue(expected.equals(actual) && actual.equals(expected));
    }

}