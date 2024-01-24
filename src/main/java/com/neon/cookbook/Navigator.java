package com.neon.cookbook;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Navigator implements Initializable {

  @FXML
  private AnchorPane showSpace;
  @FXML
  private Button btnPackages;

  @FXML
  private Label userDisplay;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    btnPackages.setVisible(Context.getContext().mRole.equals("ADMIN"));

    Context.getContext().showSpace = showSpace;
    home();

  }


  // Button that loads the search pane
  public void home() {
    try {
      Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("search.fxml")));
      showSpace.getChildren().removeAll();
      showSpace.getChildren().setAll(fxml);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Button that loads the create recipe pane
  public void createRecipe() {
    try {
      Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("create-recipe.fxml")));
      showSpace.getChildren().removeAll();
      showSpace.getChildren().setAll(fxml);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Button that loads the weekly dinner list pane
  public void weeklyDinnerList() {
    try {
      Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("weeklydinnerlist.fxml")));
      showSpace.getChildren().removeAll();
      showSpace.getChildren().setAll(fxml);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void adminPanel() {
    try {
      Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminpanel.fxml")));
      showSpace.getChildren().removeAll();
      showSpace.getChildren().setAll(fxml);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Log out button


  public void messages() {
    try {
      Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("messagemain.fxml")));
      showSpace.getChildren().removeAll();
      showSpace.getChildren().setAll(fxml);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void help() {
    try {
      Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("help.fxml")));
      showSpace.getChildren().removeAll();
      showSpace.getChildren().setAll(fxml);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void displayName(String name) {
    userDisplay.setText(name);
  }


  public void btnSignout(ActionEvent e) throws IOException {
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

}
