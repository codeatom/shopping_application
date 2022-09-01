package shopping.application.data.jdbc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class DatabaseCredentials {
    private String URL;
    private String USER;
    private String PASSWORD;
    private static DatabaseCredentials INSTANCE;

    private DatabaseCredentials() {
    }

    public String getURL() {
        return URL;
    }

    public String getUSER() {
        return USER;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public static DatabaseCredentials getINSTANCE() {
        return INSTANCE;
    }

    private void setProperties(String pathString){
        Path path = Paths.get(pathString);
        Properties properties = new Properties();

        try{
            properties.load(Files.newBufferedReader(path));

            this.USER = properties.getProperty("username");
            this.PASSWORD = properties.getProperty("password");
            this.URL = properties.getProperty("url");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void initialize(String url){
        if(INSTANCE == null){
            INSTANCE = new DatabaseCredentials();
        }

        INSTANCE.setProperties(url);
    }
}
