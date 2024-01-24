package com.neon.cookbook;

import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

// A box that appears once the user clicks on edit comment
public class EditCommentBox {

  public static void display(String title, String Content, detailedRecipeController drc) {
    Stage window = new Stage();
    window.initModality(Modality.APPLICATION_MODAL);
    window.setMinWidth(250);

    Label label = new Label();
    label.setText(title);
    TextField textField = new TextField();
    textField.setText(Content);
    textField.promptTextProperty().set("Comment");
    Button closeButton = new Button("Close");
    Button sendButton = new Button("Submit");
    HBox hBox = new HBox();

    hBox.setAlignment(Pos.CENTER);

    closeButton.setOnAction(e -> window.close());
    sendButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
          try {
            if (textField.getText().length() == 0) {
              connectionDB.getConnection().createStatement().execute(
                "DELETE FROM comment WHERE text = '%s' AND user_id = %s;".formatted(Content,
                  Context.getContext().mUserId));
            } else {
              connectionDB.getConnection().createStatement().execute(
                "UPDATE comment SET text = '%s' WHERE text = '%s' AND user_id = %s;".formatted(textField.getText(),
                  Content, Context.getContext().mUserId));
            }

          } catch (SQLException e) {
            System.out.print(e);
          }
          drc.loadCommentTable();
          window.close();
        }
      }
    );

    hBox.getChildren().addAll(sendButton, closeButton);
    VBox layout = new VBox(10);
    layout.getChildren().addAll(label, textField, hBox);
    layout.setAlignment(Pos.CENTER);

    Scene scene = new Scene(layout);
    window.setScene(scene);
    window.showAndWait();

  }

}
