package modules.osu;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OsuEventHandler {

    private List<String> osuUsersList = new ArrayList<>();

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
        if (messages[1].equals("add")) {
            addUserToTracking(messages, event);
        } else if (messages[1].equals("list")) {
            listTrackedUsers(messages, event);
        } else if (messages[1].equals("remove")) {
            removeTrackedUser(messages, event);
        }
    }
}
