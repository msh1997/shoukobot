package shoukobot.modules.osu;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import shoukobot.services.HttpRequests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class OsuApiService {

    public static String apiKey;
    private static ObjectMapper objectMapper;

    public OsuApiService() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("resources/osu.properties"));
        apiKey = properties.getProperty("api-key");
        objectMapper = new ObjectMapper();
    }

    public static OsuUser getOsuUser(String username) throws IOException, ParseException {
        String path = "https://osu.ppy.sh/api/get_user?k=" + apiKey + "&u=" + username;
        JSONArray response = HttpRequests.getOsuHttpResponse(path, new HashMap<>());

        System.out.println(response.toJSONString());

        return new OsuUser(username);
    }
}
