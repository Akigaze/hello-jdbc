package qf.jdbc.datasource.hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;
import qf.jdbc.Utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static qf.jdbc.DatabaseConfiguration.*;

public class HikariCPConfigDemo {
  public static void main(String[] args) throws SQLException, InterruptedException {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName(DRIVER);
    hikariConfig.setJdbcUrl(Utils.connectionUrl(HOST, PORT, DATABASE, CONNECTION_PARAMS));
    hikariConfig.setUsername(USER);
    hikariConfig.setPassword(PASSWORD);
    hikariConfig.setIdleTimeout(200); // 只有当 MinimumIdle < MaximumPoolSize 时才生效，且必须大于10s(10,000ms)才生效
    hikariConfig.setMaximumPoolSize(20);
    hikariConfig.setMinimumIdle(10); // 最小空闲连接数，有些抽风，官方建议不做设置

    HikariDataSource dataSource = new HikariDataSource(hikariConfig); // 使用HikariConfig做参数会先创建一个连接

    HikariPool hikariPoolMXBean = (HikariPool) dataSource.getHikariPoolMXBean(); // 在没有创建连接之前，连接池为null
    log(dataSource, hikariPoolMXBean);


    List<Connection> connections = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      connections.add(dataSource.getConnection());
      Thread.sleep(500);
      log(dataSource, hikariPoolMXBean);
    }

    log(dataSource, hikariPoolMXBean);


    for (Connection connection : connections) {
      connection.close();
      Thread.sleep(500);
      log(dataSource, hikariPoolMXBean);
    }

    log(dataSource, hikariPoolMXBean);

    dataSource.close();
  }

  private static void log(HikariDataSource dataSource, HikariPool pool) {
    System.out.println(
      String.format(
        "connection: %d, Max: %d, MinIdle: %s, Active: %d, Idle: %d, IdleTimeout: %d, connectTimeout: %d",
        pool.getTotalConnections(),
        dataSource.getMaximumPoolSize(),
        dataSource.getMinimumIdle(),
        pool.getActiveConnections(),
        pool.getIdleConnections(),
        dataSource.getIdleTimeout(),
        dataSource.getConnectionTimeout()
      )
    );
  }
}
