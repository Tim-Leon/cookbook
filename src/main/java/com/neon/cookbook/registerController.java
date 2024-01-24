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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class registerController {


  @FXML
  private TextField nicknameTextField;
  @FXML
  private TextField usernameTextField;
  @FXML
  private TextField passwordTextField;
  @FXML
  private TextField confirmPasswordTextField;
  @FXML
  private Label notificationLabel;

  private Stage stage;
  private Scene scene;
  private Parent root;

  public void register(ActionEvent e) throws IOException, SQLException {

    String nickname = nicknameTextField.getText();
    String username = usernameTextField.getText();
    String password = passwordTextField.getText();
    String confirmPassword = confirmPasswordTextField.getText();
    Connection db = connectionDB.getConnection();
    ResultSet set = db.createStatement()
      .executeQuery("SELECT Count(username) AS result From users Where username = '%s'".formatted(username));
    set.next();
    if (Integer.parseInt(set.getString("result")) != 0) {
      displayNotification("User already exists");
      return;
    }
    if (!password.equals(confirmPassword)) {
      displayNotification("Passwords dont match");
      return;
    }
    if (password.length() < 2) {
      displayNotification("Password too short");
      return;
    }

    db.createStatement().execute(
      "INSERT INTO users (username, password, role, display_name) VALUES ('%s', '%s', 'USER', '%s')".formatted(username,
        password, nickname));

    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("log-in.fxml"));
    root = fxmlLoader.load();
    LogInController logInController = fxmlLoader.getController();
    logInController.displayNotification("Succesfully registered");
    stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();

  }

  //REGISTER
  public void displayNotification(String name) {
    notificationLabel.setText(name);
  }

  public void registerToLogIn(ActionEvent e) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("log-in.fxml"));
    root = fxmlLoader.load();
    stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}