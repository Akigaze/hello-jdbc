package qf.jdbc.drivermanager;


import com.mysql.cj.jdbc.Driver;
import qf.jdbc.City;
import qf.jdbc.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DriverManagerDemo {

  public static void main(String[] args) throws SQLException {
    String host = "localhost";
    int port = 3306;
    String database = "test";
    String user = "akigaze";
    String password = "akigaze";

    Map<String, Object> connectionParams = new HashMap<>();
    connectionParams.put("useUnicode", true);
    connectionParams.put("characterEncoding", "UTF-8");
    connectionParams.put("serverTimezone", "UTC"); // 连接时区乱码问题，这个是必须的
    connectionParams.put("useSSL", false);

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    try {
      Class.forName(Driver.class.getName());
      connection = DriverManager.getConnection(Utils.connectionUrl(host, port, database, connectionParams), user, password);
      statement = connection.createStatement();
      resultSet = statement.executeQuery("select * from city");
      List<City> cities = new ArrayList<>();
      while (resultSet.next()) {
        City city = new City(
          resultSet.getInt("id"),
          resultSet.getString("code"),
          resultSet.getString("name"),
          resultSet.getString("country"),
          resultSet.getInt("population")
        );
        cities.add(city);
      }
      System.out.println(cities);
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    } finally {
      if (resultSet != null) {
        resultSet.close();
      }
      if (statement != null) {
        statement.close();
      }
      if (connection != null) {
        connection.close();
      }
    }
  }
}
