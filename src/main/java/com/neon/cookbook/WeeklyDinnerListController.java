package com.neon.cookbook;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;


public class WeeklyDinnerListController implements Initializable {
  @FXML
  private VBox weeklyDinnerList;
  @FXML
  private VBox shoppingList;
  @FXML
  private MenuButton weekDropDown;
  @FXML
  private Label shoppingListWeek;


  private int user_Id = Context.getContext().mUserId;
  private Parent root;

  private ArrayList<Recipe> recipes = new ArrayList<Recipe>();
  private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();


  /**
   * Button to return to search scene.
   */
  public void backButton(ActionEvent e) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("search.fxml"));
      root = fxmlLoader.load();
      Context c = Context.getContext();
      c.showSpace.getChildren().removeAll();
      c.showSpace.getChildren().setAll(root);

      // if something goes wrong 
    } catch (IOException e1) {
      System.out.println("Error loading search.fxml");
    }
  }

  /**
   * Update the weekly dinnerlist.
   */
  public void updateWeeklyDinnerList() {
    //remove all recipes from the list, to add new ones without duplicates
    weeklyDinnerList.getChildren().clear();
    recipes.clear();

    try {
      //clear the dropdown
      weekDropDown.getItems().clear();

      ResultSet set = connectionDB.getConnection().createStatement().executeQuery(
        "SELECT * FROM recipes INNER JOIN weeklydinnerlist ON weeklydinnerlist.recipe_id = recipes.id WHERE weeklydinnerlist.user_id = '" +
          user_Id + "'");
      ArrayList<String> listWithWeeks = new ArrayList<>();
      while (set.next()) {
        byte[] pixelBytes = set.getBytes("image");
        InputStream is = new ByteArrayInputStream(pixelBytes);
        ArrayList<String> tags = new ArrayList<String>();
        recipes.add(new Recipe(weeklyDinnerList, set.getString("name"), set.getString("summary"), new Image(is), tags,
          set.getString("date"), set.getInt("dinnerid"), this));

        //add the weeks to a list for the dropdown
        if (!listWithWeeks.contains(set.getString("week"))) {
          listWithWeeks.add(set.getString("week"));
        }
      }
      //sort the list of weeks
      Collections.sort(listWithWeeks);
      //add the weeks in dropdown menu
      ToggleGroup toggleGroup = new ToggleGroup();
      for (String i : listWithWeeks) {
        RadioMenuItem m = new RadioMenuItem(i);
        m.visibleProperty();
        m.setOnAction(event -> {
          weekDisplayer(m, toggleGroup);
        });
        weekDropDown.getItems().add(m);
      }

    } catch (SQLException es) {
      System.out.print("ERROR loading your weekly dinner recipes... Please try again!");

    }
  }

  /**
   * Update the shoppinglist, get the ingredients
   */
  public void updateShoppingList() {
    // label to clearify why there are duplicates and why all weeks shoppinglist shows
    shoppingListWeek.setText("All weeks");
    shoppingList.getChildren().clear();
    ingredients.clear();

    try {
      ResultSet set = connectionDB.getConnection().createStatement()
        .executeQuery("SELECT * FROM shoppinglist WHERE shoppinglist.user_id = '" + user_Id + "'");
      //use set1 to get name of ingredient
      ResultSet set1 = connectionDB.getConnection().createStatement().executeQuery(
        "SELECT * FROM ingredients INNER JOIN shoppinglist ON ingredients.id = shoppinglist.ingredient_id");

      while (set.next() && set1.next()) {
        ingredients.add(
          new Ingredient(shoppingList, set1.getString("name"), set.getInt("amount"), set.getString("unit")));
      }

    } catch (SQLException es) {
      System.out.print("ERROR loading the ingredients correctly");
    }
  }

  /**
   * method that runs when scene is loaded.
   * updates dinnerlist and shopping list with content of database.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    //runs when enters scene
    clearEveryNewYear();
    updateWeeklyDinnerList();
    updateShoppingList();
  }

  /**
   * Set on action for the radiomenuitems for the dropdown.
   * update dinnerlist and ahoppinglist for specific week.
   */
  public void weekDisplayer(RadioMenuItem m, ToggleGroup toggleGroup) {
    //if a week is selected
    if (m.isSelected()) {
      m.setToggleGroup(toggleGroup);

      //empty lists
      weekDropDown.setText(m.getText());
      weeklyDinnerList.getChildren().clear();
      shoppingList.getChildren().clear();
      ingredients.clear();
      recipes.clear();
      try {
        ResultSet sResultSet = connectionDB.getConnection().createStatement().executeQuery(
          "SELECT * FROM recipes INNER JOIN weeklydinnerlist ON weeklydinnerlist.recipe_id = recipes.id WHERE weeklydinnerlist.user_id = '" +
            user_Id + "' AND weeklydinnerlist.week ='" + m.getText() + "'");
        while (sResultSet.next()) {
          byte[] pixelBytes = sResultSet.getBytes("image");
          InputStream is = new ByteArrayInputStream(pixelBytes);
          ArrayList<String> tags = new ArrayList<String>();
          //add the recipes
          recipes.add(
            new Recipe(weeklyDinnerList, sResultSet.getString("name"), sResultSet.getString("summary"), new Image(is),
              tags, sResultSet.getString("date"), sResultSet.getInt("dinnerid"), this));
        }
        try {
          shoppingListWeek.setText("Week " + m.getText());
          ResultSet ingredientForWeek = connectionDB.getConnection().createStatement().executeQuery(
            "SELECT * FROM shoppinglist WHERE shoppinglist.user_id = '" + user_Id + "' AND week = '" + m.getText() +
              "'");
          ResultSet ingredientName = connectionDB.getConnection().createStatement().executeQuery(
            "SELECT * FROM ingredients INNER JOIN shoppinglist ON ingredients.id = shoppinglist.ingredient_id WHERE week = '" +
              m.getText() + "'");

          while (ingredientForWeek.next() && ingredientName.next()) {
            ingredients.add(
              new Ingredient(shoppingList, ingredientName.getString("name"), ingredientForWeek.getInt("amount"),
                ingredientForWeek.getString("unit")));
          }

        } catch (Exception e) {
          System.out.println("Error with shoppinglist for specific week");
        }

      } catch (SQLException e) {
        System.out.println("Error with recipes for specific week");
      }
    } else {
      //when week is not chosen...
      weekDropDown.setText("Week");
      updateShoppingList();
      updateWeeklyDinnerList();
    }
  }

  /**
   * Clear weeklydinnerlist from previous years.
   */
  public void clearEveryNewYear() { // Remove recipes from last year. 
    LocalDate date = LocalDate.now();
    try { // get the date from weeklydinnerlist
      ResultSet years = connectionDB.getConnection().createStatement()
        .executeQuery("SELECT * FROM weeklydinnerlist WHERE weeklydinnerlist.date");
      while (years.next()) {
        ArrayList<String> dsa = new ArrayList<>();
        for (String iterable_element : years.getString("date").split("-")) { // get only the year in 202X-XX-XX
          dsa.add(iterable_element);
        }
        int s = Integer.parseInt(dsa.get(0));

        if (s < date.getYear()) { // delete recipes from db
          ResultSet getIngredients = connectionDB.getConnection().createStatement().executeQuery(
            "SELECT * FROM recipe_ingredients WHERE recipe_ingredients.recipe_id = '" + years.getInt("recipe_id") +
              "'");
          while (getIngredients.next()) {
            PreparedStatement removeIngredient = connectionDB.getConnection().prepareStatement(
              "DELETE FROM shoppinglist WHERE ingredient_id = '" + getIngredients.getInt("ingredient_id") +
                "' AND user_id = '" + Context.getContext().mUserId + "'");
            removeIngredient.execute();
          }
          PreparedStatement deleteQuery = connectionDB.getConnection().prepareStatement(
            "DELETE FROM weeklydinnerlist WHERE weeklydinnerlist.recipe_id = " + years.getString("recipe_id") +
              " AND user_id = " + user_Id);
          try {
            deleteQuery.execute();
          } catch (SQLException e) {
            System.out.println("Faild to remove last years recipe");
          }
        }
      }
    } catch (SQLException e1) {
      System.out.println("Chould not select all recipes in weeklydinnerlist");

    }

  }
}
