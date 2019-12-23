package qf.jdbc.drivermanager;


import qf.jdbc.City;
import qf.jdbc.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class DriverManagerPreparedDemo {

  public static void main(String[] args) {
    City hongKongCity = new City("HK", "HongKong", "China", 500000);
    insertCity(hongKongCity);

    System.out.println("-------------------------------");

    List<City> cities = Arrays.asList(
      new City("BJS", "Beijing", "China", 90099),
      new City("PAR", "Pairs", "French", 10090),
      new City("GUM", "Guam", "USA", 3000)
    );
    insertCites(cities);

    System.out.println("-------------------------------");

    deleteCity(hongKongCity);

    System.out.println("-------------------------------");

    deleteCities(cities);

    System.out.println("-------------------------------");

    City cityOfUSA = findFirstCityOfCountry("USA");
    if (cityOfUSA != null) {
      cityOfUSA.setCountry("China");
      updateCity(cityOfUSA);
    }
  }

  private static void insertCity(City city) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      connection = JDBCUtils.getConnection();
      String insertCitySql = "insert city(code, name, country, population) value(?, ?, ?, ?)";
      preparedStatement = connection.prepareStatement(insertCitySql);
      preparedStatement.setString(1, city.getCode());
      preparedStatement.setString(2, city.getName());
      preparedStatement.setString(3, city.getCountry());
      preparedStatement.setInt(4, city.getPopulation());

      int insertNumberOfRow = preparedStatement.executeUpdate();
      System.out.println("insert " + insertNumberOfRow + " rows");
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    } finally {
      Utils.close(preparedStatement, connection);
    }
  }

  private static void insertCites(List<City> cities) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      connection = JDBCUtils.getConnection();
      String insertCitySql = "insert city(code, name, country, population) value(?, ?, ?, ?)";
      preparedStatement = connection.prepareStatement(insertCitySql);
      connection.setAutoCommit(false); // 默认是自动commit的

      for (City city : cities) {
        preparedStatement.setString(1, city.getCode());
        preparedStatement.setString(2, city.getName());
        preparedStatement.setString(3, city.getCountry());
        preparedStatement.setInt(4, city.getPopulation());
        preparedStatement.addBatch(); //将预设的值加入批次中，这样执行 executeBatch 批量执行才能生效
      }

      int[] insertNumberOfRowBatch = preparedStatement.executeBatch(); // 执行批量操作
      int impactedRows = Arrays.stream(insertNumberOfRowBatch).sum();
      System.out.println("total insert " + impactedRows + " rows");
      connection.commit();
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
      Utils.rollback(connection);
    } finally {
      Utils.close(preparedStatement, connection);
    }
  }

  private static void deleteCity(City city) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      connection = JDBCUtils.getConnection();
      String deleteCitySql = "delete from city where code = ? and country = ?";
      preparedStatement = connection.prepareStatement(deleteCitySql);
      connection.setAutoCommit(false);

      preparedStatement.setString(1, city.getCode());
      preparedStatement.setString(2, city.getCountry());

      int deleteNumberOfRow = preparedStatement.executeUpdate();

      System.out.println("delete " + deleteNumberOfRow + " rows");

      connection.commit();
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
      Utils.rollback(connection);
    } finally {
      Utils.close(preparedStatement, connection);
    }
  }

  private static void deleteCities(List<City> cities) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      connection = JDBCUtils.getConnection();
      String deleteCitySql = "delete from city where code = ? and country = ?";
      preparedStatement = connection.prepareStatement(deleteCitySql);
      connection.setAutoCommit(false);

      for (City city : cities) {
        preparedStatement.setString(1, city.getCode());
        preparedStatement.setString(2, city.getCountry());
        preparedStatement.addBatch();
      }

      int[] deleteNumberOfRowBatch = preparedStatement.executeBatch();
      int deleteNumberOfRow = Arrays.stream(deleteNumberOfRowBatch).sum();
      System.out.println("delete " + deleteNumberOfRow + " rows");

      connection.commit();
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
      Utils.rollback(connection);
    } finally {
      Utils.close(preparedStatement, connection);
    }
  }

  private static void updateCity(City city) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      connection = JDBCUtils.getConnection();
      String updateCitySql = "update city set code=?, name=?, country=?, population=? where id=?";
      preparedStatement = connection.prepareStatement(updateCitySql);
      connection.setAutoCommit(false);

      preparedStatement.setString(1, city.getCode());
      preparedStatement.setString(2, city.getName());
      preparedStatement.setString(3, city.getCountry());
      preparedStatement.setInt(4, city.getPopulation());
      preparedStatement.setInt(5, city.getId());

      int updateNumberOfRow = preparedStatement.executeUpdate();

      System.out.println("update " + updateNumberOfRow + " rows");

      connection.commit();
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
      Utils.rollback(connection);
    } finally {
      Utils.close(preparedStatement, connection);
    }
  }

  private static City findFirstCityOfCountry(String country) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
      connection = JDBCUtils.getConnection();
      String queryCitySql = "select * from city where country=? limit 1";
      preparedStatement = connection.prepareStatement(queryCitySql);

      preparedStatement.setString(1, country);

      resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        City city = new City(
          resultSet.getInt("id"),
          resultSet.getString("code"),
          resultSet.getString("name"),
          resultSet.getString("country"),
          resultSet.getInt("population")
        );
        System.out.println("find city: " + city);
        return city;
      }
      System.out.println("city not found");
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      Utils.close(resultSet, preparedStatement, connection);
    }
    return null;
  }
}
