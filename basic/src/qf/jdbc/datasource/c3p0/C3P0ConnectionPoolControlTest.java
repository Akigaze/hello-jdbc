package qf.jdbc.datasource.c3p0;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import qf.jdbc.Utils;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static qf.jdbc.DatabaseConfiguration.*;

public class C3P0ConnectionPoolControlTest {
  public static void main(String[] args) {
    ComboPooledDataSource dataSource = new ComboPooledDataSource();

    try {
      dataSource.setDriverClass(DRIVER);
      dataSource.setJdbcUrl(Utils.connectionUrl(HOST, PORT, DATABASE, CONNECTION_PARAMS));
      dataSource.setUser(USER);
      dataSource.setPassword(PASSWORD);

      dataSource.setMaxIdleTime(1);

      System.out.println(
        String.format(
          "connection: %d, Max: %d, Min: %d, Init: %d, Idle: %d, Increment: %d, IdleConnectionTestPeriod: %d, IdelTime: %s, Timeout: %s",
          dataSource.getNumConnections(), // 0
          dataSource.getMaxPoolSize(),  // 15
          dataSource.getMinPoolSize(), // 3
          dataSource.getInitialPoolSize(), // 3
          dataSource.getNumIdleConnections(),  //0
          dataSource.getAcquireIncrement(),  //没有可用连接时，一次性多创建的连接数， 默认3个
          dataSource.getIdleConnectionTestPeriod(),// 检测闲置连接的周期，默认0s
          dataSource.getMaxIdleTime(),  //闲置连接的最大存活时间，默认0s
          dataSource.getCheckoutTimeout()  // 等待获取连接的超时时间，默认0s无限等待
        )
      );

      List<Connection> connections = new ArrayList<>();
      for (int i = 0; i < 10; i++) {
        connections.add(dataSource.getConnection());
        System.out.println(String.format("Total connections: %d", dataSource.getNumConnections()));
        Thread.sleep(700);
        System.out.println(String.format("---Idle connections: %d", dataSource.getNumIdleConnections()));
        System.out.println(String.format("---Total connections: %d", dataSource.getNumConnections()));
      }
      Thread.sleep(2000);
      System.out.println(String.format("Total connections: %d", dataSource.getNumConnections()));

      for (Connection connection : connections) {
        connection.close();
        Thread.sleep(500);
        System.out.println(String.format("Idle connections: %d", dataSource.getNumIdleConnections()));
      }
      System.out.println(String.format("Total connections: %d", dataSource.getNumConnections()));
      System.out.println(String.format("Idle connections: %d", dataSource.getNumIdleConnections()));


    } catch (PropertyVetoException | SQLException | InterruptedException e) {
      e.printStackTrace();
    } finally {
      dataSource.close();
    }
  }
}
