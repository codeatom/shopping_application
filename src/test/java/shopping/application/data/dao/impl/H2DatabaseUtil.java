package shopping.application.data.dao.impl;

import shopping.application.data.jdbc.ConnectionProvider;
import shopping.application.data.jdbc.DatabaseCredentials;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

public class H2DatabaseUtil {

    private final String sql;

    private static H2DatabaseUtil INSTANCE;

    public H2DatabaseUtil(String sql) {
        this.sql = sql;
    }

    public static H2DatabaseUtil getInstance(){
        return INSTANCE;
    }


    static {
        DatabaseCredentials.initialize("database/h2.properties");

        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get("database/shopping_practice_script.sql"))){

            String sqlString = bufferedReader.lines().collect(Collectors.joining());
            INSTANCE = new H2DatabaseUtil(sqlString);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void createTables(){
        try(Connection connection = ConnectionProvider.getConnection();
            Statement statement = connection.createStatement()){

            statement.execute(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}