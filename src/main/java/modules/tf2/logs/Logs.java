package modules.tf2.logs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Logs {

    private int id;
    private String logTitle;
    private String mapName;
    private int viewCount;
    private int playerCount;
    private Date date;

    public Logs(@JsonProperty("id") int steamId, @JsonProperty("title") String logTitle, @JsonProperty("map") String mapName, @JsonProperty("views") int viewCount,
                @JsonProperty("players") int playerCount, @JsonProperty("date") int date) {
        this.id = steamId;
        this.logTitle = logTitle;
        this.mapName = mapName;
        this.viewCount = viewCount;
        this.playerCount = playerCount;
        this.date = new Date(date);
    }

    public int getId() {
        return id;
    }

    public String getLogTitle() { return logTitle; }
}
