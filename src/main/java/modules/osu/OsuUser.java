package modules.osu;

public class OsuUser {

    private String username;
    private int osuId;
    private int pp;
    private int playCount;
    private int playtime;
    private int rankGlobal;
    private int rankCountry;
    private int acc;

    public OsuUser(String username) {
        this.username = username;
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

    public int getAcc() {
        return acc;
    }

    public void setAcc(int acc) {
        this.acc = acc;
    }
}
