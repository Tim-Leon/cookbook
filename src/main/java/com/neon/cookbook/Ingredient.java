package com.neon.cookbook;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class Ingredient {
  private String mName;
  private int mAmount;
  private String mUnit;
  private Label lname;
  private Label lamount;
  private Label lunit;
  private int mPortion = 1;
  private VBox mVBox;


  // Ingredient constructor for gridpane
  public Ingredient(GridPane grid, String name, int amount, String unit) {
    this.mAmount = amount;
    this.mUnit = unit;
    this.mName = name;
    //ColumnConstraints a();
    lname = new Label(this.mName);
    lamount = new Label(Integer.toString(this.mAmount));
    lunit = new Label(this.mUnit);
    grid.addRow(grid.getRowCount(), lname, lamount, lunit);
  }

  void SetAmount(int amount) {
    mAmount = amount;
    lamount.setText(Integer.toString(mAmount));
  }

  void SetPortion(int portion) {
    mPortion = portion;
    lamount.setText(Integer.toString(mAmount * mPortion));
  }

  // Ingredient for weekly dinner list
  public Ingredient(VBox parent, String name, int amount, String unit) {
    //set all values
    this.mName = name;
    this.mAmount = amount;
    lname = new Label();
    lamount = new Label();
    mVBox = new VBox();
    mVBox.setPrefHeight(60);
    mVBox.setPrefWidth(427);
    mVBox.setStyle(
      "-fx-background-color: #ffffeb; -fx-border-color: #deded9; -fx-border-radius: 20; -fx-background-radius: 20; -fx-padding: 5; -fx-border-style: solid outside; -fx-border-width: 3; -fx-border-insets:2;");
    lname.setText(name);
    lname.setAlignment(Pos.CENTER);
    lname.setStyle("-fx-font-weight: bold;");
    lname.setPrefHeight(18);
    lname.setPrefWidth(332);


    lamount.setText(amount + unit);
    lamount.setPrefHeight(105);
    lamount.setPrefWidth(321);
    lamount.setWrapText(true);
    lamount.setAlignment(Pos.CENTER_LEFT);


    Button remove_button = new Button("\ud83d\uddd1"); //wastebasket char
    remove_button.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        //delete ingredient from shoppinglist
        try {
          ResultSet ingredientId = connectionDB.getConnection().createStatement().executeQuery(
            "SELECT id FROM ingredients WHERE name =\"" + name + "\"");
          ingredientId.next();
          PreparedStatement favoriteDelete = connectionDB.getConnection().prepareStatement(
            "DELETE FROM shoppinglist WHERE ingredient_id =" + ingredientId.getInt("id") + " AND user_id = " +
              Context.getContext().mUserId);
          favoriteDelete.execute();
          parent.getChildren().remove(mVBox);
        } catch (SQLException e1) {
          System.out.println("Remove ingredient failed!");
        }
      }
    });
    remove_button.setAlignment(Pos.CENTER_RIGHT);
    // add ingredients to VBox and then to parent VBox
    mVBox.getChildren().add(lname);
    mVBox.getChildren().add(lamount);
    mVBox.getChildren().add(remove_button);
    parent.getChildren().addAll(mVBox);
  }

  public String getName() {
    return mName;
  }

  public int getAmount() {
    return mAmount;
  }

  public String getUnit() {
    return mUnit;
  }

  public int getPortion() {
    return mPortion;
  }
}