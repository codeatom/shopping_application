package shopping.application.data.dao.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import shopping.application.model.Product;

import static org.junit.jupiter.api.Assertions.*;

class ProductDAOImplTest {

    private final ProductDAOImpl productDAOImpl = new ProductDAOImpl();
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
    void shouldSaveProductToDatabase() {
        //Arrange
        Product expected = new Product(0, "product_1", 10);

        //Act
        Product actual= productDAOImpl.save(expected);

        //Assert
        assertNotNull(actual);
        assertEquals(expected.hashCode(), actual.hashCode());
        assertTrue(expected.equals(actual) && actual.equals(expected));
    }

}