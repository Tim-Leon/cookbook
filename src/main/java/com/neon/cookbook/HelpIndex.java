package com.neon.cookbook;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class HelpIndex implements Initializable {

  @FXML
  private VBox showBox;

  @FXML
  private TextField searchField;

  @FXML
  private ScrollPane scrollPane;

  @FXML
  private Button okayButton;

  private final List<FAQ> articles = Arrays.asList(
    new FAQ("How do I create a recipe?",
      "Click the \"Create recipe\" at the left side of the application and fill in the information."),
    new FAQ("How do I create a weekly dinner list?",
      "When you go into any recipe you click the \"Add to dinner list\" button, choose a day and press submit."),
    new FAQ("How do I remove dinners from my weekly dinner list?",
      "In the weekly dinner you press the \"Trashcan\" icon on the recipe you want to remove."),
    new FAQ("How do I add a favorite recipe?",
      "When you go into any recipe you click the \"Heart\" icon to add it as a favorite."),
    new FAQ("How do I add tags?",
      "When creating a recipe you add tags by writing the tag inside the \"tag\" field and pressing enter, the tags will show up a bit to the right."),
    new FAQ("How do I send messages?",
      "Whe you go into any recipe you click the \"Send message\" button to be welcomed by a big text field to insert your message (if you want to) and usernames to the left to select the user you want to send to."),
    new FAQ("How do I check my messages?",
      "You press the \"Messages\" on the left side, then you will see all the messages you have received and can press \"read more\" to see the whole messages and the \"Go to recipe\" if you want to see the attached recipe."),
    new FAQ("How do I add comments?",
      "When you go into any recipe you click \"Add comment\", then you write your comment and submit."),
    new FAQ("How do I delete a user?",
      "As an admin you can delete a user by going down to the \"Adminpanel\", there you can modify data as well as delete the user as a whole."));


  private void searchEngine() {
    String[] keys = searchField.getText().split(" ");

    List<FAQ> results = articles.stream().filter(article -> {
      for (String word : keys) {
        if (!String.format("""
                %s
            """, article.title()).toLowerCase()
          .contains(word.toLowerCase())) {
          return false;
        }
      }
      return true;
    }).collect(Collectors.toList());

    showBox.getChildren().clear();

    for (FAQ article : results) {
      VBox box = new VBox();

      Text titleText = new Text(article.title());
      titleText.setStyle("-fx-font-size: 22;");
      box.getChildren().add(titleText);

      String body = article.body();
      String truncatedBody = body.substring(0, Math.min(50, body.length()));

      Text bodyElem = new Text(truncatedBody);
      bodyElem.setStyle("-fx-font-size: 14;");

      if (truncatedBody.length() < body.length()) {
        var isOpen = new Object() {
          boolean value = false;
        };

        Hyperlink hyperLink = new Hyperlink("...more");

        hyperLink.setOnAction(e -> {
          if (!isOpen.value) {
            bodyElem.setText(body);
            hyperLink.setText("hide");
          } else {
            bodyElem.setText(truncatedBody);
            hyperLink.setText("...more");
          }
          isOpen.value = !isOpen.value;
        });
        TextFlow string = new TextFlow(bodyElem, hyperLink);
        string.setPadding(new Insets(10, 0, 0, 0));
        box.getChildren().add(string);
      } else {
        TextFlow string = new TextFlow(bodyElem);
        string.setPadding(new Insets(10, 0, 0, 0));
        box.getChildren().add(string);
      }

      showBox.getChildren().add(box);
      VBox.setMargin(box, new Insets(0, 0, 30, 0));
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    scrollPane.getStyleClass().clear();
    searchEngine();
  }

  @FXML
  void SearchButton(ActionEvent event) {
    okayButton.setDefaultButton(true);
    searchEngine();
  }
}
