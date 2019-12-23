package qf.jdbc.drivermanager;

import qf.jdbc.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static qf.jdbc.DatabaseConfiguration.*;

public class JDBCUtils {
  static Connection getConnection() throws ClassNotFoundException, SQLException {
    Class.forName(DRIVER);
    Connection connection = DriverManager.getConnection(Utils.connectionUrl(HOST, PORT, DATABASE, CONNECTION_PARAMS), USER, PASSWORD);
    System.out.println("retrieve connection");
    return connection;
  }
}
