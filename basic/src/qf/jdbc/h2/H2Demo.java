package qf.jdbc.h2;

import org.h2.Driver;
import org.h2.tools.Console;

import java.sql.*;

public class H2Demo {
  public static void main(String[] args) throws ClassNotFoundException, SQLException {
    Class.forName(Driver.class.getName());
    Console console = new Console();
    console.runTool();
    Connection connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
    Statement statement = connection.createStatement();
    statement.execute("create table demo(id int, desc varchar(20))");
    statement.executeUpdate("insert into demo values(1, 'hello')");
    ResultSet resultSet = statement.executeQuery("select * from demo");

    while (resultSet.next()) {
      System.out.println(resultSet.getInt(1) + " - " + resultSet.getString(2));
    }

    resultSet.close();
    statement.close();
    connection.close();

    console.shutdown();
  }
}
