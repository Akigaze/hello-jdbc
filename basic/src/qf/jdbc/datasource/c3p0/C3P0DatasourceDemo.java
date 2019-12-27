package qf.jdbc.datasource.c3p0;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import qf.jdbc.City;
import qf.jdbc.Utils;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static qf.jdbc.DatabaseConfiguration.*;

public class C3P0DatasourceDemo {
  public static void main(String[] args) {
    ComboPooledDataSource dataSource = new ComboPooledDataSource();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    try {
      dataSource.setDriverClass(DRIVER);
      dataSource.setJdbcUrl(Utils.connectionUrl(HOST, PORT, DATABASE, CONNECTION_PARAMS));
      dataSource.setUser(USER);
      dataSource.setPassword(PASSWORD);

      System.out.println("max pool size: " + dataSource.getMinPoolSize());
      connection = dataSource.getConnection();

      statement = connection.createStatement();
      resultSet = statement.executeQuery("select * from city");

      List<City> cities = new ArrayList<>();
      while (resultSet.next()) {
        cities.add(City.build(resultSet));
      }
      System.out.println(cities);
    } catch (PropertyVetoException | SQLException e) {
      e.printStackTrace();
    } finally {
      Utils.close(resultSet, statement, connection);
    }
    dataSource.close();
  }
}
