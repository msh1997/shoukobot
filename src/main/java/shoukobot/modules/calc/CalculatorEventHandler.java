package shoukobot.modules.calc;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Stack;

import static java.lang.Float.parseFloat;
import static shoukobot.modules.calc.ShuntingYard.postfix;

public class CalculatorEventHandler {

    public CalculatorEventHandler() {

    }

    public String addition(String num1, String num2) {
        float sum;
        sum = parseFloat(num1) + parseFloat(num2);
        return String.format("%.2f", sum);
    }

    public String subtraction(String num1, String num2) {
        float diff;
        diff = parseFloat(num1) - parseFloat(num2);
        return String.format("%.2f", diff);
    }

    public String multiplication(String num1, String num2) {
        float product;
        product = parseFloat(num1) * parseFloat(num2);
        return String.format("%.2f", product);
    }

    public String division(String num1, String num2) {
        float quotient;
        quotient = parseFloat(num1) / parseFloat(num2);
        return String.format("%.2f", quotient);
    }

    public String expression(String[] messages, MessageReceivedEvent event) {
        StringBuilder rawExpr = new StringBuilder();
        for (int i = 2; i < messages.length; i++) {
            rawExpr.append(messages[i]);
        }

        String[] rawExprArray = rawExpr.toString().split("(?<=[-+*/()])|(?=[-+*/()])");

        StringBuilder expr = new StringBuilder();
        for (String s : rawExprArray) {
            expr.append(s + " ");
        }

        String[] postfixArray = postfix(expr.toString()).split(" ");
        Stack<Float> stack = new Stack<>();

        for (String s : postfixArray) {
            if (isNumber(s)) {
                stack.push(parseFloat(s));
            } else {
                float operand1 = stack.pop();
                float operand2 = stack.pop();

                switch(s) {
                    case "+":
                        stack.push(operand1 + operand2);
                        break;
                    case "-":
                        stack.push(operand2 - operand1);
                        break;
                    case "*":
                        stack.push(operand1 * operand2);
                        break;
                    case "/":
                        stack.push(operand2 / operand1);
                        break;
                    case "^":
                        stack.push((float) Math.pow(operand1, operand2));
                        break;
                }
            }
        }
        return String.format("%.2f", stack.pop());
    }

    public boolean isNumber(String val) {
        try {
            parseFloat(val);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void calcEvent(String[] messages, MessageReceivedEvent event) {
        try {
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
                case "expression":
                    event.getChannel().sendMessage(expression(messages, event)).queue();
                    break;
                default:
                    event.getChannel().sendMessage("no operation detected").queue();
            }
        } catch (Exception e) {
            event.getChannel().sendMessage("Invalid Operands.").queue();
            e.printStackTrace();
        }
    }
}
