package com.neon.cookbook;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;

/**
 * Controller for message fxml.
 */
public class MessageController {

  private int message_id;
  @FXML
  private Label senderLabel;
  @FXML
  private TextArea text;
  @FXML
  private Label recipeLabel;
  @FXML
  private Button goToRecipeButton;

  public void setMessageId(int msgId) {
    this.message_id = msgId;
  }

  /**
   * Sets the scene with the proper message and labels.
   *
   * @param messageId Id of the message
   * @throws SQLException Handle Error
   */
  public void load(int messageId) throws SQLException {
    setMessageId(messageId);
    ResultSet set = connectionDB.getConnection().createStatement()
      .executeQuery("SELECT * FROM messages WHERE messages.message_id = '%s'".formatted(messageId));

    set.next();
    String senderId = set.getString("sender_id");

    ResultSet senderSet = connectionDB.getConnection().createStatement()
      .executeQuery("SELECT * FROM users WHERE id = '%s'".formatted(senderId));
    senderSet.next();
    senderLabel.setText(senderSet.getString("display_name"));

    text.setText(set.getString("text"));
    text.setEditable(false);


    ResultSet recipeSet = connectionDB.getConnection().createStatement()
      .executeQuery("SELECT * FROM recipes WHERE id = '%s'".formatted(set.getString("recipe_id")));
    if (recipeSet.next()) {
      recipeLabel.setText(recipeSet.getString("name"));
      goToRecipeButton.setVisible(true);
    }


  }


  /**
   * Go back to "index" scene.
   *
   * @param e Handle error
   * @throws IOException Handle error
   */
  public void backToMessagemain(ActionEvent e) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("messagemain.fxml"));
    Parent root = fxmlLoader.load();
    Context c = Context.getContext();
    c.showSpace.getChildren().removeAll();
    c.showSpace.getChildren().setAll(root);
  }


  public void goToRecipe(ActionEvent event) {
    try {
      ResultSet set = connectionDB.getConnection().createStatement().executeQuery(("SELECT * FROM" +
        " messages WHERE messages.message_id = '%s'").formatted(this.message_id));
      set.next();
      ResultSet recipeSet = connectionDB.getConnection().createStatement().executeQuery(("SELECT * FROM" +
        " recipes WHERE id = '%s'").formatted(set.getString("recipe_id")));
      recipeSet.next();
      byte[] pixelBytes = recipeSet.getBytes("image");
      InputStream is = new ByteArrayInputStream(pixelBytes);

      FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("detailedRecipe.fxml"));
      Parent root = fxmlLoader.load();
      Context c = Context.getContext();
      detailedRecipeController controller = fxmlLoader.getController();
      controller.load(recipeSet.getString("name"), new Image(is));
      c.showSpace.getChildren().removeAll();
      c.showSpace.getChildren().setAll(root);

    } catch (Exception e) {
      System.out.println("ERROR: \n" + e.getMessage());
    }
  }


}
