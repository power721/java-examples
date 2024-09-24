package cn.har01d;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    
    public static void main(String[] args) {
        var client = new ApiClient();
        log.info("{}", client.getHtml("https://reqbin.com/echo"));

        log.info("{}", client.get("https://reqbin.com/echo/get/json"));

        Map<String, Object> body = new HashMap<>();
        body.put("id", 1);
        body.put("name", "test");
        Map<String, Object> response = client.post("https://reqbin.com/echo/post/json", body, Map.class);
        log.info("{}", response);

        log.info("{}", client.put("https://reqbin.com/echo/put/json", "test", String.class));
    }
}