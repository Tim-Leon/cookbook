package com.neon.cookbook;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;

public class SendMessageController {

  @FXML
  private TextArea messageTextArea;
  @FXML
  private FlowPane userListId;
  @FXML
  private ScrollPane scrollPaneId;
  @FXML
  private Label selectedUserLabel;
  @FXML
  private Label selectedRecipeLabel;

  private int selectedUserId;

  private int recipeId;

  public void backToMessagemain(ActionEvent e) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("messagemain.fxml"));
    Parent root = fxmlLoader.load();
    Context c = Context.getContext();
    c.showSpace.getChildren().removeAll();
    c.showSpace.getChildren().setAll(root);
  }


  public void load(int recipe_id) {
    setRecipeId(recipe_id);
    userListId.getChildren().clear();


    try {
      ResultSet set = connectionDB.getConnection().createStatement().executeQuery(
        "SELECT * FROM users WHERE id <> '%s'".formatted(Context.getContext().mUserId));
      while (set.next()) {
        new UsersForMessage(set.getInt("id"), userListId, set.getString("display_name"), this);
      }
    } catch (SQLException e) {
      System.out.print(e.getMessage() + "\n Nopety");
    }
    try {
      System.out.println(this.recipeId);
      ResultSet set = connectionDB.getConnection().createStatement().executeQuery(
        "SELECT * FROM recipes WHERE id = '%s'".formatted(this.recipeId));
      set.next();
      selectedRecipeLabel.setText(set.getString("name"));
    } catch (SQLException e) {
      System.out.print(e.getMessage() + "\n Nopety recipe");
    }
  }

  public void setRecipeId(int recId) {
    this.recipeId = recId;
  }

  public void setSelectedUserId(int userId) {
    selectedUserId = userId;
  }

  public void selectUser(int userId) throws SQLException {
    setSelectedUserId(userId);
    ResultSet set = connectionDB.getConnection().createStatement().executeQuery(
      "SELECT * FROM users WHERE id = '%s'".formatted(userId));
    set.next();
    selectedUserLabel.setText(set.getString("display_name"));
  }

  public void sendMessage(ActionEvent e) throws SQLException {
    Connection db = connectionDB.getConnection();
    PreparedStatement myStmt = db.prepareStatement(("INSERT INTO messages (sender_id, reciever_id, recipe_id, text)" +
      " VALUES ('%s', '%s', '%s', '%s')").formatted(Context.getContext().mUserId, this.selectedUserId,
      recipeId, messageTextArea.getText()));
    myStmt.execute();
  }

}
