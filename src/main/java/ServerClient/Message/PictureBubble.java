package ServerClient.Message;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.BufferedReader;

public class PictureBubble extends VBox {
    String emojiName;
    Image emoji;
    ImageView imageView;
    public PictureBubble(){
        //emoji = new Image(emojiName);
        imageView = new ImageView(emoji);
        setWidth(60);
        setHeight(60);
        setBackground(new Background(new BackgroundFill(Color.PINK,null,null)));
    }

    public void setEmojiName(String name){
        emojiName = name;
    }

    public void setMFEmojiImage(String name){
        PictureBubble PB = new PictureBubble();
        emoji = new Image(getClass().getClassLoader().getResource(name).toString());
        PB.imageView.setImage(emoji);
    }
}
