package qf.jdbc.drivermanager;


import com.mysql.cj.jdbc.Driver;
import qf.jdbc.City;
import qf.jdbc.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DriverManagerDemo {

  public static void main(String[] args) {
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    try {
      Class.forName(Driver.class.getName());
      connection = DatabaseConfiguration.getConnection();
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
      Utils.close(resultSet, statement, connection);
    }
  }
}
