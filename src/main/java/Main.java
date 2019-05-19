import Listeners.TestListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args) throws LoginException {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = "NTc4Nzc4MzIyODkzNTM3Mjgz.XOHjiA.o_YQk8T-2YmHPBirKLxMOqlea80";
        builder.setToken(token);
        TestListener listener = new TestListener();
        builder.addEventListener(listener);
        builder.buildAsync();
    }
}
