package modules.help;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.entities.MessageEmbed.Field;

import java.awt.*;

import static shoukobot.services.Embeds.sendEmbed;

public class HelpEventHandler {

    public void reactionsHelp(String[] messages, MessageReceivedEvent event) {
        if(messages.length < 3) {
            String name = "Available Commands in Reactions Module";
            String value = "add\ndelete\nlist\n\n" +
                    "For further information about a specific command,\n" +
                    "append the command name\n" +
                    "Ex: r.help reactions add";
            Field field = new Field(name, value, true);
            sendEmbed(field, new Color(0, 255, 0), event);
            return;
        }

        String name = "";
        String value = "";
        switch (messages[2]) {
            case "add":
                name = "r.reactions add \"[Reaction Trigger]\" [Reaction Content]";
                value = "Adds a reaction to the list of custom reactions.\n" +
                        "Ensure that the reaction trigger is encased in quotes,\n" +
                        "and that the reaction content follows after.";
                break;
            case "delete":
                name = "r.reactions delete [Reaction Trigger]";
                value = "Deletes a reaction from the list of custom reactions.";
                break;
            case "list":
                name = "r.reactions list";
                value = "Lists all available custom reactions.";
                break;
        }
        Field field = new Field(name, value, true);
        sendEmbed(field, new Color(0, 255, 0), event);
    }

    public void osuHelp(String[] messages, MessageReceivedEvent event) {
        if(messages.length < 3) {
            String name = "Available Commands in osu! Module";
            String value = "add\nremove\nlist\n\n" +
                    "For further information about a specific command,\n" +
                    "append the command name\n" +
                    "Ex: r.help osu add";
            Field field = new Field(name, value, true);
            sendEmbed(field, new Color(0, 255, 0), event);
            return;
        }

        String name = "";
        String value = "";
        switch (messages[2]) {
            case "add":
                name = "r.osu add [Username]";
                value = "Adds a user to the list of tracked osu! players.\n";
                break;
            case "remove":
                name = "r.osu remove [Username]";
                value = "Deletes a user from the list of osu! players.";
                break;
            case "list":
                name = "r.osu list";
                value = "Lists all currently tracked osu! players.";
                break;
        }
        Field field = new Field(name, value, true);
        sendEmbed(field, new Color(0, 255, 0), event);
    }

    public void tf2Help(String[] messages, MessageReceivedEvent event) {
        if(messages.length < 3) {
            String name = "Available Sub Modules in TF2 Module";
            String value = "logs\nconnect string parsing\n\n" +
                    "For further information about a specific sub module,\n" +
                    "append the sub module name\n" +
                    "Ex: r.help tf2 logs";
            Field field = new Field(name, value, true);
            sendEmbed(field, new Color(0, 255, 0), event);
            return;
        }

        switch (messages[2]) {
            case "logs":
                logsHelp(messages, event);
                break;
            case "connect":
                connectHelp(event);
                break;
        }
    }

    public void logsHelp(String[] messages, MessageReceivedEvent event) {
        if(messages.length < 4) {
            String name = "Available Commands in logs.tf Module";
            String value = "add\nremove\nget\nlist\n\n" +
                    "For further information about a specific command,\n" +
                    "append the command name\n" +
                    "Ex: r.help tf2 logs add";
            Field field = new Field(name, value, true);
            sendEmbed(field, new Color(0, 255, 0), event);
            return;
        }

        String name = "";
        String value = "";
        switch (messages[3]) {
            case "add":
                name = "r.logs add [Username] [SteamID64]";
                value = "Adds a user to the list of tracked logs.tf users.\n";
                break;
            case "remove":
                name = "r.logs remove [Username OR SteamID64]";
                value = "Deletes a user from the list of tracked logs.tf users.";
                break;
            case "get":
                name = "r.logs get [Username] [Optional: Number 1-10]";
                value = "Lists most recent logs.tf results for Username.\n" +
                        "You can specify any number of logs to retrieve between 1-10.";
                break;
            case "list":
                name = "r.logs list";
                value = "Lists all currently tracked logs.tf users.";
                break;
        }
        Field field = new Field(name, value, true);
        sendEmbed(field, new Color(0, 255, 0), event);
    }

    public void connectHelp(MessageReceivedEvent event) {
        String name = "[Source Connect String]";
        String value = "Source connect strings will be automatically parsed into clickable links.";
        Field field = new Field(name, value, true);
        sendEmbed(field, new Color(0, 255, 0), event);
    }

    public void calcHelp(String[] messages, MessageReceivedEvent event) {
        if(messages.length < 3) {
            String name = "Available Commands in Calculator Module";
            String value = "add\nsub\nmult\ndiv\n\n" +
                    "For further information about a specific command,\n" +
                    "append the command name\n" +
                    "Ex: r.help calc add";
            Field field = new Field(name, value, true);
            sendEmbed(field, new Color(0, 255, 0), event);
            return;
        }

        String name = "";
        String value = "";
        switch (messages[2]) {
            case "add":
                name = "r.calc add [Operand_1] [Operand_2]";
                value = "Adds Operand_1 to Operand_2.";
                break;
            case "sub":
                name = "r.calc sub [Operand_1] [Operand_2]";
                value = "Subtracts Operand_2 from Operand_1.";
                break;
            case "mult":
                name = "r.calc mult [Operand_1] [Operand_2]";
                value = "Multiplies Operand_1 with Operand_2.";
                break;
            case "div":
                name = "r.calc div [Operand_1] [Operand_2]";
                value = "Divides Operand_1 by Operand_2.";
                break;
        }
        Field field = new Field(name, value, true);
        sendEmbed(field, new Color(0, 255, 0), event);
    }

    public void miscHelp(String[] messages, MessageReceivedEvent event) {
        if(messages.length < 3) {
            String name = "Available Commands in Misc Module";
            String value = "ping\necho\n\n" +
                    "For further information about a specific command,\n" +
                    "append the command name\n" +
                    "Ex: r.help misc ping";
            Field field = new Field(name, value, true);
            sendEmbed(field, new Color(0, 255, 0), event);
            return;
        }

        String name = "";
        String value = "";
        switch (messages[2]) {
            case "ping":
                name = "r.ping";
                value = "Replies with \"Pong!\"";
                break;
            case "echo":
                name = "r.echo [Message]";
                value = "Replies with [Message] and deletes the message you used to call echo.";
                break;
        }
        Field field = new Field(name, value, true);
        sendEmbed(field, new Color(0, 255, 0), event);
    }

    public void helpEvent(String[] messages, MessageReceivedEvent event) {
        if (messages.length < 2) {
            String name = "Modules List:";
            String value = "reactions\nosu\ntf2\ncalc\nmisc\n\n" +
                    "For information about a specific module,\n" +
                    "append the module name\n" +
                    "Ex: r.help reactions";
            Field field = new Field(name, value, true);
            sendEmbed(field, new Color(0, 255, 0), event);
            return;
        }

        switch (messages[1]) {
            case "reactions":
                reactionsHelp(messages, event);
                break;
            case "osu":
                osuHelp(messages, event);
                break;
            case "tf2":
                tf2Help(messages, event);
                break;
            case "calc":
                calcHelp(messages, event);
                break;
            case "misc":
                miscHelp(messages, event);
                break;
        }
    }
}
