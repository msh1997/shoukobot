package modules.logs;

public class LogsUser {

    private String steamId;

    public LogsUser(String steamId) { this.steamId = steamId; }

    public void setSteamId(String steamId) { this.steamId = steamId; }

    public String getSteamId() { return steamId; }
}
