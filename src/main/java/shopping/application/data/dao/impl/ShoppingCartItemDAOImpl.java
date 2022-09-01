package shopping.application.data.dao.impl;

import shopping.application.data.dao.ProductDAO;
import shopping.application.data.dao.ShoppingCartDAO;
import shopping.application.data.dao.ShoppingCartItemDAO;
import shopping.application.data.jdbc.ConnectionProvider;
import shopping.application.model.Product;
import shopping.application.model.ShoppingCart;
import shopping.application.model.ShoppingCartItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShoppingCartItemDAOImpl extends GenericDelete implements ShoppingCartItemDAO {

    ProductDAO productDAO = new ProductDAOImpl();
    ShoppingCartDAO shoppingCartDAO = new ShoppingCartDAOImpl();

    @Override
    public ShoppingCartItem save(ShoppingCartItem shoppingCartItem) {
        ShoppingCartItem savedObject = null;
        String CREATE = "INSERT INTO shopping_cart_item (id, amount, total_price, product_id, shopping_cart_id) VALUES  (?,?,?,?,?)";

        try(Connection connection = ConnectionProvider.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setInt(1,shoppingCartItem.getId());
            preparedStatement.setInt(2, shoppingCartItem.getAmount());
            preparedStatement.setDouble(3, shoppingCartItem.getTotalPrice());
            preparedStatement.setInt(4, shoppingCartItem.getItem().getId());
            preparedStatement.setInt(5, shoppingCartItem.getCart().getId());

            int result = preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();


          if (result == 1 && resultSet.next()){
              Product product = productDAO.findById(shoppingCartItem.getItem().getId()).get();
              ShoppingCart shoppingCart = shoppingCartDAO.findById(shoppingCartItem.getCart().getId()).get();

              savedObject = new ShoppingCartItem(resultSet.getInt(1), shoppingCartItem.getAmount(),
                      shoppingCartItem.getTotalPrice(), product, shoppingCart);
          }
          resultSet.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return savedObject;
    }

    @Override
    public Optional<ShoppingCartItem> findById(Integer id) {
        Optional<ShoppingCartItem> shoppingCartItem = Optional.empty();
        String SELECT = "SELECT * FROM shopping_cart_item WHERE id = ?";

        try(Connection connection = ConnectionProvider.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT)){

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Product product = productDAO.findById(resultSet.getInt("product_id")).get();
                ShoppingCart shoppingCart = shoppingCartDAO.findById(resultSet.getInt("shopping_cart_id")).get();

                shoppingCartItem = Optional.of(new ShoppingCartItem(
                        resultSet.getInt("id"),
                        resultSet.getInt("amount"),
                        resultSet.getDouble("total_price"),
                        product,
                        shoppingCart
                ));
            }
            resultSet.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return shoppingCartItem;
    }

    @Override
    public List<ShoppingCartItem> findAll() {
        List<ShoppingCartItem> shoppingCartItemList = new ArrayList<>();
        String SELECT = "SELECT * FROM shopping_cart_item";

        try(Connection connection = ConnectionProvider.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT)){

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Product product = productDAO.findById(resultSet.getInt("product_id")).get();
                ShoppingCart shoppingCart = shoppingCartDAO.findById(resultSet.getInt("shopping_cart_id")).get();

                shoppingCartItemList.add(
                        new ShoppingCartItem(
                                resultSet.getInt("id"),
                                resultSet.getInt("amount"),
                                resultSet.getDouble("total_price"),
                                product,
                                shoppingCart
                        )
                );
            }
            resultSet.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return shoppingCartItemList;
    }

    @Override
    public boolean delete(Integer id) {
        String DELETE = "DELETE FROM shopping_cart_item WHERE id = ?";

        return delete(id, DELETE);
    }

    @Override
    public List<ShoppingCartItem> findByCartId(Integer cartId) {
        return null;
    }

    @Override
    public List<ShoppingCartItem> findByProductId(Integer productId) {
        return null;
    }

}
