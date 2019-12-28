package qf.jdbc.datasource.custom;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.logging.Logger;

public class MyDataSource implements DataSource {
  private String driver;
  private String jdbcUrl;
  private String username;
  private String password;

  private int initSize = 5;
  private int activeSize;
  private int idleSize;
  private int totalSize;

  private final static LinkedList<Connection> connectionPool = new LinkedList<>();


  public MyDataSource() {
  }

  public MyDataSource(DataSourceConfig config) {
    this.driver = config.getDriver();
    this.jdbcUrl = config.getJdbcUrl();
    this.username = config.getUsername();
    this.password = config.getPassword();
    try {
      Class.forName(config.getDriver());
      this.initSize = config.getInitPoolSize();
      for (int i = 0; i < this.initSize; i++) {
        Connection connection = this.createConnection();
        connectionPool.add(connection);
        this.totalSize++;
        this.idleSize++;
      }
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  private Connection createConnection() throws SQLException {
    System.out.println("create connection");
    return DriverManager.getConnection(this.jdbcUrl, this.username, this.password);
  }

  private Connection createProxyConnection(Connection connection) {
    System.out.println("pack connection to proxy");
    return (Connection) Proxy.newProxyInstance(
      connection.getClass().getClassLoader(),
      connection.getClass().getInterfaces(),
      new InvocationHandler() {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
          if ("close".equals(method.getName())) {
            activeSize--;
            idleSize++;
            connectionPool.addLast(connection);
            return 1;
          }
          return method.invoke(connection, args);
        }
      });
  }

  @Override
  public Connection getConnection() throws SQLException {
    System.out.println("get connection");
    synchronized (connectionPool) {
      this.activeSize++;
      if (connectionPool.size() > 0) {
        this.idleSize--;
        return this.createProxyConnection(connectionPool.removeFirst());
      } else {
        Connection connection = this.createConnection();
        this.totalSize++;
        return this.createProxyConnection(connection);
      }
    }
  }

  @Override
  public Connection getConnection(String username, String password) throws SQLException {
    return null;
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    return null;
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return false;
  }

  @Override
  public PrintWriter getLogWriter() throws SQLException {
    return null;
  }

  @Override
  public void setLogWriter(PrintWriter out) throws SQLException {

  }

  @Override
  public void setLoginTimeout(int seconds) throws SQLException {

  }

  @Override
  public int getLoginTimeout() throws SQLException {
    return 0;
  }

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    return null;
  }

  public int close() {
    int closeCount = 0;
    for (Connection connection : connectionPool) {
      try {
        System.out.println("close connection: " + connection);
        connection.close();
        closeCount++;
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    connectionPool.clear();
    this.activeSize = 0;
    this.totalSize = 0;
    this.idleSize = 0;
    return closeCount;
  }

  @Override
  public String toString() {
    return "MyDataSource{" +
      "activeSize=" + activeSize +
      ", idleSize=" + idleSize +
      ", totalSize=" + totalSize +
      '}';
  }
}
