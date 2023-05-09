package org.example;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.stage.StageStyle;
import org.example.JdbcUtils;

public class Signupcontoller {
    @FXML
    private TextField newUserUsername;
    @FXML
    private TextField newID;
    @FXML
    private PasswordField newUserPassword;
    @FXML
    private TextField age;
    @FXML
    private ChoiceBox<String> gender;
    @FXML
    private Button back;
    @FXML
    private Button Register;

    @FXML
    private void handleBack() throws Exception{
        Stage stage = (Stage)back.getScene().getWindow();
        System.out.println("BACK!");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("loginwindow.fxml"));
        Scene scene = new Scene(root,401.5,623);
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleSignUpNewUser() throws Exception {
        String Name = newUserUsername.getText();
        String ID = newID.getText();
        String Password = newUserPassword.getText();
        int Age = Integer.parseInt(age.getText());
        String Gender = gender.getValue();
        JdbcUtils.SignUp(Name,ID,Password,Age,Gender);

        StageShow();
        handleBack();
    }

    public static void StageShow(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Congratulation!");
        alert.setContentText("Add user successfully!!\nPlease back to log in window.");
        alert.initStyle(StageStyle.UTILITY);
        alert.show();
    }

}
