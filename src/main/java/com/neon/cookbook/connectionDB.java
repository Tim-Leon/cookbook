package com.neon.cookbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A singleton class that connects to the db once.
 */
public class connectionDB {

  public static Connection connector;

  public static Connection getConnection() throws SQLException {
    if (connector == null) {
      connector = DriverManager
        .getConnection("jdbc:mysql://localhost:3306/cookbook?user=root&password=root&useSSL=false");
    }
    return connector;
  }

}
