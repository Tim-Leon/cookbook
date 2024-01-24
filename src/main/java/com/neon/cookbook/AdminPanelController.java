package com.neon.cookbook;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

public class AdminPanelController implements Initializable {

  @FXML
  GridPane adminGridPane;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    updateAdminTable();

  }


  public void backToSearch(ActionEvent e) throws IOException {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("weeklydinnerlist.fxml"));
      Parent root = fxmlLoader.load();
      Context c = Context.getContext();
      c.showSpace.getChildren().removeAll();
      c.showSpace.getChildren().setAll(root);
    } catch (IOException e1) {
      System.out.println("error" + e1);
    }
  }

  public void updateAdminTable() {
    try {
      ResultSet set = connectionDB.getConnection().createStatement().executeQuery("SELECT * FROM users");
      while (set.next()) {
        new UserRow(adminGridPane, set.getString("id"), set.getString("username"), set.getString("display_name"),
          set.getString("role"));
      }
    } catch (SQLException e) {
      System.out.println("ERROR");
    }
    return;
  }

}