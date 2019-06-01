package shoukobot.modules.osu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(value={"join_date", "count300", "count100", "count50", "ranked_score", "total_score", "level", "count_rank_ss", "count_rank_ssh", "count_rank_s",
        "count_rank_sh", "count_rank_a", "country", "events"})
public class OsuUser {

    private String username;
    private int osuId;
    private double pp;
    private int playCount;
    private int playtime;
    private int rankGlobal;
    private int rankCountry;
    private double acc;

    public OsuUser(String username) {
        this.username = username;
    }

    public OsuUser(@JsonProperty("username") String username, @JsonProperty("user_id") int osuId,
                   @JsonProperty("pp_raw") double pp, @JsonProperty("playcount") int playCount,
                   @JsonProperty("total_seconds_played") int playtime,
                   @JsonProperty("pp_rank") int rankGlobal, @JsonProperty("pp_country_rank") int rankCountry,
                   @JsonProperty("accuracy") double acc) {
        this.username = username;
        this.osuId = osuId;
        this.pp = pp;
        this.playCount = playCount;
        this.playtime = playtime;
        this.rankGlobal = rankGlobal;
        this.rankCountry = rankCountry;
        this.acc = acc;
    }

    /*public OsuUser(String username, int osuId,
                   int pp, int playCount,
                   int playtime,
                   int rankGlobal, int rankCountry,
                   long acc) {
        this.username = username;
        this.osuId = osuId;
        this.pp = pp;
        this.playCount = playCount;
        this.playtime = playtime;
        this.rankGlobal = rankGlobal;
        this.rankCountry = rankCountry;
        this.acc = acc;
    }*/

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("user_id")
    public int getOsuId() {
        return osuId;
    }

    public void setOsuId(int osuId) {
        this.osuId = osuId;
    }

    @JsonProperty("pp")
    public double getPp() {
        return pp;
    }

    public void setPp(int pp) {
        this.pp = pp;
    }

    @JsonProperty("playcount")
    public int getPlaycount() {
        return playCount;
    }

    public void setPlaycount(int playcount) {
        this.playCount = playcount;
    }

    @JsonProperty("playtime")
    public int getPlaytime() {
        return playtime;
    }

    public void setPlaytime(int playtime) {
        this.playtime = playtime;
    }

    @JsonProperty("rank")
    public int getRankGlobal() {
        return rankGlobal;
    }

    public void setRankGlobal(int rankGlobal) {
        this.rankGlobal = rankGlobal;
    }

    @JsonProperty("country_rank")
    public int getRankCountry() {
        return rankCountry;
    }

    public void setRankCountry(int rankCountry) {
        this.rankCountry = rankCountry;
    }

    @JsonProperty("accuracy")
    public double getAcc() {
        return acc;
    }

    public void setAcc(double acc) {
        this.acc = acc;
    }
}
