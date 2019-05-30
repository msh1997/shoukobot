package shoukobot;

import listener.MessageListener;
import modules.osu.OsuApiService;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {

    private String token;
    private String prefix = "";
    private String osuApiKey = "";
    public static JDABuilder builder;

    public Main() throws IOException, LoginException {
        setProperties();
        this.builder = new JDABuilder(AccountType.BOT);
        builder.setToken(token);
        MessageListener listener = new MessageListener(prefix);
        OsuApiService.apiKey = osuApiKey;
        builder.addEventListener(listener);
        builder.buildAsync();
    }

    public void setProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("resources/config.properties"));
        token = properties.getProperty("token");
        prefix = properties.getProperty("bot-prefix");
        properties.load(new FileInputStream("resources/osu.properties"));
        osuApiKey = properties.getProperty("api-key");
    }

    public static void main(String[] args) throws LoginException, IOException {
        new Main();
    }
}
