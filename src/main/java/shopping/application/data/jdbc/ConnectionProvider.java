package shopping.application.data.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DatabaseCredentials.getINSTANCE().getURL(),
                DatabaseCredentials.getINSTANCE().getUSER(),
                DatabaseCredentials.getINSTANCE().getPASSWORD());
    }

}
