package shoukobot.modules.osu;

import java.util.List;

public class OsuBeatmapSet {

    private int id;
    private List<OsuBeatmap> beatmapList;
    private String mapper;

    public OsuBeatmapSet(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public List<OsuBeatmap> getBeatmapList() {
        return beatmapList;
    }

    public String getMapper() {
        return mapper;
    }
}
