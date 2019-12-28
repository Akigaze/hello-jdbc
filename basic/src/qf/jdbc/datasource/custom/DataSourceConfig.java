package qf.jdbc.datasource.custom;

public class DataSourceConfig {
  private String driver;
  private String jdbcUrl;
  private String username;
  private String password;

  private int initPoolSize = 5;

  DataSourceConfig(String driver, String jdbcUrl, String username, String password) {
    this.driver = driver;
    this.jdbcUrl = jdbcUrl;
    this.username = username;
    this.password = password;
  }

  public String getDriver() {
    return driver;
  }

  String getJdbcUrl() {
    return jdbcUrl;
  }

  String getUsername() {
    return username;
  }

  String getPassword() {
    return password;
  }

  int getInitPoolSize() {
    return initPoolSize;
  }
}
