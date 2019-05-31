package shoukobot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import shoukobot.listener.MessageListener;
import shoukobot.modules.osu.OsuApiService;

import javax.security.auth.login.LoginException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {

    private String token;
    private String prefix = "";
    private String osuApiKey = "";
    public static JDABuilder builder;
    public static JDA jda;

    public Main() throws IOException, LoginException {
        setProperties();
        this.builder = new JDABuilder(AccountType.BOT);
        builder.setToken(token);
        builder.setGame(Game.playing("Type r.help to get started!"));
        MessageListener listener = new MessageListener(prefix);
        OsuApiService.apiKey = osuApiKey;
        builder.addEventListener(listener);
        this.jda = builder.buildAsync();
    }

    public void setProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("resources/config.properties"));
        token = properties.getProperty("token");
        prefix = properties.getProperty("bot-prefix");
        properties.load(new FileInputStream("resources/osu.properties"));
        osuApiKey = properties.getProperty("api-key");
    }

    public static JDA getJda(){
        return jda;
    }

    public static void main(String[] args) throws LoginException, IOException {
        new Main();
    }
}
