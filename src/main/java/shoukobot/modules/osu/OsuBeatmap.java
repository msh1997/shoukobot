package shoukobot.modules.osu;

public class OsuBeatmap {

    private int beatmapId;
    private double sr;
    private double od;
    private double ar;
    private double bpm;
    private double hp;
    private double cs;
    private String diffName;

    public OsuBeatmap(int beatmapId) {
        this.beatmapId = beatmapId;
    }

    public int getBeatmapId() {
        return beatmapId;
    }

    public double getSr() {
        return sr;
    }

    public double getOd() {
        return od;
    }

    public double getAr() {
        return ar;
    }

    public double getBpm() {
        return bpm;
    }

    public double getHp() {
        return hp;
    }

    public double getCs() {
        return cs;
    }

    public String getDiffName() {
        return diffName;
    }
}
