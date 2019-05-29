package modules.customReactions;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            String msg = "";

            String[] messages = reaction.split(" ");

            for (String part : messages) {
                if (part.startsWith("https://")) {
                    url = part;
                } else {
                    msg += part + " ";
                }
            }

            EmbedBuilder eb = new EmbedBuilder();
            MessageBuilder mb = new MessageBuilder();

            Color green = new Color(0, 255, 0);
            eb.setColor(green);

            if (msg.length() > 0) eb.setTitle(msg);

            eb.setImage(url);
            mb.setEmbed(eb.build());

            event.getChannel().sendMessage(mb.build()).queue();
        } else
            event.getChannel().sendMessage(reaction).queue();
    }

    public void listCustomCommands(MessageReceivedEvent event) {
        StringBuilder strBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : commandMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            strBuilder.append(key).append(" : ").append(value).append("\n");
        }
        String reactionList = strBuilder.toString();

        EmbedBuilder eb = new EmbedBuilder();
        MessageBuilder mb = new MessageBuilder();

        Color green = new Color(0, 255, 0);
        eb.setColor(green);
        eb.addField("Custom Reaction List:", reactionList, true);
        mb.setEmbed(eb.build());

        event.getChannel().sendMessage(mb.build()).queue();
    }

    public void addCustomCommand(String msg, MessageReceivedEvent event) {
        if (containsCustomEvent(msg)) {
            event.getChannel().sendMessage("That reaction already exists.").queue();
        } else {
            String[] splitMsg = msg.split("\"");
            commandMap.put(splitMsg[1], splitMsg[2].substring(1));

            JSONObject obj = (JSONObject) commandMap;
            try (FileWriter file = new FileWriter("resources/CustomCommands.json")) {

                file.write(obj.toJSONString());
                file.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
            event.getChannel().sendMessage("Reaction successfully added.").queue();
        }
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
        switch (messages[1]) {
            case "list":
                listCustomCommands(event);
                break;
            case "add":
                addCustomCommand(msg, event);
                break;
            case "delete":
                deleteCustomCommand(msg, event);
                break;
        }
    }
}

