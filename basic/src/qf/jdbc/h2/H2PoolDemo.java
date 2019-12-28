package qf.jdbc.h2;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class H2PoolDemo {
  public static void main(String[] args) throws SQLException {
    JdbcDataSource dataSource = new JdbcDataSource(); // h2 的数据源相当于一个jdbcUtil，每次都新创建一个连接
    dataSource.setUrl("jdbc:h2:mem:test");
    dataSource.setUser("sa");
    dataSource.setPassword("");

    Connection connection = dataSource.getConnection();
    Statement statement = connection.createStatement();
    statement.execute("create table demo(id int, desc varchar(20))");
    statement.executeUpdate("insert into demo values(1, 'hello')");
    ResultSet resultSet = statement.executeQuery("select * from demo");

    while (resultSet.next()) {
      System.out.println(resultSet.getInt(1) + " - " + resultSet.getString(2));
    }

    Connection connection2 = dataSource.getConnection();
    System.out.println(connection == connection2);

    ResultSet resultSet2 = connection2.createStatement().executeQuery("select * from demo");

    while (resultSet2.next()) {
      System.out.println(resultSet2.getInt(1) + " - " + resultSet2.getString(2));
    }

    resultSet.close();
    statement.close();
    connection.close();
    resultSet2.close();
    connection2.close();
  }
}
