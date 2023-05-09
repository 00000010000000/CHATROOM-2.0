package ServerClient;

import ServerClient.Message.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.*;
import java.io.IOException;
import java.net.*;
import java.util.ResourceBundle;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.JdbcUtils;

import static ServerClient.Message.MessageType.EMOJI;

public class ChatController implements Initializable{
    @FXML
    private TextArea ta;
    private Client clt;
    @FXML
    private Button send;
    @FXML
    private Button Voice;
    @FXML
    private Button History;
    @FXML
    private  ScrollPane scrollPane;
    @FXML
    private  VBox chatBoxes;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ChoiceBox<String> emoji;
    final  static int ServerPort = 1234;
    public static String id_str = "";
    public static String username;
    public static Stage stage;
    public static Scene scene;
    static boolean scrollToBottom;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


    //read message in textarea
    //run the sendString function
    //clean the textarea
    @FXML
    private void sendMessage()throws IOException,NullPointerException{
        String message = ta.getText();
        Message newMessage = new Message();
        newMessage.setName(id_str);
        if(!message.isEmpty()){
            newMessage.setType(MessageType.STRING);
            newMessage.setMsg(message);
            Client.sendString(newMessage);
            ta.clear();
        }else {
            System.out.println("EMPTY");
        }
    }

    //get the chat history from database
    @FXML
    private void getHistory(){
        Message historyMessage = new Message();
        Bubble historyBubble = new Bubble();
        String history = JdbcUtils.getHistory();
        historyMessage.setMsg(history);
        historyBubble.setBubble(history,historyBubble);

        chatBoxes.setLayoutX(0);
        chatBoxes.setLayoutY(0);
        chatBoxes.getChildren().add(historyBubble);
        chatBoxes.setAlignment(Pos.BOTTOM_LEFT);
        scrollPane.setLayoutX(0);
        scrollPane.setLayoutY(0);
        scrollToBottom = true;
        scrollPane.setContent(chatBoxes);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVvalue(1);
        scrollPane.vvalueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (scrollToBottom) {
                    scrollPane.setVvalue(scrollPane.getVmax());
                    scrollToBottom = false;
                }
            }
        });
    }


    // show message on the chat view in the form of "id: message"
    public void send(Message message){
        System.out.println("Send message: " + message);
        Bubble messageBubble = new Bubble();
        messageBubble.getName(message.getName());
        if(message.getType() == MessageType.VOICE) {
            messageBubble.setBubble(message.getName()+" Send a voice message!!", messageBubble);
//            Button btn = new Button();
//            messageBubble.addButton(messageBubble,btn);
//            btn.addEventHandler(MouseEvent.ANY, event -> {
//                if(event.getEventType() == MouseEvent.MOUSE_CLICKED){
//                    VoicePlayBack.playAudio(message.getVoiceMsg());
//                }
//            });
            VoicePlayBack.playAudio(message.getVoiceMsg());
            JdbcUtils.History(message.getName(),"Voice Message");
        }
        else if(message.getType() == MessageType.STRING) {
            String msg = message.getMsg();
            messageBubble.setBubble(msg, messageBubble);
            JdbcUtils.History(message.getName(),msg);
        }
             System.out.println(messageBubble);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                chatBoxes.getChildren().add(messageBubble);
            }
        });

            chatBoxes.setAlignment(Pos.BOTTOM_LEFT);
            scrollPane.setLayoutX(0);
            scrollPane.setLayoutY(0);
            scrollToBottom = true;
            scrollPane.setContent(chatBoxes);
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            scrollPane.setVvalue(1);
            scrollPane.vvalueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    if (scrollToBottom) {
                        scrollPane.setVvalue(scrollPane.getVmax());
                        scrollToBottom = false;
//                        System.out.println(123456);
                    }
                }
            });
        System.out.println(100000);
    }


    //record the voice
    public void recordVoiceMessage() throws IOException{
        if(VoiceUtil.isRecording()){
            Platform.runLater(() -> {
                Voice.setStyle("-fx-background-color: #4d0f0b");
            });
            VoiceUtil.setRecording(false);
        } else {
            Platform.runLater(() -> {
                Voice.setStyle("-fx-background-color:  #cd5c5c");
            });
            //miss  platform.runLater
            voiceRecorder.captureAudio();
        }
    }


    @FXML
    public void Emoji(){
        PictureBubble PB = new PictureBubble();
        Message emojiMessage = new Message();
        emojiMessage.setType(EMOJI);
        String EmojiName = emoji.getValue();
        if(EmojiName == "melting-face"){
            PB.setMFEmojiImage("melting-face.jpg");
        }else if(EmojiName == "question-face"){
            PB.setMFEmojiImage("question-face.jpg");
        }else if(EmojiName == "salute-emoji"){
            PB.setMFEmojiImage("salute-emoji.webp");
        }else if (EmojiName == "saluting-face"){
            PB.setMFEmojiImage("saluting-face.webp");
        }
        chatBoxes.setLayoutX(0);
        chatBoxes.setLayoutY(0);
        chatBoxes.getChildren().add(PB);
        chatBoxes.setAlignment(Pos.BOTTOM_LEFT);
        scrollPane.setLayoutX(0);
        scrollPane.setLayoutY(0);
        scrollToBottom = true;
        scrollPane.setContent(chatBoxes);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVvalue(1);
        scrollPane.vvalueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (scrollToBottom) {
                    scrollPane.setVvalue(scrollPane.getVmax());
                    scrollToBottom = false;
                }
            }
        });
    }
}
