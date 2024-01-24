package com.neon.cookbook;

import java.sql.SQLException;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;


class TagBubble {

  private FlowPane mFlowPane;
  private HBox mSearchBox;
  private Label mLabel;


  // Constructor, for when we want to have a clickable bubble
  public TagBubble(FlowPane flowPane, HBox searchHBox, String name, TextField searchTextFieldCategoriesId) {

    mFlowPane = flowPane;
    mSearchBox = searchHBox;
    mLabel = new Label();
    mLabel.setText(name.toLowerCase());
    mLabel.setStyle("-fx-background-color: #FFBCBC; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 5;");
    mFlowPane.getChildren().addAll(mLabel);

    mLabel.setOnMouseClicked((EventHandler<? super MouseEvent>) new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (mFlowPane.getChildren().contains(mLabel)) {
          mFlowPane.getChildren().remove(mLabel);
          searchHBox.getChildren().addAll(mLabel);
        } else {
          searchHBox.getChildren().remove(mLabel);
          if (name.contains(searchTextFieldCategoriesId.getText().toLowerCase()) && existsInTagDb(name)) {
            mFlowPane.getChildren().addAll(mLabel);
          }
        }
      }
    });
  }

  // Constructor, for when we want to update the search box on every bubble click
  public TagBubble(FlowPane flowPane, HBox searchHBox, String name, TextField searchTextFieldCategoriesId,
                   SearchController sc) {

    mFlowPane = flowPane;
    mSearchBox = searchHBox;
    mLabel = new Label();
    mLabel.setText(name.toLowerCase());
    mLabel.setStyle("-fx-background-color: #FFBCBC; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 5;");
    mFlowPane.getChildren().addAll(mLabel);

    mLabel.setOnMouseClicked((EventHandler<? super MouseEvent>) new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (mFlowPane.getChildren().contains(mLabel)) {
          mFlowPane.getChildren().remove(mLabel);
          searchHBox.getChildren().addAll(mLabel);
        } else {
          searchHBox.getChildren().remove(mLabel);
          if (name.contains(searchTextFieldCategoriesId.getText().toLowerCase()) && existsInTagDb(name)) {
            mFlowPane.getChildren().addAll(mLabel);
          }
        }
        sc.updateTable();
      }
    });
  }

  // Constructor, for when we want to have a non clickable bubble
  public TagBubble(FlowPane flowPane, String name) {

    mFlowPane = flowPane;
    mLabel = new Label();
    mLabel.setText(name.toLowerCase());
    mLabel.setStyle("-fx-background-color: #FFBCBC; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 5;");
    mFlowPane.getChildren().addAll(mLabel);
  }

  // Checks if tag exists in the db
  private boolean existsInTagDb(String name) {
    try {
      var result = connectionDB.getConnection().createStatement()
        .executeQuery("SELECT COUNT(name) AS result FROM tags WHERE name = '%s'".formatted(name));
      while (result.next()) {
        if (result.getInt("result") >= 1) {
          return true;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public void moveToTarget() {
    // Check if
    if (!(mSearchBox == null)) {
      if (mFlowPane.getChildren().contains(mLabel)) {
        mFlowPane.getChildren().remove(mLabel);
        mSearchBox.getChildren().addAll(mLabel);
      }
    }

    return;
  }

  // Returns its Label Object
  public Label getLabel() {
    return mLabel;
  }


}