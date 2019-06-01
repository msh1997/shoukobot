package shoukobot.modules.customReactions;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed.Field;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static shoukobot.services.Embeds.sendEmbed;

public class CustomEventHandler {

    private HashMap<String, String> commandMap = new HashMap<>();

    public CustomEventHandler() {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("resources/CustomCommands.json")) {
            //Read JSON file
            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            commandMap = (HashMap) obj;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean containsCustomEvent(String customEvent) {
        return commandMap.containsKey(customEvent);
    }

    public void customEvent(String message, MessageReceivedEvent event) {

        String reaction = commandMap.get(message);
        Pattern imageLink = Pattern.compile("(.*)(https://)(.*)(.jpg|.jpeg|.png|.gif)(.*)");
        Matcher m = imageLink.matcher(reaction);
        if (m.matches()) {

            String url = "";
            StringBuilder msg = new StringBuilder();

            String[] messages = reaction.split(" ");

            for (String part : messages) {
                if (part.startsWith("https://")) {
                    url = part;
                } else {
                    msg.append(part).append(" ");
                }
            }

            sendEmbed(msg.toString(), url, null, new Color(0, 255, 0), event);
        } else {
            event.getChannel().sendMessage(reaction).queue();
        }
    }

    public void listCustomCommands(MessageReceivedEvent event) {
        StringBuilder strBuilder = new StringBuilder();
        int i = 0;
        for (Map.Entry<String, String> entry : commandMap.entrySet()) {
            String key = entry.getKey();
            strBuilder.append("`#" + i + "` ").append(key).append("\n");
            i++;
        }
        String reactionList = strBuilder.toString();

        Field field = new Field("Custom Reaction List:", reactionList, true);
        sendEmbed(field, new Color(0, 255, 0), event);
    }

    public void addCustomCommand(String msg, MessageReceivedEvent event) {
        String[] splitMsg = msg.split("\"");

        if (containsCustomEvent(splitMsg[1])) {
            event.getChannel().sendMessage("That reaction already exists.").queue();
            return;
        }

        List<Message.Attachment> attachmentList = event.getMessage().getAttachments();

        if (splitMsg.length > 2 && attachmentList.size() == 0) {
            commandMap.put(splitMsg[1], splitMsg[2].substring(1));
        } else if (splitMsg.length < 3 && attachmentList.size() > 0) {
            String url = "";
            for (Message.Attachment a : attachmentList) {
                if (a.isImage()) {
                    url += a.getUrl() + " ";
                }
            }

            commandMap.put(splitMsg[1], url);
        } else {
            String url = "";
            if (attachmentList.size() > 0) {
                for (Message.Attachment a : attachmentList) {
                    if (a.isImage()) {
                        url += a.getUrl() + " ";
                    }
                }
            }

            commandMap.put(splitMsg[1], url + splitMsg[2].substring(1));
        }

        JSONObject obj = (JSONObject) commandMap;
        try (FileWriter file = new FileWriter("resources/CustomCommands.json")) {

            file.write(obj.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        event.getChannel().sendMessage("Reaction successfully added.").queue();
    }

    public void deleteCustomCommand(String msg, MessageReceivedEvent event) {
        int idx = msg.indexOf("delete");
        String substr = msg.substring(idx + "delete".length() + 1);
        String result = commandMap.remove(substr);

        JSONObject obj = (JSONObject) commandMap;
        try (FileWriter file = new FileWriter("resources/CustomCommands.json")) {

            file.write(obj.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (result == null)
            event.getChannel().sendMessage("Reaction did not exist.").queue();
        else
            event.getChannel().sendMessage("Reaction successfully deleted.").queue();
    }

    public void customCommandEvent(String[] messages, String msg, MessageReceivedEvent event) {
        if (messages.length < 2) return;
        switch (messages[1]) {
            case "list":
                listCustomCommands(event);
                break;
            case "add":
                addCustomCommand(msg, event);
                break;
            case "delete":
            case "remove":
                deleteCustomCommand(msg, event);
                break;
        }
    }
}

