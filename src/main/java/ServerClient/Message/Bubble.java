package ServerClient.Message;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Bubble extends VBox {
    private Rectangle rectangle;
    private Font textFont = Font.font("Arial",12);
    private String message;
    private String name;
    public Bubble(){
        //change rectangle into Vbox
        Text text = new Text(message);
        text.setFont(textFont);
        int textW = (int) text.getLayoutBounds().getWidth();
        int textH = (int) text.getLayoutBounds().getHeight();

        setWidth(textW + 300);
        setHeight(textH + 50);
        setBackground(new Background(new BackgroundFill(Color.web("#e6e8fa"),null,null)));
    }

    public void setBubble(String msg, Bubble bubble){
        String newMessage = name + ": " + msg;
        Text text = new Text(newMessage);
        bubble.getChildren().addAll(text);
    }

    public void getName(String name){
        this.name = name;
    }

    public void addButton(Bubble bub, Button btn){
        btn = new Button();
        btn.setText("V");
        btn.setStyle("-fx-background-color: PINK");
        bub.getChildren().add(btn);
    }
}
