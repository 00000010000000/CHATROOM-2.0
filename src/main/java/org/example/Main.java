package org.example;

import ServerClient.ChatController;
import javafx.animation.*;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import static javafx.scene.paint.Color.*;


public class Main extends Application {
    public Stage primarystage;
    @FXML
    public BorderPane bp;
    @FXML
    StackPane sp;

    @Override
    public void start(Stage stage) throws Exception {
        this.primarystage = stage;
        show();
    }

    //show the login window
    public void show() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("loginwindow.fxml")));

        ChatController ct = fxmlLoader.getController();

        Scene scene = new Scene(root, 401.5, 623);
        scene.setRoot(root);
        primarystage.setScene(scene);
        primarystage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
