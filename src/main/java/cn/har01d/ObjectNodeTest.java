package cn.har01d;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import net.minidev.json.JSONObject;

public class ObjectNodeTest {
    public static void main(String[] args) {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("user", "test");
        System.out.println(String.valueOf(objectNode.get("user")));
        System.out.println(objectNode.get("user"));
        System.out.println(objectNode.get("user").asText());
        System.out.println(String.valueOf(objectNode.get("xxx")));

        System.out.println(formatAsJson(objectNode.get("user").asText(), "password"));
        System.out.println(formatAsJson(String.valueOf(objectNode.get("user")), "password"));
        System.out.println(formatAsJson(String.valueOf(objectNode.get("xxx")), "password"));
    }

    protected static String formatAsJson(String username, String password) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("username", username);
        jsonObj.put("password", password);
        return jsonObj.toJSONString();
    }
}
