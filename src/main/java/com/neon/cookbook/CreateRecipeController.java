package com.neon.cookbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CreateRecipeController implements Initializable {

  @FXML
  private FlowPane commentBoxId;
  @FXML
  private TextField name;
  @FXML
  private TextArea desc;
  @FXML
  private TextField shdesc;
  @FXML
  private ImageView image;
  @FXML
  private ComboBox ingredient_unit;
  @FXML
  private TextField ingredient_amount;
  @FXML
  private ComboBox ingredient_name;
  @FXML
  private GridPane ingredient_grid;
  @FXML
  private Button ingredient_add_btn;
  @FXML
  private Button servings_sub_btn;
  @FXML
  private TextField ingredient_portion_count_tb;
  @FXML
  private HBox tags_hb;
  @FXML
  private TextField tags_tf;
  @FXML
  private FlowPane tags_fp;

  // Enumerator for units
  enum Unit {
    kg, g, l, dl, cl, ml, st, tsp, tbsp
  }

  private Stage stage;
  private Parent root;
  private Scene scene;
  private String mPath;
  private static ChangeListener<String> ing_name;

  private List<Ingredient> mIngredients = new ArrayList<>();

  // *** Beggining of methods ***
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ing_name = new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        Platform.runLater(new Runnable() {
          @Override
          public void run() {
            ingredient_name.getEditor().textProperty().removeListener(ing_name);
            UpdateIngredientNameComboBox(newValue);
            ingredient_name.getEditor().clear();
            ingredient_name.getEditor().insertText(ingredient_name.getEditor().getCaretPosition(), newValue);
            ingredient_name.getEditor().textProperty().addListener(ing_name);
          }
        });
      }
    };

    ingredient_name.getEditor().textProperty().addListener(ing_name);


    for (Unit day : Unit.values()) {
      ingredient_unit.getItems().add(day.toString());
    }

    // Forces only the number characters to apear in the text
    ingredient_amount.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        ingredient_amount.setText(newValue.replaceAll("[^\\d]", ""));
      }
    });

    tags_tf.textProperty().addListener((observable, oldValue, newValue) -> {
      updateCategories();
    });

    ingredient_portion_count_tb.textProperty().addListener(this::changed);

    UpdateIngredientNameComboBox("");
  }


  public void submit(ActionEvent e) throws IOException, SQLException {
    String nameOfRecipe = name.getText();
    String description = desc.getText();
    String shortDescription = shdesc.getText();
    FileInputStream is;

    // No Image selected
    if (mPath == null) {
      System.out.println("IMAGE HAS NO PATH");
      return;
    } else {
      is = new FileInputStream(mPath);
    }

    Connection db = connectionDB.getConnection();
    ResultSet set = db.createStatement()
      .executeQuery("SELECT Count(name) AS counter FROM recipes Where name = '%s'".formatted(nameOfRecipe));
    set.next();
    if (set.getString("counter").equals("0")) {
      PreparedStatement myStmt = db.prepareStatement(
        "INSERT INTO recipes(user_id, name, summary, description, image) values ('%s','%s', '%s', '%s', ?)".formatted(
          Context.getContext().mUserId, nameOfRecipe, shortDescription, description));
      myStmt.setBinaryStream(1, is);
      myStmt.execute();
      System.out.println("Recipe entered");
      var res = connectionDB.getConnection().createStatement()
        .executeQuery("SELECT * FROM recipes WHERE name = '%s'".formatted(nameOfRecipe));
      res.next();
      var recipeId = res.getInt("id");
      SubmitRecipeIngredientList(recipeId);

      for (int i = 0; i < tags_hb.getChildren().size(); i++) {
        try {
          if (tags_hb.getChildren().get(i) instanceof Label) {
            SqlAddTag(recipeId, ((Label) tags_hb.getChildren().get(i)).getText());
          }
        } catch (SQLException ee) {
          System.out.println(ee.getMessage());
        }

      }
    } else {
      System.out.println("Already Exists");
    }
  }

  // Method used to upload an image
  public void uploadImage(ActionEvent e) {

    FileChooser fileChooser = new FileChooser();
    File selectedFile = fileChooser.showOpenDialog(stage);
    mPath = selectedFile.getAbsolutePath();
    Image img = new Image(selectedFile.getAbsolutePath(), 100, 100, false, false);
    image.setImage(img);
  }

  //return to search when pressing back button
  public void returnTo(ActionEvent e) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("search.fxml"));
      root = fxmlLoader.load();
      Context c = Context.getContext();
      c.showSpace.getChildren().removeAll();
      c.showSpace.getChildren().setAll(root);
      // if something goes wrong 
    } catch (IOException e1) {
      System.out.println("Error" + e1);
    }
  }

  // Function that updates the Categories table
  public void updateCategories() {
    // Store all existing tag names in a string list
    ArrayList<String> existingNameList = new ArrayList<>();
    for (int i = 0; i < tags_hb.getChildren().size(); i++) {
      if (tags_hb.getChildren().get(i) instanceof Label) {
        if (!existingNameList.contains(((Label) (tags_hb.getChildren().get(i))).getText())) {
          existingNameList.add(((Label) (tags_hb.getChildren().get(i))).getText());
        }
      }
    }

    try {
      tags_fp.getChildren().clear();
      ResultSet set = connectionDB.getConnection().createStatement()
        .executeQuery("SELECT DISTINCT name FROM tags WHERE name LIKE '%" + tags_tf.getText().toLowerCase() + "%'");
      while (set.next()) {
        if (!existingNameList.contains(set.getString("name")) && tags_fp.getChildren().size() <= 30) {
          new TagBubble(tags_fp, tags_hb, set.getString("name"), tags_tf);
        }
      }
    } catch (SQLException exception) {
      System.out.println(exception);
    }
  }

  public void OnTagSearch(KeyEvent key) throws IOException {
    updateCategories();
    var a = key.getCode();
    if (a.getName() == KeyCode.ENTER.getName()) {
      var search = tags_tf.getText();
      if (!checkIfTagExists(search)) {
        var tempTag = new TagBubble(tags_fp, tags_hb, search, tags_tf);
        tempTag.moveToTarget();
        updateCategories();
      }
    }
  }

  // Checks if the tag list contains a tag with the given name
  public boolean checkIfTagExists(String text) {
    tags_hb.getChildren().size();
    for (Node a : tags_hb.getChildren()) {
      if (a instanceof Label && ((Label) a).getText().equals(text)) {
        return true;
      }
    }
    return false;
  }

  // Checks if servigs checkbox changes
  private void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    if (!newValue.matches("\\d*")) {
      ingredient_portion_count_tb.setText(newValue.replaceAll("[^\\d]", ""));
      return;
    }
    for (var a : mIngredients) {
      a.SetPortion(Integer.parseInt(newValue));
    }
  }


  // Updates the Ingredient box
  public void UpdateIngredientNameComboBox(String search) {
    try {
      ingredient_name.getItems().clear();

      var result = SqlUpdateRecipeIngredientSearch(search);
      for (var b : result) {
        ingredient_name.getItems().add(b);
      }

    } catch (SQLException sql) {
      System.out.print(sql.getMessage());
    }
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

  // Button to add an ingredient
  public void AddIngredientCallbackBtn(ActionEvent e) throws IOException {

    if (this.ingredient_amount.getText().length() <= 0) { // Checks if amount is correct
      return;
    }

    if (this.ingredient_unit.getSelectionModel().getSelectedItem().toString().length() <=
      0) { // Checks if selection is correct
      return;
    }

    for (var a : mIngredients) {

      if (this.ingredient_name.getEditor().getText().equals(a.getName()) ||
        ingredient_name.getEditor().getText().length() <= 0) { // Checks if name is existant
        return;
      }

    }
    mIngredients.add(new Ingredient(this.ingredient_grid, this.ingredient_name.getEditor().getText(),
      Integer.parseInt(this.ingredient_amount.getText()),
      this.ingredient_unit.getSelectionModel().getSelectedItem().toString()));
  }

  // *** SQL FUNCTIONS BEGINING ***

  // Adds all the ingredients from the list to a given recipe_id
  public void SubmitRecipeIngredientList(int recipe_id) {
    for (var a : this.mIngredients) {
      try {
        var id = SqlGetIngredientId(a.getName());
        if (id == -1) { // Jumba dumba.
          SqlAddIngredient(a.getName());
          id = SqlGetIngredientId(a.getName());
        }
        SqlAddRecipeIngredient(recipe_id, id, a.getAmount(), a.getUnit(), a.getPortion());
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  // Used to insert a tag into the database to a specific recipe
  public void SqlAddTag(int recipeId, String name) throws SQLException {
    connectionDB.getConnection().createStatement()
      .execute("INSERT INTO tags(recipe_id,name) VALUES('%s','%s')".formatted(recipeId, name));
  }

  // Add an ingredient to database.
  public void SqlAddIngredient(String name) throws SQLException {
    // Send sql add ingredient by name query
    connectionDB.getConnection().createStatement()
      .execute("INSERT INTO ingredients(name) VALUES('%s')".formatted(name));
  }

  // Get ingredient id from database.
  public int SqlGetIngredientId(String name) throws SQLException {
    var result = connectionDB.getConnection().createStatement()
      .executeQuery("SELECT * FROM ingredients Where name = '%s'".formatted(name));
    while (result.next()) {
      return result.getInt("id");
    }
    return -1;
  }

  // Add Recipe Ingredient for recipe.
  public void SqlAddRecipeIngredient(int recipeId, int ingredientId, int amount, String unit, int portions)
    throws SQLException {
    connectionDB.getConnection().createStatement().execute(
      "INSERT INTO recipe_ingredients(recipe_id,ingredient_id, amount, unit, portions) VALUES('%s','%s','%s','%s','%s')".formatted(
        recipeId, ingredientId, amount, unit, portions));
  }

  // Returns a String List of Ingredients
  public List<String> SqlUpdateRecipeIngredientSearch(String search) throws SQLException {

    var result = connectionDB.getConnection().createStatement()
      .executeQuery("SELECT * FROM ingredients WHERE name LIKE '%" + search + "%'");
    List<String> ingredientNames = new ArrayList<>();
    while (result.next()) {
      ingredientNames.add(result.getString("name"));
    }
    return ingredientNames;
  }


  // *** SQL FUNCTIONS ENDING ***

}

