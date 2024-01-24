package com.neon.cookbook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class DatePickerController {
  private static LocalDate localDate;


  private static int user_id = Context.getContext().mUserId;

  public static void display(String recipeName, int servings, Button dinnerListButton) {
    Stage s = new Stage();
    TilePane r = new TilePane();

    // create a date picker
    DatePicker d = new DatePicker();
    Button closeButton = new Button("Submit");

    // action event
    EventHandler<ActionEvent> close = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        try {
          Connection db = connectionDB.getConnection();
          ResultSet recipeId =
            db.createStatement().executeQuery("SELECT id FROM recipes WHERE name =\"" + recipeName + "\"");
          recipeId.next();
          ResultSet set1 = db.createStatement().executeQuery(
            "SELECT * FROM recipe_ingredients WHERE recipe_ingredients.recipe_id = " + recipeId.getInt("id"));
          try {
            DatePickerController d = new DatePickerController();

            int week = d.getWeekNr();
            String dateForRecipe = d.getLocalDate();
            //reset localdate because static variable 
            localDate = null;

            while (set1.next()) {
              try {
                //if ingredient already in shoppinglist check that unit is same and week is same, if same update amount
                ResultSet set2 = db.createStatement().executeQuery(
                  "SELECT unit, amount, week FROM shoppinglist WHERE ingredient_id ='" +
                    set1.getString("ingredient_id") + "'");
                set2.next();
                if (set1.getString("unit").equals(set2.getString("unit")) && week == (set2.getInt("week"))) {
                  int oldAmount = set2.getInt("amount");
                  int newAmount = set1.getInt("amount") * servings;
                  PreparedStatement modifyShoppinglist = db.prepareStatement(
                    "UPDATE shoppinglist SET amount =" + (oldAmount + newAmount) + " WHERE ingredient_id = " +
                      set1.getString("ingredient_id"));
                  modifyShoppinglist.execute();
                } else {
                  // if ingredient already in shoppinglist but not same week or not same unit add new one
                  PreparedStatement shoppinglist = db.prepareStatement(
                    "INSERT INTO shoppinglist(ingredient_id, user_id, week, amount, unit) VALUES ('" +
                      set1.getString("ingredient_id") + "','" + user_id + "','" + week + "','" +
                      (set1.getInt("amount") * servings) + "','" + set1.getString("unit") + "')");
                  shoppinglist.execute();
                }
              } catch (Exception ee) {
                //if ingredient not in shoppinglist add it
                PreparedStatement shoppinglist = db.prepareStatement(
                  "INSERT INTO shoppinglist(ingredient_id, user_id, week, amount, unit) VALUES ('" +
                    set1.getString("ingredient_id") + "','" + user_id + "','" + week + "','" +
                    (set1.getInt("amount") * servings) + "','" + set1.getString("unit") + "')");
                shoppinglist.execute();
              }


            }
            // add recipe
            PreparedStatement weeklyPreparedStatement = db.prepareStatement(
              "INSERT INTO weeklydinnerlist(user_id, recipe_id, week, date, servings) VALUES('" + user_id + "','" +
                recipeId.getString("id") + "','" + week + "','" + dateForRecipe + "','" + servings + "')");
            weeklyPreparedStatement.execute();
            s.close();
          } catch (Exception error) {
            // date not chosen
            System.out.println(error);
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Please choose a date!");
            errorAlert.showAndWait();
          }

        } catch (SQLException e1) {
          System.out.println("häer");
          System.out.println(e1);
        }
      }
    };
    closeButton.setOnAction(close);

    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        // get the date picker value

        localDate = d.getValue();

      }
    };

    // show week numbers
    d.setShowWeekNumbers(true);

    // when datePicker is pressed
    d.setOnAction(event);
    // add button and label
    r.getChildren().add(d);
    r.getChildren().add(closeButton);

    // create a scene
    Scene sc = new Scene(r, 200, 200);

    // set the scene
    s.setScene(sc);

    s.show();
  }

  public String getLocalDate() {
    String s = localDate.toString();
    return s;
  }

  public int getWeekNr() {
    Locale locale = Locale.GERMANY; // set weeknr
    int weekOfYear = localDate.get(WeekFields.of(locale).weekOfWeekBasedYear());
    return weekOfYear;
  }
}
