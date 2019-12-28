package qf.jdbc.datasource.hikari;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;
import qf.jdbc.City;
import qf.jdbc.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static qf.jdbc.DatabaseConfiguration.*;

public class HikariCPDatasourceDemo {
  public static void main(String[] args) throws SQLException {
    HikariDataSource dataSource = new HikariDataSource(); // 使用无参构造方法不会立马创建连接，而是在获取连接时才创建
    dataSource.setDriverClassName(DRIVER);
    dataSource.setJdbcUrl(Utils.connectionUrl(HOST, PORT, DATABASE, CONNECTION_PARAMS));
    dataSource.setUsername(USER);
    dataSource.setPassword(PASSWORD);

    Connection connection = dataSource.getConnection();
    HikariPool hikariPoolMXBean = (HikariPool) dataSource.getHikariPoolMXBean(); // 在没有创建连接之前，连接池为null
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("select * from city");

    System.out.println(
      String.format(
        "connection: %d, Max: %d, MinIdle: %s, Active: %d, Idle: %d, IdleTimeout: %d, connectTimeout: %d",
        hikariPoolMXBean.getTotalConnections(), // 当前连接数1
        dataSource.getMaximumPoolSize(),  // 10
        dataSource.getMinimumIdle(), // 10
        hikariPoolMXBean.getActiveConnections(),  //当前使用的连接数 1
        hikariPoolMXBean.getIdleConnections(), // 闲置连接数
        dataSource.getIdleTimeout(),  // 闲置连接存活时间，默认10min(600,000ms)
        dataSource.getConnectionTimeout() // 等待连接超时时间，默认30s(30,000ms)
      )
    );

    List<City> cities = new ArrayList<>();
    while (resultSet.next()) {
      cities.add(City.build(resultSet));
    }
    System.out.println(cities);

    connection.close();

    System.out.println(
      String.format(
        "connection: %d, Max: %d, MinIdle: %s, Active: %d, Idle: %d, IdleTimeout: %d, connectTimeout: %d",
        hikariPoolMXBean.getTotalConnections(),
        dataSource.getMaximumPoolSize(),
        dataSource.getMinimumIdle(),
        hikariPoolMXBean.getActiveConnections(),
        hikariPoolMXBean.getIdleConnections(),
        dataSource.getIdleTimeout(),
        dataSource.getConnectionTimeout()
      )
    );

    dataSource.close();
  }
}
