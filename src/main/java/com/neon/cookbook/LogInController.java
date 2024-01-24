package com.neon.cookbook;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LogInController {


  @FXML
  private TextField nicknameTextField;
  @FXML
  private TextField usernameTextField;
  @FXML
  private TextField passwordTextField;
  @FXML
  private Label notificationLabel;

  @FXML
  private Button okayButton;

  private Stage stage;
  private Scene scene;
  private Parent root;

  // Log in button
  public void logIn(ActionEvent e) throws IOException, SQLException {

    String username = usernameTextField.getText();
    String password = passwordTextField.getText();

    Connection db = connectionDB.getConnection();
    ResultSet set = db.createStatement().executeQuery("SELECT Count(username) AS counter FROM users Where username = '%s' AND password = '%s'".formatted(username, password));
    set.next();

    // Confirming the log in
    if (set.getString("counter").equals("1")) {

      okayButton.setDefaultButton(true);
      // Once, we confirmed it, we store the user id in the context Object.
      set = db.createStatement().executeQuery("SELECT * FROM users Where username = '%s'".formatted(username));
      set.next();
      Context.getContext().mUsername = username;
      Context.getContext().mUserId = set.getInt("id");
      Context.getContext().mNickname = set.getString("display_name");
      Context.getContext().mRole = set.getString("role");

      // Load the new scene
      FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("index.fxml"));
        root = fxmlLoader.load();
        IndexController indexController =  fxmlLoader.getController();
        indexController.displayName(username);
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } else {
      displayNotification("Wrong login");
    }
  }



  // Register button
  public void logInRegister(ActionEvent e) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("register.fxml"));
    root = fxmlLoader.load();
    stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  // modifies the text on the label
  public void displayNotification(String name) {
    notificationLabel.setText(name);
  }


}
