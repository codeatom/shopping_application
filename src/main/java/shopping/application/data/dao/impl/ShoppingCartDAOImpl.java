package shopping.application.data.dao.impl;

import shopping.application.data.dao.ShoppingCartDAO;
import shopping.application.data.jdbc.ConnectionProvider;
import shopping.application.model.ShoppingCart;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShoppingCartDAOImpl extends GenericDelete implements ShoppingCartDAO {

    @Override
    public ShoppingCart save(ShoppingCart shoppingCart) {
        ShoppingCart savedObject = null;
        String CREATE = "INSERT INTO shopping_cart (id, last_update, order_status, delivery_address, " +
                "customer_reference) VALUES  (?,?,?,?,?)";

        try(Connection connection = ConnectionProvider.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setInt(1,shoppingCart.getId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(shoppingCart.getLastUpdate()));
            preparedStatement.setString(3, shoppingCart.getOrderStatus());
            preparedStatement.setString(4,shoppingCart.getDeliveryAddress());
            preparedStatement.setString(5, shoppingCart.getCustomerReference());

            int result = preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (result == 1 && resultSet.next()){
                savedObject = new ShoppingCart(resultSet.getInt(1), shoppingCart.getLastUpdate(),
                         shoppingCart.getOrderStatus(), shoppingCart.getDeliveryAddress(), shoppingCart.getCustomerReference());
            }
            resultSet.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return savedObject;
    }

    @Override
    public Optional<ShoppingCart> findById(Integer id) {
        Optional<ShoppingCart> shoppingCart = Optional.empty();
        String SELECT = "SELECT * FROM shopping_cart WHERE id = ?";

        try(Connection connection = ConnectionProvider.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT)){

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                shoppingCart = Optional.of(new ShoppingCart(
                        resultSet.getInt("id"),
                        resultSet.getTimestamp("last_update").toLocalDateTime(),
                        resultSet.getString("order_status"),
                        resultSet.getString("delivery_address"),
                        resultSet.getString("customer_reference")
                ));
            }
            resultSet.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return shoppingCart;
    }

    @Override
    public List<ShoppingCart> findAll() {
        String SELECT = "SELECT * FROM shopping_cart";
        return findShoppingCarts(null, SELECT);
    }

    @Override
    public List<ShoppingCart> findByOrderStatus(String status) {
        String SELECT = "SELECT * FROM shopping_cart WHERE shopping_cart.order_status = ?";
        return findShoppingCarts(status, SELECT);
    }

    @Override
    public List<ShoppingCart> findByReference(String customer) {
        String SELECT = "SELECT * FROM shopping_cart WHERE shopping_cart.customer_reference = ?";
        return findShoppingCarts(customer, SELECT);
    }

    @Override
    public boolean delete(Integer id) {
        String DELETE = "DELETE FROM shopping_cart WHERE id = ?";

        return delete(id, DELETE);
    }

    private List<ShoppingCart> findShoppingCarts(String keyWord, String SELECT) {
        List<ShoppingCart> shoppingCartList = new ArrayList<>();

        try(Connection connection = ConnectionProvider.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT)){

            if(!isNullOrEmpty(keyWord)){
                preparedStatement.setString(1, keyWord);
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                shoppingCartList.add(
                        new ShoppingCart(
                                resultSet.getInt("id"),
                                resultSet.getTimestamp("last_update").toLocalDateTime(),
                                resultSet.getString("order_status"),
                                resultSet.getString("delivery_address"),
                                resultSet.getString("customer_reference")
                        )
                );
            }
            resultSet.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return shoppingCartList;
    }

    private boolean isNullOrEmpty(String s){
        return s == null || s.trim().isEmpty();
    }

}
