package modules.customReactions;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    public void customCommandEvent(String [] messages, String msg, MessageReceivedEvent event) {
        if(messages[1].equals("list")) {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder.append("Custom Reactions:\n");
            for (Map.Entry<String, String> entry : commandMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                strBuilder.append(key + " : " + value + "\n");
            }
            String reactionList = strBuilder.toString();
            event.getChannel().sendMessage(reactionList).queue();
        }
        else if(messages[1].equals("add")) {
            if(containsCustomEvent(msg)) {
                event.getChannel().sendMessage("That reaction already exists.").queue();
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
                event.getChannel().sendMessage("Reaction successfully added.").queue();
            }
        }
        else if(messages[1].equals("delete")) {
            int idx = msg.indexOf("delete");
            String substr = msg.substring(idx + "delete".length() + 1);
            String result = commandMap.remove(substr);

            JSONObject obj = (JSONObject)commandMap;
            try (FileWriter file = new FileWriter("resources/CustomCommands.json")) {

                file.write(obj.toJSONString());
                file.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }

            if(result == null)
                event.getChannel().sendMessage("Reaction did not exist.").queue();
            else
                event.getChannel().sendMessage("Reaction successfully deleted.").queue();
        }
    }
}

