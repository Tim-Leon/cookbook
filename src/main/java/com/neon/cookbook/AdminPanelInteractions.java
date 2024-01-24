package com.neon.cookbook;

import java.sql.ResultSet;
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
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdminPanelInteractions {

  // modify nickname method
  public static void modifyNickname(String id, String currentNickname) {
    Stage window = new Stage();
    window.initModality(Modality.APPLICATION_MODAL);
    window.setMinWidth(250);

    Label label = new Label();
    label.setText("Modify Nickname");
    Label warning = new Label();
    warning.setText("");
    warning.setTextFill(Color.RED);
    TextField textField = new TextField();
    textField.setText(currentNickname);
    textField.promptTextProperty().set("New NickName");
    Button closeButton = new Button("Close and Save");
    HBox hBox = new HBox();

    hBox.setAlignment(Pos.CENTER);

    closeButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {

          try {
            // Check if nickname isnt too short
            if (textField.getText().length() < 3) {
              warning.setText("Nickname too short!");
              return;
            } else if (textField.getText().length() > 32) {
              warning.setText("Nickname too long!");
              return;
            }

            ResultSet set = connectionDB.getConnection().createStatement().executeQuery(
              "SELECT Count(*) as counter FROM users WHERE display_name = '%s'".formatted(textField.getText()));
            set.next();

            // Check for duplicates
            if (set.getInt("counter") != 0 && !currentNickname.equals(textField.getText())) {
              warning.setText("Nickname already exists!");
              return;
            }

            // By now, we know that the nickname is valid
            System.out.println(
              "UPDATE users SET display_name = '%s' WHERE display_name = '%s' AND id = %s".formatted(textField.getText(),
                currentNickname, id));
            connectionDB.getConnection().createStatement().execute(
              "UPDATE users SET display_name = '%s' WHERE display_name = '%s' AND id = %s".formatted(textField.getText(),
                currentNickname, id));
          } catch (SQLException e) {
            System.out.print(e);
          }
          window.close();
        }
      }
    );

    hBox.getChildren().addAll(closeButton);
    VBox layout = new VBox(10);
    layout.getChildren().addAll(label, warning, textField, hBox);
    layout.setAlignment(Pos.CENTER);

    Scene scene = new Scene(layout);
    window.setScene(scene);
    window.showAndWait();
  }


  // modify username method
  public static void modifyUsername(String id, String currentUsername) {
    Stage window = new Stage();
    window.initModality(Modality.APPLICATION_MODAL);
    window.setMinWidth(250);

    Label label = new Label();
    label.setText("Modify Username");
    Label warning = new Label();
    warning.setText("");
    warning.setTextFill(Color.RED);
    TextField textField = new TextField();
    textField.setText(currentUsername);
    textField.promptTextProperty().set("New username");
    Button closeButton = new Button("Close and Save");
    HBox hBox = new HBox();

    hBox.setAlignment(Pos.CENTER);

    closeButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {

          try {
            // Check if username isnt too short
            if (textField.getText().length() == 0) {
              warning.setText("Username too short!");
              return;
            } else if (textField.getText().length() > 16) {
              warning.setText("Username too long!");
              return;
            }

            ResultSet set = connectionDB.getConnection().createStatement()
              .executeQuery("SELECT Count(*) as counter FROM users WHERE username = '%s'".formatted(textField.getText()));
            set.next();

            // Check for duplicates
            if (set.getInt("counter") != 0 && !currentUsername.equals(textField.getText())) {
              warning.setText("Username already exists!");
              return;
            }

            // By now, we know that the username is valid
            connectionDB.getConnection().createStatement().execute(
              "UPDATE users SET username = '%s' WHERE username = '%s' AND id = %s".formatted(textField.getText(),
                currentUsername, id));
          } catch (SQLException e) {
            System.out.print(e);
          }
          window.close();
        }
      }
    );

    hBox.getChildren().addAll(closeButton);
    VBox layout = new VBox(10);
    layout.getChildren().addAll(label, warning, textField, hBox);
    layout.setAlignment(Pos.CENTER);

    Scene scene = new Scene(layout);
    window.setScene(scene);
    window.showAndWait();
  }


  // modify password method
  public static void modifyPassword(String id) {
    Stage window = new Stage();
    window.initModality(Modality.APPLICATION_MODAL);
    window.setMinWidth(250);

    Label label = new Label();
    label.setText("New Password");
    Label warning = new Label();
    warning.setText("");
    warning.setTextFill(Color.RED);
    TextField textField = new TextField();
    textField.promptTextProperty().set("New password");
    Button closeButton = new Button("Close and Save");
    HBox hBox = new HBox();

    hBox.setAlignment(Pos.CENTER);

    closeButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {

          try {
            // Check if password isnt too short
            if (textField.getText().length() == 0) {
              warning.setText("Password too short!");
              return;
            } else if (textField.getText().length() > 16) {
              warning.setText("Password too long!");
              return;
            }

            connectionDB.getConnection().createStatement()
              .execute("UPDATE users SET password = '%s' WHERE id = %s".formatted(textField.getText(), id));
          } catch (SQLException e) {
            System.out.print(e);
          }
          window.close();
        }
      }
    );

    hBox.getChildren().addAll(closeButton);
    VBox layout = new VBox(10);
    layout.getChildren().addAll(label, warning, textField, hBox);
    layout.setAlignment(Pos.CENTER);

    Scene scene = new Scene(layout);
    window.setScene(scene);
    window.showAndWait();
  }

  // modify ROLE
  public static void modifyRole(String id) {
    Stage window = new Stage();
    window.initModality(Modality.APPLICATION_MODAL);
    window.setMinWidth(250);

    Label label = new Label();
    label.setText("Modify role");
    HBox hBox = new HBox();
    hBox.setAlignment(Pos.CENTER);

    Button admin = new Button("ADMIN");
    admin.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        try {
          connectionDB.getConnection().createStatement()
            .execute("UPDATE users SET role = 'ADMIN' WHERE id = %s".formatted(id));
        } catch (SQLException e) {
          System.out.println(e.getMessage());
        }
        window.close();
      }
    });


    Button user = new Button("USER");
    user.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        try {
          connectionDB.getConnection().createStatement()
            .execute("UPDATE users SET role = 'USER' WHERE id = %s".formatted(id));
        } catch (SQLException e) {
          System.out.println(e.getMessage());
        }
        window.close();
      }
    });


    hBox.getChildren().addAll(user, admin);
    VBox layout = new VBox(10);
    layout.getChildren().addAll(label, hBox);
    layout.setAlignment(Pos.CENTER);

    Scene scene = new Scene(layout);
    window.setScene(scene);
    window.showAndWait();
  }

  // Delete user
  public static void deleteUser(String id) {
    Stage window = new Stage();
    window.initModality(Modality.APPLICATION_MODAL);
    window.setMinWidth(250);

    Label label = new Label();
    label.setText("Are you sure you want to delete this user?");
    HBox hBox = new HBox(2);
    hBox.setAlignment(Pos.CENTER);

    Button confirm = new Button("Yes");
    confirm.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        try {
          connectionDB.getConnection().createStatement().execute("Delete FROM users WHERE id = %s".formatted(id));
          connectionDB.getConnection().createStatement()
            .execute("Delete FROM favorites WHERE user_id = %s".formatted(id));
          connectionDB.getConnection().createStatement()
            .execute("Delete FROM comment WHERE user_id = %s".formatted(id));
          connectionDB.getConnection().createStatement()
            .execute("Delete FROM shoppinglist WHERE user_id = %s".formatted(id));
          connectionDB.getConnection().createStatement()
            .execute("Delete FROM weeklydinnerlist WHERE user_id = %s".formatted(id));
          connectionDB.getConnection().createStatement().execute("Delete FROM users WHERE id = %s".formatted(id));
          connectionDB.getConnection().createStatement()
            .execute("Delete FROM messages WHERE sender_id = '%s' OR reciever_id = '%s'".formatted(id, id));

          ResultSet set = connectionDB.getConnection().createStatement()
            .executeQuery("Select * FROM recipes WHERE user_id = %s".formatted(id));
          while (set.next()) {
            connectionDB.getConnection().createStatement()
              .execute("Delete FROM tags WHERE recipe_id = %s".formatted(set.getString("id")));
          }
          connectionDB.getConnection().createStatement()
            .execute("Delete FROM recipes WHERE user_id = %s".formatted(id));

        } catch (SQLException e) {
          System.out.println(e.getMessage());
        }
        window.close();
      }
    });


    Button abort = new Button("No");
    abort.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        window.close();
      }
    });


    hBox.getChildren().addAll(confirm, abort);
    VBox layout = new VBox(10);
    layout.getChildren().addAll(label, hBox);
    layout.setAlignment(Pos.CENTER);

    Scene scene = new Scene(layout);
    window.setScene(scene);
    window.showAndWait();
  }

}