package ServerClient.Message;

import ServerClient.User;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
    private String name;
    private String msg;
    private ArrayList<User> users;
    private MessageType type;
    private byte[] voiceMsg;

    public byte[] getVoiceMsg(){
        return voiceMsg;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getMsg(){
        return msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public void setType(MessageType type){
        this.type = type;
    }

    public void setUsers(ArrayList<User> users){
        this.users = users;
    }

    public MessageType getType(){
        return type;
    }

    public void setVoiceMsg(byte[] voiceMsg){
        this.voiceMsg = voiceMsg;
    }
}
