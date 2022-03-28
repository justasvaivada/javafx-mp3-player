package com.justasvaivada.javafxsimplelogin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class LoginController {

    @FXML
    private TextField usernameTf;
    @FXML
    private PasswordField passwordPf;
    @FXML
    private Button loginBtn;
    @FXML
    private BorderPane borderid;
    @FXML
    private TextField pathTf;


    private Parent root;


    @FXML
    private void handleButtonAction() {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) borderid.getScene().getWindow();
        File file = directoryChooser.showDialog(stage);
        if (file != null) {
            pathTf.setText(file.getAbsolutePath());
        }
    }


    @FXML
    private void initialize() {
        loginBtn.setOnAction(event -> {
            String path = pathTf.getText();
            System.out.println(path);
            Boolean check = validateLoginDetails();
            if (check) {
                SceneController sceneController = new SceneController();
                sceneController.setPathVar(path);
                try {
                    sceneController.switchScene("player-view.fxml", event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Boolean authenticate = false;

    public TextField getUsernameTf() {
        return usernameTf;
    }

    public PasswordField getPasswordPf() {
        return passwordPf;
    }

    public String getUsername() {
        return "root";
    }

    public String getPassword() {
        return "toor";
    }

    public Boolean getAuthenticate() {
        return authenticate;
    }

    public void setAuthenticate(Boolean authenticate) {
        this.authenticate = authenticate;
    }

    public Boolean validateLoginDetails () {
        String userTf = getUsernameTf().getText();
        String passPf = getPasswordPf().getText();

        setAuthenticate(Objects.equals(userTf, getUsername()) || Objects.equals(passPf, getPassword()));

        return getAuthenticate();
    }
}