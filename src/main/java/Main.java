import Listener.MessageListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {

    public static String token;
    public static String prefix = "";

    public static void main(String[] args) throws LoginException, IOException {
        setProperties();
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken(token);
        MessageListener listener = new MessageListener(prefix);
        builder.addEventListener(listener);
        builder.buildAsync();
    }

    public static void setProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("resources/config.properties"));
        token = properties.getProperty("token");
        prefix = properties.getProperty("bot-prefix");
    }
}
