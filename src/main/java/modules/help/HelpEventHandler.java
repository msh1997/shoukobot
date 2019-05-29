package modules.help;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static services.Embeds.sendEmbed;

public class HelpEventHandler {

    public void reactionsHelp(String[] messages, MessageReceivedEvent event) {
        if(messages.length < 3) {
            String name = "Available Commands in Reactions Module";
            String value = "add\ndelete\nlist\n\n" +
                    "For further information about a specific command," +
                    "\nappend the command name\n" +
                    "Ex: r.help reactions add";

            sendEmbed(name, value, event);
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
                value = "Deletes a reaction from the list of custom reactions.\n";
                break;
            case "list":
                name = "r.reactions list";
                value = "Lists all available custom reactions.";
                break;
        }
        sendEmbed(name, value, event);
    }

    public void osuHelp(String[] messages, MessageReceivedEvent event) {

    }

    public void tf2Help(String[] messages, MessageReceivedEvent event) {

    }

    public void miscHelp(String[] messages, MessageReceivedEvent event) {

    }

    public void helpEvent(String[] messages, MessageReceivedEvent event) {
        if (messages.length < 2) {
            String name = "Modules List:";
            String value = "reactions\nosu\ntf2\nmisc\n\nFor information about a specific module,\n" +
                    "append the module name\n" +
                    "Ex: r.help reactions";

            sendEmbed(name, value, event);
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
            case "misc":
                miscHelp(messages, event);
                break;
        }
    }
}