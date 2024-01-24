package com.neon.cookbook;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class IndexController {

  @FXML
  private Label userDisplay;


  @FXML
  private Button okayButton;

  public void displayName(String name) {
    userDisplay.setText("Welcome " + name + "!");
  }

  public void logOut(ActionEvent e) throws IOException {

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Log out");
    alert.setHeaderText("You're about to logout!");
    alert.setContentText("Do want to log out ?");
    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    stage.getIcons().add(new Image("favicon.png")); // To add an icon
    if (alert.showAndWait().get() == ButtonType.OK) {
      Context.removeContext(); // Removes the current user id
      FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("log-in.fxml"));
      Parent root = fxmlLoader.load();
      stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    }

  }

  public void buttonToCookBook(ActionEvent e) throws IOException {
    okayButton.setDefaultButton(true);

    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Navigator.fxml"));
    Parent root = fxmlLoader.load();
    Navigator navigatorController = fxmlLoader.getController();
    navigatorController.displayName(Context.getContext().mUsername);
    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.centerOnScreen();
    stage.setResizable(false);
    stage.show();
  }

}
