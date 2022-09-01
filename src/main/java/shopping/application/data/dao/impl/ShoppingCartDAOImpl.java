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
        ResultSet resultSet = null;
        String CREATE = "INSERT INTO shopping_cart (id, last_update, order_status, delivery_address, " +
                "customer_reference) VALUES  (?,?,?,?,?)";

        try(Connection connection = ConnectionProvider.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1,shoppingCart.getId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(shoppingCart.getLastUpdate()));
            preparedStatement.setString(3, shoppingCart.getOrderStatus());
            preparedStatement.setString(4,shoppingCart.getDeliveryAddress());
            preparedStatement.setString(5, shoppingCart.getCustomerReference());

            int result = preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (result == 1 && resultSet.next()){
                return new ShoppingCart(resultSet.getInt(1), shoppingCart.getLastUpdate(),
                         shoppingCart.getOrderStatus(), shoppingCart.getDeliveryAddress(), shoppingCart.getCustomerReference());
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Optional<ShoppingCart> findById(Integer id) {
        Optional<ShoppingCart> shoppingCart = Optional.empty();
        String SELECT = "SELECT * FROM shopping_cart WHERE id = ?";

        try(Connection connection = ConnectionProvider.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT);

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
        }catch (SQLException e){
            e.printStackTrace();
        }

        return shoppingCart;
    }

    @Override
    public List<ShoppingCart> findAll() {
        List<ShoppingCart> shoppingCartList = new ArrayList<>();
        String SELECT = "SELECT * FROM shopping_cart";

        try(Connection connection = ConnectionProvider.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT);

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
        }catch (SQLException e){
            e.printStackTrace();
        }

        return shoppingCartList;
    }

    @Override
    public boolean delete(Integer id) {
        String DELETE = "DELETE FROM shopping_cart WHERE id = ?";

        return delete(id, DELETE);
    }


    @Override
    public List<ShoppingCart> findByOrderStatus(String status) {
        return null;
    }

    @Override
    public List<ShoppingCart> findByReference(String customer) {
        return null;
    }

}
