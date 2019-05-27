package services;

import java.util.ArrayList;

public class MessageParser {

    public String[] parseMessage(String message){
        String[] messages = message.split(" ");
        ArrayList<String> tempMessages = new ArrayList<>();
        for (String s : messages) {
            if(!s.equals(""))
                tempMessages.add(s);
        }
        return tempMessages.toArray(new String[tempMessages.size()]);
    }
}
