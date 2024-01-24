package com.neon.cookbook;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

public class detailedRecipeController implements Initializable {

  private String recipeId;
  @FXML
  private Label recipeName;
  @FXML
  private Label recipeSummary;
  @FXML
  private TextArea recipeDescription;
  @FXML
  private ImageView recipeFavoriteButton;
  @FXML
  private Label newComment;
  @FXML
  private FlowPane commentBoxId;
  @FXML
  private ScrollPane scrollPaneId;
  @FXML
  private ImageView recipeImage;
  @FXML
  private FlowPane tagFlowPane;
  @FXML
  private GridPane ingredient_grid;
  @FXML
  private Button dinnerListButton;
  @FXML
  private Button ingredient_add_btn;
  @FXML
  private Button servings_sub_btn;
  @FXML
  private TextField ingredient_portion_count_tb;

  private int user_id = Context.getContext().mUserId;

  private List<Ingredient> mIngredients = new ArrayList<Ingredient>();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    recipeName.setStyle("-fx-font-weight: bold;");
    tagFlowPane.setVgap(2);
    tagFlowPane.setHgap(2);
    ingredient_portion_count_tb.textProperty().addListener(this::changed);
  }

  // Subtract button for portions
  public void ServingsSubCallback(ActionEvent e) throws IOException {
    if (Integer.parseInt(ingredient_portion_count_tb.getText()) <= 1) {
      return;
    }
    ingredient_portion_count_tb.textProperty()
      .setValue(Integer.toString(Integer.parseInt(ingredient_portion_count_tb.getText()) - 1));
  }

  // button for proportions
  public void ServingsAddCallback(ActionEvent e) throws IOException {
    var newVal = Integer.toString(Integer.parseInt(ingredient_portion_count_tb.getText()) + 1);
    ingredient_portion_count_tb.textProperty().setValue(newVal);
  }

  private void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    if (!newValue.matches("\\d*")) {
      ingredient_portion_count_tb.setText(newValue.replaceAll("[^\\d]", ""));
      return;
    }
    for (var a : mIngredients) {
      a.SetPortion(Integer.parseInt(newValue));
    }
  }


  public void backToRecipeList(ActionEvent e) throws IOException {

    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("search.fxml"));
    Parent root = fxmlLoader.load();
    Context c = Context.getContext();
    c.showSpace.getChildren().removeAll();
    c.showSpace.getChildren().setAll(root);
  }

  //A function that loads This scene at the very begining
  public void load(String name, Image image) throws SQLException {
    // Code to collect the correct data from db
    ResultSet set = connectionDB.getConnection().createStatement()
      .executeQuery("SELECT * FROM recipes WHERE name LIKE '%" + name + "%'");

    set.next();

    recipeId = set.getString("id");
    recipeName.setText(set.getString("name"));
    recipeSummary.setText(set.getString("summary"));
    recipeDescription.setText(set.getString("description"));
    recipeImage.setImage(image);
    loadCommentTable();
    loadTagsByRecipeId(Integer.parseInt(recipeId));
    loadIngredientGridByRecipeId(Integer.parseInt(recipeId));

    ResultSet recipeId = connectionDB.getConnection().createStatement().executeQuery(
      "SELECT id FROM recipes WHERE name =\"" + name + "\"");
    recipeId.next();
    ResultSet favoriteCount = connectionDB.getConnection().createStatement().executeQuery(
      "SELECT Count(user_id) AS counter FROM favorites WHERE user_id = '" + Context.getContext().mUserId +
        "' AND recipe_id =" + recipeId.getInt("id"));
    favoriteCount.next();
    if (favoriteCount.getInt("counter") == 1) {
      recipeFavoriteButton.setImage(new Image("favoritedStar.png"));
    } else {
      recipeFavoriteButton.setImage(new Image("unFavoritedStar.png"));
    }

    //Favorite Button
    recipeFavoriteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        try {
          Context c = Context.getContext();
          ResultSet recipeId = connectionDB.getConnection().createStatement().executeQuery(
            "SELECT id FROM recipes WHERE name =\"" + name + "\"");
          recipeId.next();
          ResultSet favorites = connectionDB.getConnection().createStatement().executeQuery(
            "SELECT * FROM favorites WHERE user_id = " + c.mUserId + " AND recipe_id = " + recipeId.getInt("id"));

          if (!favorites.next()) {
            PreparedStatement favoriteInsert = connectionDB.getConnection().prepareStatement(
              "INSERT INTO favorites VALUES('%s', %d)".formatted(c.mUserId, recipeId.getInt("id")));
            favoriteInsert.execute();
            recipeFavoriteButton.setImage(new Image("favoritedStar.png"));
          } else {
            PreparedStatement favoriteDelete = connectionDB.getConnection().prepareStatement(
              "DELETE FROM favorites WHERE recipe_id =" + recipeId.getInt("id") + " AND user_id = " + c.mUserId);
            favoriteDelete.execute();
            recipeFavoriteButton.setImage(new Image("unFavoritedStar.png"));
          }
        } catch (SQLException e) {
          System.out.println(e.getMessage());
        }
      }

    });
  }

  // Presents the user with a prompt up for a comment
  public void addNewComment() {
    NewCommentBox.display("New comment", "Insert your message", recipeId);
    loadCommentTable();
    return;
  }

  // this method modifies the dinnerlist to remove or modify value on ingredients from shoppinglist
  public void modifyDinnerList(ActionEvent event) {

    try {
      DatePickerController.display(recipeName.getText(), Integer.parseInt(ingredient_portion_count_tb.getText()),
        dinnerListButton);
    } catch (Exception e) {
      System.out.println("ERROR");
    }
  }


  // Function that updates the table
  public void loadCommentTable() {

    commentBoxId.getChildren().clear();

    try {
      ResultSet set = connectionDB.getConnection().createStatement().executeQuery(
        "SELECT * FROM comment JOIN users ON comment.user_id WHERE users.id = comment.user_id AND comment.recipe_id = '%s'".formatted(
          recipeId));
      while (set.next()) {
        new Comment(commentBoxId, set.getString("display_name"), set.getString("text"), scrollPaneId.getPrefWidth(),
          this);
      }
    } catch (SQLException e) {
      System.out.print(e);
    }
  }

  // Loads the tags of the recipe
  public void loadTagsByRecipeId(int recipeId) {
    try {
      ResultSet set = connectionDB.getConnection().createStatement()
        .executeQuery("SELECT name FROM tags WHERE recipe_id = %s".formatted(recipeId));
      while (set.next()) {
        new TagBubble(tagFlowPane, set.getString("name"));
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public void loadIngredientGridByRecipeId(int id) {
    try {
      ResultSet set = connectionDB.getConnection().createStatement().executeQuery(
        "SELECT * FROM recipe_ingredients JOIN ingredients ON recipe_ingredients.ingredient_id = ingredients.id WHERE recipe_id = %s".formatted(
          id));
      mIngredients.clear();
      while (set.next()) {
        var portin = set.getString("portions");
        ingredient_portion_count_tb.setText(portin);
        var ing = new Ingredient(ingredient_grid, set.getString("name"), set.getInt("amount"), set.getString("unit"));
        ing.SetPortion(Integer.parseInt(portin));
        mIngredients.add(ing);
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public void sendMessage(ActionEvent e) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("sendMessage.fxml"));
    Parent root = fxmlLoader.load();
    Context c = Context.getContext();
    SendMessageController controller = fxmlLoader.getController();
    controller.load(Integer.parseInt(this.recipeId));
    c.showSpace.getChildren().removeAll();
    c.showSpace.getChildren().setAll(root);
  }

}
