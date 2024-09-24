package cn.har01d;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ApiClient {
    private static final Logger log = LoggerFactory.getLogger(ApiClient.class);
    private static final ObjectMapper objectMapper;
    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json;charset=UTF-8";
    public static final String CONTENT_TYPE = "Content-Type";

    static {
        objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.registerModule(new JavaTimeModule());
    }

    private final HttpClient client;

    public ApiClient() {
        this(null);
    }

    public ApiClient(String proxy) {
        var builder = HttpClient.newBuilder();
        if (proxy != null) {
            builder.proxy(getProxy(proxy));
        }
        client = builder.build();
    }

    private ProxySelector getProxy(String proxy) {
        try {
            if (!proxy.startsWith("http")) {
                proxy = "https://" + proxy;
            }
            URI uri = new URI(proxy);
            return ProxySelector.of(new InetSocketAddress(uri.getHost(), uri.getPort()));
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String getHtml(String uri) {
        return get(uri, String.class);
    }

    public JsonNode get(String uri) {
        return get(uri, Map.of(), null);
    }

    public <T> T get(String uri, Class<T> clazz) {
        return get(uri, Map.of(), clazz);
    }

    public <T> T get(String uri, Map<String, String> headers, Class<T> clazz) {
        try {
            var builder = HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .GET();
            for (var entry : headers.entrySet()) {
                builder.setHeader(entry.getKey(), entry.getValue());
            }
            return sendRequest(builder.build(), clazz);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public <T> T post(String uri, Object body, Class<T> clazz) {
        return post(uri, Map.of(), body, clazz);
    }

    public <T> T post(String uri, Map<String, String> headers, Object body, Class<T> clazz) {
        try {
            var builder = HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .headers(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8)
                    .POST(HttpRequest.BodyPublishers.ofString(toJsonString(body)));
            for (var entry : headers.entrySet()) {
                builder.setHeader(entry.getKey(), entry.getValue());
            }
            return sendRequest(builder.build(), clazz);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public <T> T put(String uri, Object body) {
        return put(uri, Map.of(), body, null);
    }

    public <T> T put(String uri, Object body, Class<T> clazz) {
        return put(uri, Map.of(), body, clazz);
    }

    public <T> T put(String uri, Map<String, String> headers, Object body, Class<T> clazz) {
        try {
            var builder = HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .headers(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8)
                    .PUT(HttpRequest.BodyPublishers.ofString(toJsonString(body)));
            for (var entry : headers.entrySet()) {
                builder.setHeader(entry.getKey(), entry.getValue());
            }
            return sendRequest(builder.build(), clazz);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private <T> T sendRequest(HttpRequest request, Class<T> clazz) throws IOException, InterruptedException {
        log.info("Send request: {}", request);
        if (InputStream.class.equals(clazz)) {
            return (T) client.send(request, HttpResponse.BodyHandlers.ofInputStream()).body();
        }
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) {
            return null;
        }
        if (clazz == null || clazz.equals(JsonNode.class)) {
            return (T) readJson(response.body());
        }
        if (clazz.equals(String.class)) {
            return (T) response.body();
        }
        return readJson(response.body(), clazz);
    }

    public static String toJsonString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static JsonNode readJson(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T readJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T readJson(String json, TypeReference<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
