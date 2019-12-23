package qf.jdbc;

import com.mysql.cj.jdbc.Driver;

import java.util.HashMap;
import java.util.Map;

public class DatabaseConfiguration {
  public static final String DRIVER = Driver.class.getName();
  public static final String HOST = "localhost";
  public static final int PORT = 3306;
  public static final String DATABASE = "test";
  public static final String USER = "akigaze";
  public static final String PASSWORD = "akigaze";

  public static final Map<String, Object> CONNECTION_PARAMS = new HashMap<>();

  static {
    CONNECTION_PARAMS.put("useUnicode", true);
    CONNECTION_PARAMS.put("characterEncoding", "UTF-8");
    CONNECTION_PARAMS.put("serverTimezone", "UTC"); // 连接时区乱码问题，这个是必须的
    CONNECTION_PARAMS.put("useSSL", false);
  }
}
