package shopping.application;


import shopping.application.data.dao.impl.ProductDAOImpl;
import shopping.application.data.dao.impl.ShoppingCartDAOImpl;
import shopping.application.data.dao.impl.ShoppingCartItemDAOImpl;
import shopping.application.data.jdbc.DatabaseCredentials;
import shopping.application.model.ShoppingCartItem;

public class Main {
    public static void main(String[] args) {

            DatabaseCredentials.initialize("database/mysql.properties");

            ProductDAOImpl productDAOImpl = new ProductDAOImpl();
            ShoppingCartDAOImpl shoppingCartDAImpl = new ShoppingCartDAOImpl();
            ShoppingCartItemDAOImpl shoppingCartItemDAImpl = new ShoppingCartItemDAOImpl();

    }
}