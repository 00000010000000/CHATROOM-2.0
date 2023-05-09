package ServerClient;

import java.io.Serializable;

public class User implements Serializable {
    String name;
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
