package com.neon.cookbook;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class Comment {

  private HBox mHbox;
  private Label authorName;
  private Label commentText;
  private Label editComment;


  public Comment(FlowPane CommentTable, String author, String text, double commentWidth, detailedRecipeController drc) {
    GridPane g = new GridPane();
    g.setMinWidth(CommentTable.getPrefWidth());
    mHbox = new HBox();

    // Author label
    authorName = new Label();
    authorName.setText(author);
    authorName.setStyle("-fx-padding: 10 ");
    authorName.setPrefWidth(50);

    // Hbox
    mHbox.setSpacing(5);
    mHbox.setStyle("-fx-background-color: #999999;");
    mHbox.getChildren();
    mHbox.setMinWidth(CommentTable.getPrefWidth() - authorName.getPrefWidth());
    mHbox.setMaxWidth(CommentTable.getPrefWidth() - authorName.getPrefWidth());
    commentText = new Label();
    commentText.setText(text);
    commentText.setWrapText(true);
    commentText.setLineSpacing(5);
    commentText.setStyle("-fx-padding: 10");

    mHbox.getChildren().addAll(commentText);

    // Edit label/button
    // Create an edit button if the author and the user matches
    if (Context.getContext().mNickname.equals(author)) {
      editComment = new Label();
      editComment.setText("edit");
      editComment.setTextFill(Color.NAVY);
      editComment.setUnderline(true);
      editComment.setMinWidth(50);

      editComment.setOnMouseClicked((EventHandler<? super MouseEvent>) new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
          EditCommentBox.display("Edit comment", text, drc);
        }
      });

      mHbox.getChildren().addAll(editComment);
    }
    g.addRow(g.getRowCount(), authorName, mHbox);
    g.setGridLinesVisible(true);
    CommentTable.getChildren().addAll(g);
  }

}
