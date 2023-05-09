package org.example;

import ServerClient.ChatController;
import ServerClient.Client;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.security.cert.CertPathBuilder;
import java.util.*;

import org.example.JdbcUtils;

import static ServerClient.ChatController.id_str;
import static javafx.scene.paint.Color.BROWN;

public class LoginController implements Initializable {
    @FXML
    private Button loginbutton;
    @FXML
    private Button signupbutton;
    @FXML
    private TextField ID;
    @FXML
    private PasswordField password;
    @FXML
    private Pane pane;
    @FXML
    private AnchorPane anchorPane;
    private int xpos=10;
    private int ypos=10;
    public ChatController controller;
    Rectangle rect;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int number = 30;
        while (number > 0){
            rectAnimation();
            number--;
        }
    }

    //this function check the input id and password with database; correct -> login
    //get the chat view
    @FXML
    private void handleLogin() throws Exception{
        String id = ID.getText();
        String pw = password.getText();
        System.out.println(id);
        System.out.println(pw);
        boolean isCorrect = JdbcUtils.LogIn(id,pw);
        if(isCorrect) {
            Stage stage = (Stage) loginbutton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("chatview.fxml")));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            controller = loader.<ChatController>getController();
            id_str = id;
            controller.stage = stage;
            controller.scene = scene;
            Client client = new Client("localhost", 1234, id_str, controller);
            Thread thread = new Thread(client);
            thread.start();

            //gx=XXX.getcontroller()  ==>>>clientHandler
            //gx.id_str=id;

            stage.setScene(scene);
            stage.show();
        } else {
            System.out.println("Password incorrect!!!");
        }
    }

    //function for Sign Up button
    //save your information into database
    //jump back to log in window
    @FXML
    private void handleSignUp() throws IOException {
        System.out.println("register bottom clicked");
//        loginBox.setVisible(false);
//        signUpBox.setVisible(true);
        Stage stage=(Stage)signupbutton.getScene().getWindow();

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("signup.fxml"));
        Scene scene = new Scene(root, 401.5,623);
        //    scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        stage.setTitle("login window");
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();
    }




    //function for animation
    public void rectAnimation(){
        Random random = new Random();
        rect = new Rectangle(xpos + random.nextInt(200),ypos + random.nextInt(200),20 + random.nextInt(20),20 + random.nextInt(20));
        rect.setFill(Color.web("#F0F8FF"));
        rect.setOpacity(0.1);
        xpos+=1;
        Path path = createEllipsepath(300,300,80,0);
        path.getElements().add(new MoveTo(200 + random.nextInt(200),10 + random.nextInt(200)));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setPath(path);
        pathTransition.setNode(rect);
        pathTransition.setAutoReverse(true);
        pathTransition.setDuration(Duration.millis(40000));
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(50);
//        pathTransition.play();

        KeyValue moveXAxis = new KeyValue(rect.xProperty(), 300 + random.nextInt(200));
        KeyValue moveYAxis = new KeyValue(rect.yProperty(), 300 + random.nextInt(200));

        KeyFrame keyFrame = new KeyFrame(Duration.millis(10  * 1000), moveXAxis, moveYAxis);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

        pane.getChildren().add(rect);
        pane.getChildren().add(path);
    }

    // function for create a path
    private Path createEllipsepath(double X,double Y,double radius,double rotate) {
    //hand write a circle path
        ArcTo arcto = new ArcTo();
        arcto.setX(X - radius + 1);
        arcto.setY(Y - radius);
        arcto.setXAxisRotation(rotate);

        Path path = new Path();
        path.getElements().addAll(
                new MoveTo(X - radius, Y - radius),
                arcto,
                new ClosePath()
        );
        path.setStroke(Color.DODGERBLUE);
        path.getStrokeDashArray().setAll(5d,5d);
        return path;
    }

}
