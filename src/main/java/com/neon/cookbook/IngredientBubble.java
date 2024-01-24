package com.neon.cookbook;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

public class IngredientBubble {
  private Label mLabel;

  public IngredientBubble(FlowPane mFlowPane, HBox searchHBox, String name, TextField searchTextFieldCategoriesId,
                          SearchController sc) {
    ;
    mLabel = new Label();
    mLabel.setText(name);

    mLabel.setStyle("-fx-background-color: #00FF00; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 5;");
    mFlowPane.getChildren().addAll(mLabel);

    mLabel.setOnMouseClicked((EventHandler<? super MouseEvent>) new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (mFlowPane.getChildren().contains(mLabel)) {
          mFlowPane.getChildren().remove(mLabel);
          searchHBox.getChildren().addAll(mLabel);
        } else {
          searchHBox.getChildren().remove(mLabel);
          if (name.contains(searchTextFieldCategoriesId.getText())) {
            mFlowPane.getChildren().addAll(mLabel);
          }

        }
        sc.updateTable();
      }
    });

  }
}
