package qf.jdbc;

public class City {
  private int id;
  private String code;
  private String name;
  private String country;
  private Integer population;


  public City(int id, String code, String name, String country, Integer population) {
    this.id = id;
    this.code = code;
    this.name = name;
    this.country = country;
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
