package com.neon.cookbook;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class SearchController implements Initializable {

  @FXML
  private FlowPane recipeListId;

  @FXML
  private FlowPane tagListId;

  @FXML
  private HBox pickedTagListId;

  @FXML
  private TextField searchTextFieldRecipeNameId;

  @FXML
  private TextField searchTextFieldCategoriesId;

  @FXML
  private CheckBox favoriteButton;

  @FXML
  private ScrollPane recipeScrollPane;

  private Stage stage;
  private Scene scene;
  private Parent root;


  // This function is ran once, uppon opening the scene
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    recipeScrollPane.setStyle(" -fx-focus-color: transparent; ");

    recipeListId.setVgap(5);
    tagListId.setVgap(2);
    tagListId.setHgap(2);
    updateTable(); // We generate the recipies
    updateCategories(); // We generate the categories
    searchTextFieldRecipeNameId.textProperty()
      .addListener((observable, oldValue, newValue) -> { // We create a an event listener to handle search bar changes.
        updateTable();
      });
    searchTextFieldRecipeNameId.textProperty().addListener((observable, oldValue, newValue) -> {
      updateCategories();
    });
  }

  private ArrayList<Recipe> mRecipes = new ArrayList<Recipe>();

  // Function that updates the table
  public void updateTable() {

    // Clears the recipies tables
    recipeListId.getChildren().clear();
    mRecipes.clear();

    ArrayList<String> pickedTagTempList = new ArrayList<>();
    ArrayList<String> pickedIngredientTempList = new ArrayList<>();

    // Gets all the tags and Ingredients into separate lists
    for (Node n : pickedTagListId.getChildren()) {
      if (n instanceof Label) {
        if (((Label) n).getStyle().contains("-fx-background-color: #FFBCBC;")) { // Separates the Labels by Color
          pickedTagTempList.add(((Label) n).getText());
        } else {
          pickedIngredientTempList.add(((Label) n).getText());
        }
      }
    }

    // FAVORITE SEARCH ON
    if (favoriteButton.isSelected()) {
      try {
        ArrayList<String> displayRecipesTags = new ArrayList<>();
        // Gets all recipes based on tags
        if (pickedTagTempList.size() != 0) {
          String stmt = "SELECT T.recipe_id, COUNT(*) as repeats FROM (";
          for (int i = 0; i < pickedTagTempList.size(); i++) {
            if (i == 0) {
              stmt += "SELECT * FROM tags WHERE tags.name = '%s' ".formatted(pickedTagTempList.get(i));
            } else {
              stmt += "UNION SELECT * FROM tags WHERE tags.name = '%s' ".formatted(pickedTagTempList.get(i));
            }
          }
          stmt += ") AS T GROUP BY T.recipe_id HAVING repeats >= %s".formatted(pickedTagTempList.size());
          ResultSet set = connectionDB.getConnection().createStatement().executeQuery(stmt);
          while (set.next()) {
            ResultSet set2 = connectionDB.getConnection().createStatement()
              .executeQuery("SELECT name from recipes WHERE id = '%s'".formatted(set.getString("recipe_id")));
            while (set2.next()) {
              displayRecipesTags.add(set2.getString("name"));
            }
          }
        }

        ArrayList<String> displayRecipesIngredients = new ArrayList<>();
        // Gets all recipes based on tags and on ingredients
        if (pickedIngredientTempList.size() != 0) {
          String stmt = "SELECT T.recipe_id, COUNT(*) AS repeats FROM (";
          for (int i = 0; i < pickedIngredientTempList.size(); i++) {
            if (i == 0) {
              stmt +=
                "SELECT * FROM recipe_ingredients JOIN ingredients ON recipe_ingredients.ingredient_id = ingredients.id WHERE ingredients.name = '%s' ".formatted(
                  pickedIngredientTempList.get(i));
            } else {
              stmt +=
                "UNION SELECT * FROM recipe_ingredients JOIN ingredients ON recipe_ingredients.ingredient_id = ingredients.id WHERE ingredients.name = '%s' ".formatted(
                  pickedIngredientTempList.get(i));
            }
          }
          stmt += ") AS T GROUP BY T.recipe_id HAVING repeats >= %s".formatted(pickedIngredientTempList.size());
          ResultSet set = connectionDB.getConnection().createStatement().executeQuery(stmt);
          while (set.next()) {
            ResultSet set2 = connectionDB.getConnection().createStatement()
              .executeQuery("SELECT name from recipes WHERE id = '%s'".formatted(set.getString("recipe_id")));
            while (set2.next()) {
              // If it exists in the tag list, that means we should keep it
              if (displayRecipesTags.contains(set2.getString("name")) || pickedTagTempList.size() == 0) {
                displayRecipesIngredients.add(set2.getString("name"));
              }
            }
          }
        } else { // If no ingredients are picked we use the tag list
          displayRecipesIngredients = displayRecipesTags;
        }


        int user_Id = Context.getContext().mUserId;
        ResultSet set = connectionDB.getConnection().createStatement().executeQuery(
          "SELECT * FROM recipes INNER JOIN favorites ON favorites.recipe_id = recipes.id WHERE favorites.user_id = '" +
            user_Id + "' AND recipes.name LIKE '%" + searchTextFieldRecipeNameId.getText() + "%'");

        while (set.next()) {
          if (sizeOfPickedBubbles() > 0) {
            if (displayRecipesIngredients.contains(set.getString("name"))) {
              byte[] pixelBytes = set.getBytes("image");
              InputStream is = new ByteArrayInputStream(pixelBytes);
              mRecipes.add(
                new Recipe(recipeListId, set.getString("name"), set.getString("summary"), new Image(is), this));
            }
          } else {
            byte[] pixelBytes = set.getBytes("image");
            InputStream is = new ByteArrayInputStream(pixelBytes);
            mRecipes.add(
              new Recipe(recipeListId, set.getString("name"), set.getString("summary"), new Image(is), this));
          }
        }

      } catch (SQLException e) {
        System.out.print(e);
      }

    } else {
      //FAVORITE SEARCH OFF
      try {
        ArrayList<String> displayRecipesTags = new ArrayList<>();
        // Gets all recipes based on tags
        if (pickedTagTempList.size() != 0) {
          String stmt = "SELECT T.recipe_id, COUNT(*) as repeats FROM (";
          for (int i = 0; i < pickedTagTempList.size(); i++) {
            if (i == 0) {
              stmt += "SELECT * FROM tags WHERE tags.name = '%s' ".formatted(pickedTagTempList.get(i));
            } else {
              stmt += "UNION SELECT * FROM tags WHERE tags.name = '%s' ".formatted(pickedTagTempList.get(i));
            }
          }
          stmt += ") AS T GROUP BY T.recipe_id HAVING repeats >= %s".formatted(pickedTagTempList.size());
          ResultSet set = connectionDB.getConnection().createStatement().executeQuery(stmt);
          while (set.next()) {
            ResultSet set2 = connectionDB.getConnection().createStatement()
              .executeQuery("SELECT name from recipes WHERE id = '%s'".formatted(set.getString("recipe_id")));
            while (set2.next()) {
              displayRecipesTags.add(set2.getString("name"));
            }
          }
        }

        ArrayList<String> displayRecipesIngredients = new ArrayList<>();
        // Gets all recipes based on tags and on ingredients
        if (pickedIngredientTempList.size() != 0) {
          String stmt = "SELECT T.recipe_id, COUNT(*) AS repeats FROM (";
          for (int i = 0; i < pickedIngredientTempList.size(); i++) {
            if (i == 0) {
              stmt +=
                "SELECT * FROM recipe_ingredients JOIN ingredients ON recipe_ingredients.ingredient_id = ingredients.id WHERE ingredients.name = '%s' ".formatted(
                  pickedIngredientTempList.get(i));
            } else {
              stmt +=
                "UNION SELECT * FROM recipe_ingredients JOIN ingredients ON recipe_ingredients.ingredient_id = ingredients.id WHERE ingredients.name = '%s' ".formatted(
                  pickedIngredientTempList.get(i));
            }
          }
          stmt += ") AS T GROUP BY T.recipe_id HAVING repeats >= %s".formatted(pickedIngredientTempList.size());
          ResultSet set = connectionDB.getConnection().createStatement().executeQuery(stmt);
          while (set.next()) {
            ResultSet set2 = connectionDB.getConnection().createStatement()
              .executeQuery("SELECT name from recipes WHERE id = '%s'".formatted(set.getString("recipe_id")));
            while (set2.next()) {
              // If it exists in the tag list, that means we should keep it
              if (displayRecipesTags.contains(set2.getString("name")) || pickedTagTempList.size() == 0) {
                displayRecipesIngredients.add(set2.getString("name"));
              }
            }
          }
        } else { // If no ingredients are picked we use the tag list
          displayRecipesIngredients = displayRecipesTags;
        }

        ResultSet set = connectionDB.getConnection().createStatement()
          .executeQuery("SELECT * FROM recipes WHERE name LIKE '%" + searchTextFieldRecipeNameId.getText() + "%'");
        while (set.next()) {
          if (sizeOfPickedBubbles() > 0) {
            if (displayRecipesIngredients.contains(set.getString("name"))) {
              byte[] pixelBytes = set.getBytes("image");
              InputStream is = new ByteArrayInputStream(pixelBytes);
              mRecipes.add(
                new Recipe(recipeListId, set.getString("name"), set.getString("summary"), new Image(is), this));
            }
          } else {
            byte[] pixelBytes = set.getBytes("image");
            InputStream is = new ByteArrayInputStream(pixelBytes);
            mRecipes.add(
              new Recipe(recipeListId, set.getString("name"), set.getString("summary"), new Image(is), this));
          }
        }

      } catch (SQLException e) {
        System.out.print(e);
      }
    }
  }

  // To avoid an error I've created a separate function
  public int sizeOfPickedBubbles() {
    int i = 0;
    for (Node n : pickedTagListId.getChildren()) {
      i++;
    }
    return i;
  }

  // Function that updates the Categories table
  public void updateCategories() {
    // Store all existing tag names in a string list
    ArrayList<String> existingNameList = new ArrayList<>();
    for (int i = 0; i < pickedTagListId.getChildren().size(); i++) {
      if (pickedTagListId.getChildren().get(i) instanceof Label) {
        if (!existingNameList.contains(((Label) (pickedTagListId.getChildren().get(i))).getText())) {
          existingNameList.add(((Label) (pickedTagListId.getChildren().get(i))).getText());
        }
      }
    }

    try {
      tagListId.getChildren().clear();

      ResultSet set = connectionDB.getConnection().createStatement().executeQuery(
        "SELECT DISTINCT name FROM tags WHERE name LIKE '%" + searchTextFieldRecipeNameId.getText() + "%'");
      while (set.next()) {
        if (!existingNameList.contains(set.getString("name")) && tagListId.getChildren().size() <= 30) {
          new TagBubble(tagListId, pickedTagListId, set.getString("name"), searchTextFieldRecipeNameId, this);
        }
      }
      set = connectionDB.getConnection().createStatement()
        .executeQuery("SELECT * FROM ingredients WHERE name LIKE '%" + searchTextFieldRecipeNameId.getText() + "%'");
      while (set.next()) {
        if (!existingNameList.contains(set.getString("name")) && tagListId.getChildren().size() <= 25) {
          new IngredientBubble(tagListId, pickedTagListId, set.getString("name"), searchTextFieldRecipeNameId, this);
        }
      }
    } catch (SQLException exception) {
      System.out.println(exception);
    }

  }


  public void createRecipe(ActionEvent e) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("create-recipe.fxml"));
      root = fxmlLoader.load();
      Context c = Context.getContext();
      c.showSpace.getChildren().removeAll();
      c.showSpace.getChildren().setAll(root);

    } catch (IOException e1) {
      System.out.println("error" + e1);
    }
  }

  public void weeklyDinner(ActionEvent e) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("weeklydinnerlist.fxml"));
      root = fxmlLoader.load();
      Context c = Context.getContext();
      c.showSpace.getChildren().removeAll();
      c.showSpace.getChildren().setAll(root);
    } catch (IOException e1) {
      System.out.println("error" + e1);
    }
  }
}
