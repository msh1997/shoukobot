package shoukobot.modules.osu;

public class OsuUser {

    private String username;
    private int osuId;
    private int pp;
    private int playCount;
    private int playtime;
    private int rankGlobal;
    private int rankCountry;
    private long acc;

    public OsuUser(String username) {
        this.username = username;
    }

//    public OsuUser(@JsonProperty("username") String username, @JsonProperty("user_id") int osuId,
//                   @JsonProperty("pp_raw") int pp, @JsonProperty("playcount") int playCount,
//                   @JsonProperty("total_seconds_played") int playtime,
//                   @JsonProperty("pp_rank") int rankGlobal, @JsonProperty("pp_country_rank") int rankCountry,
//                   @JsonProperty("accuracy") long acc) {
//        this.username = username;
//        this.osuId = osuId;
//        this.pp = pp;
//        this.playCount = playCount;
//        this.playtime = playtime;
//        this.rankGlobal = rankGlobal;
//        this.rankCountry = rankCountry;
//        this.acc = acc;
//    }

    public OsuUser(String username, int osuId,
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
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getOsuId() {
        return osuId;
    }

    public void setOsuId(int osuId) {
        this.osuId = osuId;
    }

    public int getPp() {
        return pp;
    }

    public void setPp(int pp) {
        this.pp = pp;
    }

    public int getPlaycount() {
        return playCount;
    }

    public void setPlaycount(int playcount) {
        this.playCount = playcount;
    }

    public int getPlaytime() {
        return playtime;
    }

    public void setPlaytime(int playtime) {
        this.playtime = playtime;
    }

    public int getRankGlobal() {
        return rankGlobal;
    }

    public void setRankGlobal(int rankGlobal) {
        this.rankGlobal = rankGlobal;
    }

    public int getRankCountry() {
        return rankCountry;
    }

    public void setRankCountry(int rankCountry) {
        this.rankCountry = rankCountry;
    }

    public long getAcc() {
        return acc;
    }

    public void setAcc(int acc) {
        this.acc = acc;
    }
}
