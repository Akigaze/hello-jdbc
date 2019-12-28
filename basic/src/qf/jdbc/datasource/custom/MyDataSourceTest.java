package qf.jdbc.datasource.custom;

import qf.jdbc.Utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static qf.jdbc.DatabaseConfiguration.*;

public class MyDataSourceTest {
  public static void main(String[] args) throws SQLException {
    DataSourceConfig config = new DataSourceConfig(
      DRIVER, Utils.connectionUrl(HOST, PORT, DATABASE, CONNECTION_PARAMS), USER, PASSWORD
    );

    MyDataSource dataSource = new MyDataSource(config);

    List<Connection> connections = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      Connection connection = dataSource.getConnection();
      System.out.println(connection);
      connections.add(connection);
    }

    for (Connection connection : connections) {
      connection.close();
      System.out.println(dataSource);
    }

    System.out.println(dataSource);
    System.out.println("close connections: " + dataSource.close());
  }
}
