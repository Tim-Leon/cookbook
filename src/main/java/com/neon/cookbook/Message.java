package com.neon.cookbook;


import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class Message {

  private HBox mHbox;
  private Label sender;
  private Label message;
  private Label goToMessage;

  public Message(int messageId, FlowPane CommentTable, String author, String text) {
    GridPane g = new GridPane();
    g.setMinWidth(CommentTable.getPrefWidth());
    mHbox = new HBox();

    // Sender label
    sender = new Label();
    sender.setText(author);
    sender.setStyle("-fx-padding: 10");
    sender.setPrefWidth(50);

    // Hbox
    mHbox.setSpacing(5);
    // Style to match some other scenes
    mHbox.setStyle("-fx-background-color: #ffffeb; -fx-border-color: #deded9; -fx-border-radius: 15;" +
      " -fx-background-radius: 15; -fx-padding: 3; -fx-border-style: solid outside;" +
      " -fx-border-width: 3; -fx-border-insets:2; ");
    mHbox.getChildren();
    mHbox.setMinWidth(CommentTable.getPrefWidth() - sender.getPrefWidth());
    mHbox.setMaxWidth(CommentTable.getPrefWidth() - sender.getPrefWidth());
    // Message
    message = new Label();
    message.setText(text);
    message.setWrapText(true);
    message.setLineSpacing(5);
    message.setStyle("-fx-padding: 10; ");

    goToMessage = new Label();
    goToMessage.setText("Read more");
    goToMessage.setTextFill(Color.NAVY);
    goToMessage.setUnderline(true);
    goToMessage.setMinWidth(50);

    //Click on Read more to go to the whole message
    goToMessage.setOnMouseClicked(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent event) {
        // Load the Message scene with the right info
        try {
          FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("message.fxml"));
          Parent root = fxmlLoader.load();
          Context c = Context.getContext();
          MessageController controller = fxmlLoader.getController();
          controller.load(messageId);
          c.showSpace.getChildren().removeAll();
          c.showSpace.getChildren().setAll(root);
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
      }
    });


    mHbox.getChildren().addAll(goToMessage);
    mHbox.getChildren().addAll(message);
    g.addRow(g.getRowCount(), sender, mHbox);
    g.setGridLinesVisible(true);
    CommentTable.getChildren().addAll(g);
  }

}
