package shopping.application.data.dao.impl;

import shopping.application.data.jdbc.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class GenericDelete {

    public boolean delete(Integer id, String DELETE) {
        try(Connection connection = ConnectionProvider.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE)){

            preparedStatement.setInt(1, id);

            int result = preparedStatement.executeUpdate();

            if (result == 1){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

}
