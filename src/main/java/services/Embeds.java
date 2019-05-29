package services;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.entities.MessageEmbed.Field;

import java.awt.*;
import java.util.ArrayList;

public class Embeds {

    public static void sendEmbed(String title, String url,
                                 ArrayList<Field> fieldList, Color color,
                                 MessageReceivedEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        MessageBuilder mb = new MessageBuilder();
        if(fieldList != null) {
            for (Field field : fieldList) {
                eb.addField(field.getName(), field.getValue(), true);
            }
        }
        eb.setColor(color);
        if(title != null && title.trim().length() > 0) eb.setTitle(title);
        if(url != null) eb.setImage(url);
        mb.setEmbed(eb.build());
        event.getChannel().sendMessage(mb.build()).queue();
    }

    public static void sendEmbed(ArrayList<Field> fieldList, Color color,
                                 MessageReceivedEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        MessageBuilder mb = new MessageBuilder();
        eb.setColor(color);
        if(fieldList != null) {
            for (Field field : fieldList) {
                eb.addField(field.getName(), field.getValue(), true);
            }
        }
        mb.setEmbed(eb.build());
        event.getChannel().sendMessage(mb.build()).queue();
    }

    public static void sendEmbed(Field field, Color color, MessageReceivedEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        MessageBuilder mb = new MessageBuilder();
        eb.setColor(color);
        eb.addField(field.getName(), field.getValue(), true);
        mb.setEmbed(eb.build());
        event.getChannel().sendMessage(mb.build()).queue();
    }
}
