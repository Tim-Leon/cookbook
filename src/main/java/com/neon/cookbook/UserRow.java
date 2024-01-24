package com.neon.cookbook;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class UserRow {

  Label idLabel;
  Label usernameLabel;
  Label nicknameLabel;
  Label passwordLabel;
  Label roleLabel;
  Label deleteLabel;

  public UserRow(GridPane grid, String id, String username, String nickname, String role) {
    this.idLabel = new Label(id);
    this.usernameLabel = new Label(username);
    this.nicknameLabel = new Label(nickname);
    this.passwordLabel = new Label("******");
    this.roleLabel = new Label(role);
    this.deleteLabel = new Label("DELETE");
    grid.addRow(grid.getRowCount(), idLabel, nicknameLabel, usernameLabel, passwordLabel, roleLabel, deleteLabel);
    GridPane.setHalignment(idLabel, HPos.CENTER);
    GridPane.setHalignment(usernameLabel, HPos.CENTER);
    GridPane.setHalignment(nicknameLabel, HPos.CENTER);
    GridPane.setHalignment(passwordLabel, HPos.CENTER);
    GridPane.setHalignment(roleLabel, HPos.CENTER);
    GridPane.setHalignment(deleteLabel, HPos.CENTER);

    // Ititial admin cannot be modified by others
    if (!id.equals("1") || id.equals("1") && Context.getContext().mUserId == 1) {
      // Nickname
      nicknameLabel.setTextFill(Color.NAVY);
      nicknameLabel.setUnderline(true);
      nicknameLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
          AdminPanelInteractions.modifyNickname(id, nickname);
        }
      });

      // Username
      usernameLabel.setTextFill(Color.NAVY);
      usernameLabel.setUnderline(true);
      usernameLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
          AdminPanelInteractions.modifyUsername(id, username);
          ;
        }
      });

      // Password
      passwordLabel.setTextFill(Color.NAVY);
      passwordLabel.setUnderline(true);
      passwordLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
          AdminPanelInteractions.modifyPassword(id);
        }
      });

      // Role
      roleLabel.setTextFill(Color.NAVY);
      roleLabel.setUnderline(true);
      roleLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
          AdminPanelInteractions.modifyRole(id);
        }
      });

      // Delete
      deleteLabel.setTextFill(Color.RED);
      deleteLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
          AdminPanelInteractions.deleteUser(id);
        }
      });
    }
  }

}
