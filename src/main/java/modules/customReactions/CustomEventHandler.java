package modules.customReactions;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
//import java.io.FileWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class CustomEventHandler {

    private HashMap<String, String> commandMap = new HashMap<>();

    public CustomEventHandler() {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("resources/CustomCommands.json"))
        {
            //Read JSON file
            JSONObject obj = (JSONObject)jsonParser.parse(reader);
            commandMap = (HashMap)obj;

            System.out.println(commandMap);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean containsCustomEvent(String customEvent){
        return commandMap.containsKey(customEvent);
    }

    public void customEvent(String message, MessageReceivedEvent event){
        event.getChannel().sendMessage(commandMap.get(message)).queue();
    }

    public void addCustomCommand(String[] messages, String msg, MessageReceivedEvent event) {
        if(messages[1].equals("add")) {
            if(containsCustomEvent(msg)) {
                event.getChannel().sendMessage("That custom prompt already exists.").queue();
            }
            else {
                String [] splitMsg = msg.split("\"");
                commandMap.put(splitMsg[1], splitMsg[2]);

                JSONObject obj = (JSONObject)commandMap;
                try (FileWriter file = new FileWriter("resources/CustomCommands.json")) {

                    file.write(obj.toJSONString());
                    file.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

        /*if(messages[0].equals("ping")) {
            event.getChannel().sendMessage("pong").queue();
        }

        if(messages[0].equals("echo")) {
            if(messages.length > 1) {
                StringBuilder strBuilder = new StringBuilder();
                for (int i = 1; i < messages.length; i++) {
                    strBuilder.append(messages[i]);
                }
                String msg = strBuilder.toString();
                event.getChannel().sendMessage(msg).queue();
            }
            else {
                event.getChannel().sendMessage("Empty echo arguments.").queue();
            }
        }*/
