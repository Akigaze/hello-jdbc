package qf.jdbc;

public class City {
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
