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

public class NewCommentBox {

  public static void display(String title, String message, String recipeId) {
    Stage window = new Stage();
    window.initModality(Modality.APPLICATION_MODAL);
    window.setMinWidth(250);

    Label label = new Label();
    label.setText(message);
    TextField textField = new TextField();
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
            System.out.print("INSERT INTO comment (user_id, recipe_id, text) VALUES ('%s' '%s' '%s')".formatted(
              Context.getContext().mUserId, recipeId, textField.getText()));
            connectionDB.getConnection().createStatement().execute(
              "INSERT INTO comment (user_id, recipe_id, text) VALUES ('%s', '%s', '%s')".formatted(
                Context.getContext().mUserId, recipeId, textField.getText()));
          } catch (SQLException e) {
            System.out.print(e);
          }
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
