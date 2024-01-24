package com.neon.cookbook;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Recipe {
  private HBox mHBox;
  private VBox mVBox;
  private ImageView mImageView;
  private Label mRecipeName;
  private Label mDescription;
  private WeeklyDinnerListController weeklyDController;


  // Recipe In weekly dinner list
  Recipe(VBox parent, String recipeName, String description, Image image, List<String> tags, String date,
         int weeklyDinnerListId, WeeklyDinnerListController weeklyController) {
    mHBox = new HBox();
    mVBox = new VBox();
    mImageView = new ImageView();
    mRecipeName = new Label();
    mDescription = new Label();
    Label mDate = new Label();
    this.weeklyDController = weeklyController;


    Button removeRecipe = new Button();
    removeRecipe.setText("Remove");
    EventHandler<ActionEvent> remoEventHandler = new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        try {
          Connection db = connectionDB.getConnection();
          ResultSet set = db.createStatement()
            .executeQuery("Select * FROM weeklydinnerlist WHERE dinnerid = %s".formatted(weeklyDinnerListId));
          System.out.println(weeklyDinnerListId);
          set.next();
          // GET ALL INGREDIENTS THAT ARE LISTED IN THE RECIPE
          ResultSet recipeSet = db.createStatement().executeQuery(
            "Select * FROM recipes JOIN recipe_ingredients ON recipes.id = recipe_ingredients.recipe_id WHERE id = %s ".formatted(
              set.getInt("recipe_id")));
          while (recipeSet.next()) {
            ResultSet shoppingSet = db.createStatement().executeQuery(
              "Select * FROM shoppinglist WHERE ingredient_id = %s AND unit = '%s' AND user_id = %s".formatted(
                recipeSet.getInt("ingredient_id"), recipeSet.getString("unit"), Context.getContext().mUserId));
            while (shoppingSet.next()) {
              // IF THE AMOUNT IS EQUAL IT MEANS IT SHOULD DELETE ITSELF
              if (shoppingSet.getInt("amount") == recipeSet.getInt("amount")) {
                db.createStatement().execute(
                  "DELETE FROM shoppinglist WHERE ingredient_id = %s AND unit = '%s' AND user_id = %s AND week = %s".formatted(
                    recipeSet.getInt("ingredient_id"), recipeSet.getString("unit"), Context.getContext().mUserId,
                    set.getInt("week")));
                // IF THE AMOUNT IS NOT EQUAL IT MEANS IT SHOULD MODIFY THE AMOUNT VALUE
              } else {
                int newsum = shoppingSet.getInt("amount") - recipeSet.getInt("amount") * set.getInt("servings");
                // FOR SOME REASON IT SOMEHOW STILL GETS THE SUM OF 0, THEREFORE I DO A 2ND CHECKUP
                if (newsum <= 0) {
                  db.createStatement().execute(
                    "DELETE FROM shoppinglist WHERE ingredient_id = %s AND unit = '%s' AND user_id = %s AND week = %s".formatted(
                      recipeSet.getInt("ingredient_id"), recipeSet.getString("unit"), Context.getContext().mUserId,
                      set.getInt("week")));
                } else {
                  db.createStatement().execute(
                    "UPDATE shoppinglist SET amount = %s WHERE ingredient_id = %s AND unit = '%s' AND user_id = %s AND week = %s".formatted(
                      newsum, recipeSet.getInt("ingredient_id"), recipeSet.getString("unit"),
                      Context.getContext().mUserId, set.getInt("week")));
                }

              }
            }
          }
          db.createStatement()
            .execute("DELETE FROM weeklydinnerlist WHERE dinnerid = %s".formatted(weeklyDinnerListId));
          weeklyController.updateShoppingList();
          weeklyController.updateWeeklyDinnerList();
        } catch (SQLException e) {
          System.out.println(e.getMessage());
        }
      }
    };

    removeRecipe.setOnAction(remoEventHandler);


    mImageView.setStyle(
      ".image-view-wrapper:border { -fx-border-color: black; -fx-border-style: solid; -fx-border-width: 5;}");
    mHBox.setPrefHeight(60);
    mHBox.setPrefWidth(427);
    mHBox.setMaxWidth(parent.getPrefWidth());
    mHBox.setMinWidth(parent.getPrefWidth());
    mHBox.setStyle(
      "-fx-background-color: #ffffeb; -fx-border-color: #deded9; -fx-border-radius: 20; -fx-background-radius: 20; -fx-padding: 20; -fx-border-style: solid outside; -fx-border-width: 3; -fx-border-insets:2;");
    mHBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        try {
          Context c = Context.getContext();
          FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("detailedRecipe.fxml"));
          Parent root = fxmlLoader.load();
          detailedRecipeController controller = fxmlLoader.getController();
          controller.load(recipeName, image);
          c.showSpace.getChildren().removeAll();
          c.showSpace.getChildren().setAll(root);

        } catch (Exception e) {
          System.out.println("Error" + e);
        }

        System.out.println(mRecipeName.getText().toString());
      }
    });
    mImageView.setFitHeight(119);
    mImageView.setFitWidth(107);
    mImageView.setImage(image);

    mRecipeName.setText(recipeName);
    mRecipeName.setAlignment(Pos.CENTER);
    mRecipeName.setStyle("-fx-font-weight: bold;");
    mRecipeName.setPrefHeight(18);
    mRecipeName.setPrefWidth(332);

    mDescription.setText(description);
    mDescription.setAlignment(Pos.CENTER);
    mDescription.setPrefHeight(105);
    mDescription.setPrefWidth(321);
    mDescription.setWrapText(true);

    mDate.setText(date);
    mDate.setAlignment(Pos.CENTER);
    mDate.setPrefHeight(105);
    mDate.setPrefWidth(321);
    mDate.setWrapText(true);

    mHBox.getChildren().add(mImageView);
    mHBox.getChildren().add(mVBox);

    mVBox.getChildren().add(mRecipeName);
    mVBox.getChildren().add(mDescription);
    mVBox.getChildren().add(mDate);
    mVBox.getChildren().addAll(removeRecipe);
    parent.getChildren().addAll(mHBox);
  }


  // Recipe in SearchScene
  Recipe(FlowPane parent, String recipeName, String description, Image image, SearchController sControler) {
    mHBox = new HBox();
    mImageView = new ImageView();
    mRecipeName = new Label();
    mDescription = new Label();
    mImageView.setStyle(
      ".image-view-wrapper:border { -fx-border-color: black; -fx-border-style: solid; -fx-border-width: 5;}");

    mHBox.setPrefHeight(60);
    mHBox.setMaxWidth(parent.getPrefWidth());
    mHBox.setMinWidth(parent.getPrefWidth());
    mHBox.setStyle(
      "-fx-background-color: #ffffeb; -fx-border-color: #deded9; -fx-border-radius: 15; -fx-background-radius: 15; -fx-padding: 3; -fx-border-style: solid outside; -fx-border-width: 3; -fx-border-insets:2; ");

    mImageView.setFitHeight(119);
    mImageView.setFitWidth(107);
    mImageView.setImage(image);

    mRecipeName.setText(recipeName);
    mRecipeName.setAlignment(Pos.CENTER);
    mRecipeName.setStyle("-fx-font-weight: bold; -fx-padding: 20");
    mRecipeName.setMinHeight(119);
    mRecipeName.setFont(Font.font(24));

    mDescription.setText(description);
    mDescription.setWrapText(true);
    mDescription.setMinHeight(119);
    mDescription.setStyle("-fx-padding: 10");

    mHBox.getChildren().add(mImageView);
    mHBox.getChildren().add(mRecipeName);


    mHBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                              @Override
                              public void handle(MouseEvent event) {
                                try {

                                  FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("detailedRecipe.fxml"));
                                  Parent root = fxmlLoader.load();
                                  detailedRecipeController controller = fxmlLoader.getController();
                                  controller.load(recipeName, image);
                                  Context c = Context.getContext();
                                  c.showSpace.getChildren().removeAll();
                                  c.showSpace.getChildren().setAll(root);
                                } catch (Exception e) {
                                  System.out.println("ERROR");
                                }
                              }
                            }
    );

    // On Hover
    mHBox.setOnMouseEntered(new EventHandler<Event>() {

      @Override
      public void handle(Event event) {
        mHBox.getChildren().remove(mRecipeName);
        mHBox.getChildren().add(mDescription);
      }

    });

    // Off Hover
    mHBox.setOnMouseExited(new EventHandler<Event>() {

      @Override
      public void handle(Event event) {
        mHBox.getChildren().remove(mDescription);
        mHBox.getChildren().add(mRecipeName);
      }

    });


    parent.getChildren().addAll(mHBox);
    parent.setFocusTraversable(false);
  }

  protected void finalize() {

  }


}