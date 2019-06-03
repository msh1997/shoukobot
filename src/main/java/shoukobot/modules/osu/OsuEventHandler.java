package shoukobot.modules.osu;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.entities.MessageEmbed.Field;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static shoukobot.modules.osu.OsuApiService.getOsuUser;
import static shoukobot.services.Embeds.sendEmbed;

public class OsuEventHandler {

    private List<String> osuUsersList = new ArrayList<>();
    // TODO change osu user stored as KV-pairs with discord id and user id

    public OsuEventHandler() {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("resources/OsuUsers.json")) {
            //Read JSON file
            JSONObject obj = (JSONObject) jsonParser.parse(reader);

            JSONArray usersJsonArray = (JSONArray) (obj.get("users"));

            usersJsonArray.forEach(user -> osuUsersList.add((String) user));

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

    public void addUserToTracking(String[] messages, MessageReceivedEvent event) {

        String username = "";

        for (int i = 2; i < messages.length; i++) {
            username += messages[i];
        }

        if (osuUsersList.contains(username)) {
            event.getChannel().sendMessage("User is already being tracked").queue();
            return;
        }
        osuUsersList.add(username);
        writeTrackedUsersJson();
        event.getChannel().sendMessage(messages[2] + " added to tracking list").queue();
    }


    public void removeTrackedUser(String[] messages, MessageReceivedEvent event) {
        String username = "";

        for (int i = 2; i < messages.length; i++) {
            username += messages[i];
        }

        for (String user : osuUsersList) {
            if (user.equals(username)) {
                osuUsersList.remove(user);
                writeTrackedUsersJson();
                event.getChannel().sendMessage("User " + username + " removed from tracking list").queue();
                return;
            }
        }
        event.getChannel().sendMessage("User " + username + " not found").queue();
    }

    public void listTrackedUsers(String[] messages, MessageReceivedEvent event) {

        StringBuilder message = new StringBuilder();
        for (String user : osuUsersList) {
            message.append(user).append("\n");
        }

        if (message.toString().equals("")) {
            event.getChannel().sendMessage("No tracked users").queue();
        } else {
            event.getChannel().sendMessage(message.toString()).queue();
        }
    }

    public void getUser(String[] messages, MessageReceivedEvent event) throws IOException, ParseException {
        if (messages.length > 2) {
            OsuUser user = getOsuUser(messages[2]);

            String name = "osu! stats for " + user.getUsername();
            String value = "";

            value += "Acc: " + user.getAcc() + "\n";
            value += "pp: " + user.getPp();

            Field field = new Field(name, value, true);
            sendEmbed(field, new Color(0, 255, 0), event);
        }
    }

    public void writeTrackedUsersJson() {
        JSONObject usersJson = new JSONObject();
        JSONArray usersJsonArray = new JSONArray();
        for (String user : osuUsersList) {
            usersJsonArray.add(user);
        }
        usersJson.put("users", usersJsonArray);
        try (FileWriter file = new FileWriter("resources/OsuUsers.json")) {

            file.write(usersJson.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void osuEvent(String[] messages, MessageReceivedEvent event) {
        switch (messages[1]) {
            case "add":
                addUserToTracking(messages, event);
                break;
            case "list":
                listTrackedUsers(messages, event);
                break;
            case "remove":
                removeTrackedUser(messages, event);
                break;
            case "get":
                try {
                    getUser(messages, event);
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
