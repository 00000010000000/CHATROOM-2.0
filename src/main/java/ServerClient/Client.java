package ServerClient;

import ServerClient.Message.Message;
import ServerClient.Message.MessageType;
import javafx.scene.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.StringTokenizer;
import ServerClient.*;
import javafx.scene.control.TextArea;
import org.example.LoginController;

import static ServerClient.Server.logger;

public class Client implements Runnable{
    public static String name;
    private ObjectInputStream dis;
    private static ObjectOutputStream dos;
    private OutputStream outputStream;
    private InputStream inputStream;
    private Socket s;
    //ChatController ct;
    static TextArea ta;
    public ChatController ctr;
    static int ServerPort = 1234;
    public String hostname = "localhost";

    // create Client
    public Client(String hostname, int ServerPort, String name, ChatController ctr1) {
        this.hostname = hostname;
        this.ServerPort = ServerPort;
        Client.name = name;
        this.ctr = ctr1;
    }

    //get the message and send it to server
    public void run(){
        try{
            s = new Socket(hostname, ServerPort);
            //LoginController.getInstance().showScene();
            outputStream = s.getOutputStream();
            dos = new ObjectOutputStream(outputStream);
            inputStream = s.getInputStream();
            dis = new ObjectInputStream(inputStream);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.error("Could not Connect");
        }
        logger.info("Connection accept " + s.getInetAddress() + ": " + s.getPort());

        try{
            //need to set username, msg, type to message
            logger.info("Socket in and out ready!");

            while (s.isConnected()){
                Message message = (Message) dis.readObject();
                System.out.println(message);

                if(message != null){
                    logger.debug("Message received: " + message.getMsg() + " MessageType: " + message.getType() + message.getName());
                    switch (message.getType()){
                        case STRING:
                            ctr.send(message);
                            System.out.println(22222);
                            break;
                        case VOICE:
                            ctr.send(message);
                            break;
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
            //logout scene
        }
    }

    // function for send voice message
    public static void sendVoiceMessage(byte[] audio) throws IOException{
        Message newMessage = new Message();
        newMessage.setType(MessageType.VOICE);
        newMessage.setVoiceMsg(audio);
        newMessage.setName(Client.name);
        dos.writeObject(newMessage);
        dos.flush();
    }

    // function for send String message
    public static void sendString(Message msg) throws IOException{
        System.out.println("msg is "+msg);
        Message newMessage = msg;
        dos.writeObject(newMessage);
        dos.flush();
    }

    }

