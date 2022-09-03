package shopping.application.data.dao.impl;

import shopping.application.data.dao.ProductDAO;
import shopping.application.data.jdbc.ConnectionProvider;
import shopping.application.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDAOImpl extends GenericDelete implements ProductDAO {

    @Override
    public Product save(Product product) {
        Product savedObject = null;
        String CREATE = "INSERT INTO product (id, name, price) VALUES  (?,?,?)";

        try(Connection connection = ConnectionProvider.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setInt(1,product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getPrice());

            int result = preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (result == 1 && resultSet.next()){
                savedObject = new Product(resultSet.getInt(1), product.getName(), product.getPrice());
            }
            resultSet.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return savedObject;
    }

    @Override
    public Optional<Product> findById(Integer id) {
        Optional<Product> product = Optional.empty();
        String SELECT = "SELECT * FROM product WHERE id = ?";

        try(Connection connection = ConnectionProvider.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT)){

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                product = Optional.of(new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price")
                ));
            }
            resultSet.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return product;
    }

    @Override
    public List<Product> findAll() {
        List<Product> productList = new ArrayList<>();
        String SELECT = "SELECT * FROM product";

        try(Connection connection = ConnectionProvider.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT)){

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                productList.add(
                        new Product(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getDouble("price")
                        )
                );
            }
            resultSet.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return productList;
    }

    @Override
    public List<Product> findByName(String name) {
        List<Product> productList = new ArrayList<>();
        String SELECT = "SELECT * FROM product WHERE product.name LIKE ? OR product.name LIKE ?";

        try(Connection connection = ConnectionProvider.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT)){

            preparedStatement.setString(1, "%" + name);
            preparedStatement.setString(2, name + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                productList.add(
                        new Product(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getDouble("price")
                        )
                );
            }
            resultSet.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return productList;
    }

    @Override
    public List<Product> findByPriceBetween(double low, double high) {
        List<Product> productList = new ArrayList<>();
        String SELECT = "SELECT * FROM product WHERE product.price BETWEEN ? AND ?";

        try(Connection connection = ConnectionProvider.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT)){

            preparedStatement.setDouble(1, low);
            preparedStatement.setDouble(2, high);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                productList.add(
                        new Product(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getDouble("price")
                        )
                );
            }
            resultSet.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return productList;
    }

    @Override
    public boolean delete(Integer id) {
        String DELETE = "DELETE FROM product WHERE id = ?";

        return delete(id, DELETE);
    }

}
