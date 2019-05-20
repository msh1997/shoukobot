package services;

public class MessageParser {

    public String[] parseMessage(String message){
        String[] messages = message.split(" ");
        return messages;
    }
}
