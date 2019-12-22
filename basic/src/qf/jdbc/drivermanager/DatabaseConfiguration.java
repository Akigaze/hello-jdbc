package qf.jdbc.drivermanager;

import com.mysql.cj.jdbc.Driver;
import qf.jdbc.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseConfiguration {
  private static final String HOST = "localhost";
  private static final int PORT = 3306;
  private static final String DATABASE = "test";
  private static final String USER = "akigaze";
  private static final String PASSWORD = "akigaze";

  private static final Map<String, Object> CONNECTION_PARAMS = new HashMap<>();

  static {
    CONNECTION_PARAMS.put("useUnicode", true);
    CONNECTION_PARAMS.put("characterEncoding", "UTF-8");
    CONNECTION_PARAMS.put("serverTimezone", "UTC"); // 连接时区乱码问题，这个是必须的
    CONNECTION_PARAMS.put("useSSL", false);
  }

  static Connection getConnection() throws ClassNotFoundException, SQLException {
    Class.forName(Driver.class.getName());
    Connection connection = DriverManager.getConnection(Utils.connectionUrl(HOST, PORT, DATABASE, CONNECTION_PARAMS), USER, PASSWORD);
    System.out.println("retrieve connection");
    return connection;
  }
}
