package modules.osu;

public class OsuApiService {

    public static String apiKey;

    public static OsuUser getUser(String username) {
        return new OsuUser(username);
    }
}
