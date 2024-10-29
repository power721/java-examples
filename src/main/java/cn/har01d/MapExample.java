package cn.har01d;

import java.util.HashMap;
import java.util.Map;

public class MapExample {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("1", "1");
        System.out.println(map.get("1"));
        System.out.println(map.get("2"));
        System.out.println(map.get(null));
        System.out.println(map.getOrDefault("1", "2"));
        System.out.println(map.getOrDefault("2", "2"));
        System.out.println(map.getOrDefault(null, "2"));
    }
}
