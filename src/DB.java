import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private static String url = "jdbc:mysql://localhost:3306/bms";
    private static String user = "root";

    //db password
    private static String password = "Mohd#0426";
    private static Connection connection = null;
    public static Connection connect() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
