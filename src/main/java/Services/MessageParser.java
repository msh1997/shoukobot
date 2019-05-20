package Services;

public class MessageParser {

    public MessageParser(){

    }

    public String[] parseMessage(String message){
        String[] messages = message.split(" ");
        return messages;
    }
}
