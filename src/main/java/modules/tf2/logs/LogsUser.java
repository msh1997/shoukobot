package modules.tf2.logs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LogsUser {

    private String steamId;
    private String username;

    @JsonCreator
    public LogsUser(@JsonProperty("steamid") String steamId, @JsonProperty("username") String username) {
        this.steamId = steamId;
        this.username = username;
    }

    @JsonProperty("steamid")
    public String getSteamId() { return steamId; }

    @JsonProperty("username")
    public String getUsername() { return username; }
}
