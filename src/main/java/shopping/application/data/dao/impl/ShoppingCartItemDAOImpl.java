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
            if(shoppingCartItem.getItem() == null){
                preparedStatement.setNull(4, java.sql.Types.INTEGER);
            }else {
                preparedStatement.setInt(4, shoppingCartItem.getItem().getId());
            }
            if(shoppingCartItem.getCart() == null){
                preparedStatement.setNull(5, java.sql.Types.INTEGER);
            }else {
                preparedStatement.setInt(5, shoppingCartItem.getCart().getId());
            }

            int result = preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();


          if (result == 1 && resultSet.next()){
              Product product = null;
              ShoppingCart shoppingCart = null;

              if(shoppingCartItem.getItem() != null){
                  product = productDAO.findById(shoppingCartItem.getItem().getId()).get();
              }
              if(shoppingCartItem.getCart() != null){
                  shoppingCart = shoppingCartDAO.findById(shoppingCartItem.getCart().getId()).get();
              }

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
        String SELECT = "SELECT * FROM shopping_cart_item WHERE id = ?";
        return  findShoppingCartItems(id, SELECT).size() > 0 ? Optional.of(findShoppingCartItems(id, SELECT).get(0)) : Optional.empty();
    }

    @Override
    public List<ShoppingCartItem> findAll() {
        String SELECT = "SELECT * FROM shopping_cart_item";
        return findShoppingCartItems(0, SELECT);
    }

    @Override
    public List<ShoppingCartItem> findByCartId(Integer cartId) {
        String SELECT = "SELECT * FROM shopping_cart_item WHERE shopping_cart_id = ?";
        return findShoppingCartItems(cartId, SELECT);
    }

    @Override
    public List<ShoppingCartItem> findByProductId(Integer productId) {
        String SELECT = "SELECT * FROM shopping_cart_item WHERE product_id = ?";
        return findShoppingCartItems(productId, SELECT);
    }

    @Override
    public boolean delete(Integer id) {
        String DELETE = "DELETE FROM shopping_cart_item WHERE id = ?";
        return delete(id, DELETE);
    }

    private List<ShoppingCartItem> findShoppingCartItems(Integer id, String SELECT) {
        List<ShoppingCartItem> shoppingCartItemList = new ArrayList<>();

        try(Connection connection = ConnectionProvider.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT)){

            if(id > 0){
                preparedStatement.setInt(1, id);
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Product product = null;
                ShoppingCart shoppingCart = null;

                if(resultSet.getInt("product_id") != 0){
                    product = productDAO.findById(resultSet.getInt("product_id")).get();
                }
                if(resultSet.getInt("shopping_cart_id") != 0){
                    shoppingCart = shoppingCartDAO.findById(resultSet.getInt("shopping_cart_id")).get();
                }

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

}
