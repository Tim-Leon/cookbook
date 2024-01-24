package com.neon.cookbook;


import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class UsersForMessage {

  private HBox mHbox;
  private Label sender;
  private Label message;

  public UsersForMessage(int userId, FlowPane CommentTable, String displayName, SendMessageController smc) {
    GridPane g = new GridPane();
    g.setMinWidth(CommentTable.getPrefWidth());
    mHbox = new HBox();

    // Sender label
    sender = new Label();
    sender.setText(displayName);
    sender.setStyle("-fx-padding: 10");
    sender.setPrefWidth(50);
    sender.setStyle("-fx-background-color: #999999;");

    // Hbox
    mHbox.setSpacing(5);
    mHbox.setStyle("-fx-background-color: #999999;");
    mHbox.getChildren();
    mHbox.setMinWidth(CommentTable.getPrefWidth() - sender.getPrefWidth());
    mHbox.setMaxWidth(CommentTable.getPrefWidth() - sender.getPrefWidth());
    message = new Label();
    message.setText(displayName);
    message.setWrapText(true);
    message.setLineSpacing(5);
    message.setStyle("-fx-padding: 10");


    mHbox.setOnMouseClicked(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent event) {

        try {
          smc.selectUser(userId);
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
      }
    });


    mHbox.getChildren().addAll(message);
    g.addRow(g.getRowCount(), sender, mHbox);
    g.setGridLinesVisible(true);
    CommentTable.getChildren().addAll(g);
  }

}

