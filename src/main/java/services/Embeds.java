package services;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public class Embeds {

    public static void sendEmbed(String name, String value, MessageReceivedEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        MessageBuilder mb = new MessageBuilder();

        Color green = new Color(0, 255, 0);
        eb.setColor(green);
        eb.addField(name, value, true);
        mb.setEmbed(eb.build());

        event.getChannel().sendMessage(mb.build()).queue();
    }
}
