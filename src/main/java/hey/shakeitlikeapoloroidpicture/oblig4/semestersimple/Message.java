package hey.shakeitlikeapoloroidpicture.oblig4.semestersimple;

import java.io.Serializable;

public class Message implements Serializable {
    Integer type;  //GET_LIST, LOG_ON, CHAT_MESSAGE, LOG_OFF.
    String payload;
    String serializedMessage;
    public static final Integer GET_LIST = 0;
    public static final Integer LOG_ON = 1;
    public static final Integer CHAT_MESSAGE = 2;
    public static final Integer LOG_OFF = 3;
    public static final Integer DEBUG_DEBUG=9;


    public Message(Integer type, String payload){
        this.type = type;
        this.payload = payload;
    }

    public String serialize(){
        serializedMessage=type+payload;
        return serializedMessage;
    }

    public void deserialize(String serializedMessageObj){
        Character firstLetter;
        firstLetter = (serializedMessageObj.charAt(0));
        this.type = Integer.parseInt(firstLetter.toString());
        this.payload = serializedMessageObj.substring(1, (serializedMessageObj.length()));
    }

    public Integer getType() {
        return type;
    }

    public String getPayload() {
        return payload;
    }
}
