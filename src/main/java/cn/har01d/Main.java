package cn.har01d;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    
    public static void main(String[] args) {
        var client = new ApiClient();
        String html = client.getHtml("https://reqbin.com/echo");
        log.info("html: {}", html);

        HttpResponse<JsonNode> response = client.get("https://reqbin.com/echo/get/json");
        log.info("JsonNode: {}", response.body());

        Map<String, Object> body = new HashMap<>();
        body.put("id", 1);
        body.put("name", "test");
        HttpResponse<Map> response1 = client.post("https://reqbin.com/echo/post/json", body, Map.class);
        log.info("Map: {}", response1.body());

        HttpResponse<String> response2 = client.put("https://reqbin.com/echo/put/json", "test", String.class);
        log.info("String: {}", response2.body());
    }
}