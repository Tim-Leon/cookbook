package com.neon.cookbook;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;

public class MessagemainController implements Initializable {

  @FXML
  private FlowPane messageBoxId;
  @FXML
  private ScrollPane scrollPaneId;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      loadMessageInfo();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  /**
   * Load messages in a small thingy.
   *
   * @throws IOException Error handle
   */
  public void loadMessageInfo() throws IOException {
    messageBoxId.getChildren().clear();
    scrollPaneId.setVisible(!scrollPaneId.isVisible());

    try {
      ResultSet set = connectionDB.getConnection().createStatement().executeQuery(
        "SELECT * FROM messages JOIN users ON messages.sender_id WHERE users.id = messages.sender_id AND messages.reciever_id = '%s'".formatted(
          Context.getContext().mUserId));
      while (set.next()) {
        new Message(set.getInt("messages.message_id"), messageBoxId, set.getString("display_name"),
          set.getString("text"));
      }
    } catch (SQLException e) {
      System.out.print(e.getMessage() + "\n Nopety");
    }
  }

}
