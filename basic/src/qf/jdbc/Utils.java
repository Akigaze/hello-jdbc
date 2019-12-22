package qf.jdbc;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {
  public static String connectionUrl(String host, Integer port, String database, Map<String, Object> params) {
    String baseUrl = String.format("jdbc:mysql://%s:%d/%s", host, port, database);
    if (params == null || params.isEmpty()) {
      return baseUrl;
    }
    List<String> paramPairs = params.entrySet().stream()
      .map(entry -> entry.getKey() + "=" + entry.getValue())
      .collect(Collectors.toList());
    return baseUrl + "?" + String.join("&", paramPairs);
  }
}
