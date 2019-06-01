package shoukobot.services;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class HttpRequests {

    public static JSONParser parser = new JSONParser();

    public static JSONObject getHttpResponse(String path, HashMap<String, String> headers) throws IOException, ParseException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(path);
        headers.keySet().forEach(headerKey -> get.setHeader(headerKey, headers.get(headerKey)));
        HttpResponse response = client.execute(get);
        String responseBody = EntityUtils.toString(response.getEntity());
        return (JSONObject) parser.parse(responseBody);
    }

    public static JSONArray getOsuHttpResponse(String path, HashMap<String, String> headers) throws IOException, ParseException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(path);
        headers.keySet().forEach(headerKey -> get.setHeader(headerKey, headers.get(headerKey)));
        HttpResponse response = client.execute(get);
        String responseBody = EntityUtils.toString(response.getEntity());
        return (JSONArray) parser.parse(responseBody);
    }

    public static JSONObject postHttpResponse(String path, List<NameValuePair> form, HashMap<String, String> headers) throws IOException, ParseException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(path);
        headers.keySet().forEach(headerKey -> post.setHeader(headerKey, headers.get(headerKey)));

        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(form, Consts.UTF_8);
        post.setEntity(entity);
        HttpResponse response = client.execute(post);
        String responseBody = EntityUtils.toString(response.getEntity());
        return (JSONObject) parser.parse(responseBody);
    }

    public static JSONObject putHttpResponse(String path, List<NameValuePair> form, HashMap<String, String> headers) throws IOException, ParseException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPut put = new HttpPut(path);

        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(form, Consts.UTF_8);
        put.setEntity(entity);
        headers.keySet().forEach(headerKey -> put.setHeader(headerKey, headers.get(headerKey)));
        HttpResponse response = client.execute(put);
        String responseBody = EntityUtils.toString(response.getEntity());
        return (JSONObject) parser.parse(responseBody);
    }
}
