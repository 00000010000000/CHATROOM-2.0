package ServerClient;

import ServerClient.Message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.DuplicateFormatFlagsException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Handler;

public class Server {
    private static final int PORT = 1234;
    private static final HashMap<String,User> names = new HashMap<>();
    private static HashSet<ObjectOutputStream> writers = new HashSet<>();
    private static ArrayList<User> users = new ArrayList<>();
    static Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws Exception{
        logger.info("The chat server is running");
        ServerSocket listener = new ServerSocket(PORT);

        try{
            while (true){

                new Handler(listener.accept()).start();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            listener.close();
        }
    }

    private static class Handler extends Thread {
        private String name;
        private Socket socket;
        private Logger logger = LoggerFactory.getLogger(Handler.class);
        private User user;
        private ObjectInputStream input;
        private ObjectOutputStream output;
        private InputStream is;
        private OutputStream os;

        public Handler(Socket socket) throws IOException{
            this.socket = socket;
            //通过该函数将socket传过去
        }

        public void run(){
            logger.info("Attempting to connect a user...");
            try{
                is = socket.getInputStream();
                input = new ObjectInputStream(is);
                os = socket.getOutputStream();
                output = new ObjectOutputStream(os);

                Message firstMessage = (Message) input.readObject();
                checkDuplicateUsername(firstMessage);
                writers.add(output);
                write(firstMessage);

                while(socket.isConnected()){
                    Message inputMsg = (Message) input.readObject();
                    System.out.println("step1 "+inputMsg);
                    if(inputMsg != null){
                        logger.info(inputMsg.getType() + " - " + inputMsg.getName() + ": " + inputMsg.getMsg());
                        switch (inputMsg.getType()){
                            case STRING:
                                System.out.println("step2");
                                write(inputMsg);
                                break;
                            case VOICE:
                                write(inputMsg);
                        }
                    }
                }
            }catch (SocketException socketException){
                logger.error("Socket Exception for user " + name);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (DuplicateFormatFlagsException duplicateFormatFlagsException){
                logger.error(" Username : " + name);
            } finally {
                closeConnections();
            }
        }

        //check the users' action in the list
        private synchronized void checkDuplicateUsername(Message firstMessage) throws DuplicateFormatFlagsException{
            logger.info(firstMessage.getName() + " is trying to connect");
            if(!names.containsKey(firstMessage.getName())){
                this.name = firstMessage.getName();
                user = new User();
                user.setName(firstMessage.getName());

                users.add(user);
                names.put(name, user);

                logger.info(name + " has been added to the list");
            } else {
                logger.error(firstMessage.getName() + " is already connected");
                throw new DuplicateFormatFlagsException(firstMessage.getName() + " is already connected");
            }
        }

        private synchronized void closeConnections(){
            logger.debug("closeConnections() method Enter");
            if(name != null){
                names.remove(name);
                logger.info("User: " + name + " has  been removed!");
            }
            if(user != null){
                users.remove(user);
                logger.info("User object: " + user + " has been removed!");
            }
            if(output != null){
                writers.remove(output);
                logger.info("Wruter object: " + user + " has been removed!");
            }
            if(is != null){
                try{
                    is.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if(os != null){
                try{
                    os.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            logger.info("HashMap names: " + names.size() + " writers: " + writers.size() + " usersList size: " + users.size());
            logger.debug("closeConnections() method Exit");
        }

        private void write(Message msg) throws IOException{
            for (ObjectOutputStream writer : writers){
                System.out.println("step3 "+writer);
                msg.setUsers(users);
                msg.setName(name);
                writer.writeObject(msg);
                writer.reset();
            }
        }
    }
}



