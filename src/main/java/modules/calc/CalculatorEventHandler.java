package modules.calc;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CalculatorEventHandler {

    public CalculatorEventHandler() {

    }

    public String addition(String num1, String num2){
        float sum;
        sum = Float.parseFloat(num1) + Float.parseFloat(num2);
        return String.format("%.2f", sum);
    }

    public String subtraction(String num1, String num2){
        float diff;
        diff = Float.parseFloat(num1) - Float.parseFloat(num2);
        return String.format("%.2f", diff);
    }
    public String multiplication(String num1, String num2){
        float product;
        product = Float.parseFloat(num1) * Float.parseFloat(num2);
        return String.format("%.2f", product);
    }
    public String division(String num1, String num2){
        float quotient;
        quotient = Float.parseFloat(num1) / Float.parseFloat(num2);
        return String.format("%.2f", quotient);
    }

    public void calcEvent(String[] messages, MessageReceivedEvent event) {
        switch (messages[1]) {
            case "add":
                event.getChannel().sendMessage(addition(messages[2], messages[3])).queue();
                break;
            case "sub":
                event.getChannel().sendMessage(subtraction(messages[2], messages[3])).queue();
                break;
            case "mult":
                event.getChannel().sendMessage(multiplication(messages[2], messages[3])).queue();
                break;
            case "div":
                event.getChannel().sendMessage(division(messages[2], messages[3])).queue();
                break;
            default:
                event.getChannel().sendMessage("no operation detected").queue();
        }
    }
}
