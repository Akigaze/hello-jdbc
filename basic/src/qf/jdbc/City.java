package qf.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public class City {
  private static final String FIELD_ID = "id";
  private static final String FIELD_CODE = "code";
  private static final String FIELD_NAME = "name";
  private static final String FIELD_COUNTRY = "country";
  private static final String FIELD_POPULATION = "population";

  private int id;
  private String code;
  private String name;
  private String country;
  private Integer population;

  public City(String code, String name, String country, Integer population) {
    this.code = code;
    this.name = name;
    this.country = country;
    this.population = population;
  }

  public City(int id, String code, String name, String country, Integer population) {
    this.id = id;
    this.code = code;
    this.name = name;
    this.country = country;
    this.population = population;
  }

  public static City build(ResultSet resultSet) throws SQLException {
    if(resultSet !=null && !resultSet.wasNull()){
      return new City(
        resultSet.getInt(FIELD_ID),
        resultSet.getString(FIELD_CODE),
        resultSet.getString(FIELD_NAME),
        resultSet.getString(FIELD_COUNTRY),
        resultSet.getInt(FIELD_POPULATION)
      );
    }
    return null;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public Integer getPopulation() {
    return population;
  }

  public void setPopulation(Integer population) {
    this.population = population;
  }

  @Override
  public String toString() {
    return "City{" +
      "id=" + id +
      ", code='" + code + '\'' +
      ", name='" + name + '\'' +
      ", country='" + country + '\'' +
      ", population=" + population +
      '}';
  }
}
