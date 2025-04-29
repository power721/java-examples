package cn.har01d;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestExamples {
    public static void main(String[] args) {
        String ids = IntStream
                .range(0, 1000)
                .mapToObj(i -> UUID.randomUUID().toString())
                .map(id -> "\"" + id + "\"")
                .collect(Collectors.joining(",", "(", ")"));
        System.out.println(ids);

        // Java
        String input = "login: Pokemon5, password: 1q2w3e4r5t";
        Pattern pattern = Pattern.compile("\\w*\\d+\\w*");
        Matcher matcher = pattern.matcher(input);
        String replacementResult = matcher.replaceAll(it -> "xxx");
        System.out.println("Initial input: '" + input + "'");
        System.out.println("Anonymized input: '" + replacementResult + "'");

        objectIsInstance();

        var numbers = List.of(1, 2, 3, 1);
        var set = Set.copyOf(numbers);
        System.out.println(set);

        System.out.println("xxx".split("||")[0]);
        System.out.println("xxx||yyy".split("||")[0]);
        System.out.println("xxx".split("\\|\\|")[0]);
        System.out.println("xxx||yyy".split("\\|\\|")[0]);

        String json = """
                {
                    "id": 1001,
                    "name": "Pokemon5",
                    "owner": {
                        "id": 1002
                    }
                }
                """;
        System.out.println(json);
        System.out.println(Long.parseLong("100L"));
    }

    public static void objectIsInstance() {
        var numbers = new ArrayList<>();
        numbers.add(null);
        numbers.add(1);
        numbers.add("two");
        numbers.add(3.0);
        numbers.add("four");
        System.out.println("All String elements in upper case:");
        numbers.stream().filter(it -> it instanceof String)
                .forEach(it -> System.out.println(((String) it).toUpperCase()));
    }
}
