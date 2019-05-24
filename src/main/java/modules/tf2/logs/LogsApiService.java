package modules.tf2.logs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import services.HttpRequests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LogsApiService {

    private int apiKey;
    private ObjectMapper objectMapper;
    private JSONParser parser;

    public LogsApiService(){
        objectMapper = new ObjectMapper();

    }

    public List<Logs> getLogsByUser(LogsUser logsUser, int limit) throws IOException, ParseException {
        String path = "http://logs.tf/api/v1/log?player=" + logsUser.getSteamId() + "&limit=" + limit;
        JSONObject response = HttpRequests.getHttpResponse(path, new HashMap<>());
        JSONArray logsListJson = (JSONArray) response.get("logs");
        String logsListJsonString = logsListJson.toJSONString();
        List<Logs> logsList = objectMapper.readValue(logsListJsonString, new TypeReference<List<Logs>>(){});

        return logsList;
    }

}
